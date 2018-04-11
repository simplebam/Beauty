package com.yueyue.mybeauty.picture;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yueyue.mybeauty.MainActivity;
import com.yueyue.mybeauty.R;
import com.yueyue.mybeauty.base.BaseActivity;
import com.yueyue.mybeauty.config.Constants;
import com.yueyue.mybeauty.model.DataBase;
import com.yueyue.mybeauty.model.Image;
import com.yueyue.mybeauty.ui.SettingFragment;
import com.yueyue.mybeauty.utils.SnackUtil;
import com.yueyue.mybeauty.utils.SpUtil;

import io.realm.RealmResults;


/**
 * Gank and DB beauty fragment.
 */
public abstract class PictureFragment extends RecyclerFragment {
    public static final int REQUEST_VIEW = 1;
    private static final String TAG = "PictureFragment";
    public static int LOAD_COUNT = 10;
    private static int PRELOAD_COUNT = 10;

    String url;
    boolean isFetching;
    String title;
    int page = 1;
    String info;//附加信息，这里暂时用于type为A区时的帖子地址
    BaseActivity context;
    String baseType;
    StaggeredGridLayoutManager layoutManager;
    PictureAdapter adapter;
    RealmResults<Image> images;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        firstPosition = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()])[0];
        SpUtil.save(imageType + Constants.POSITION, firstPosition);
        super.onPause();
    }


    @Override
    protected void initViews() {
        super.initViews();
//        setRetainInstance(true);
        initFab();
        //baseType is for base url
        baseType = getArguments() == null ? "" : getArguments().getString(Constants.TYPE);
        context = (BaseActivity) getActivity();
        layoutManager = new WrapContentLinearLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PictureAdapter(initAdapterLayout(), this);
        //开启动画(默认为渐显效果)
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //禁止默认第一次加载Item进入回调onLoadMoreRequested方法
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            //取消掉所有的动画,解决"更新单个Item时，此Item有闪烁"的问题
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                onImageClicked(view, i);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 30) {
                    return;
                }
                int[] spans = new int[layoutManager.getSpanCount()];
                firstPosition = layoutManager.findFirstVisibleItemPositions(spans)[0];
                lastPosition = findMax(layoutManager.findLastVisibleItemPositions(spans));
            }
        });
        imageType = baseType;
    }

    private int findMax(int[] arr) {
        int max = arr[0];
        for (int value : arr) {
            if (value > max) max = value;
        }
        return max;
    }

    protected void onImageClicked(View view, int position) {
        startViewer(view, position);
    }

    protected int initAdapterLayout() {
        return R.layout.picture_item;
    }

    public void startViewer(View view, int position) {
        Intent intent = new Intent(context.getApplicationContext(), ViewerActivity.class);
        intent.putExtra(Constants.TYPE, imageType);
        intent.putExtra(Constants.POSITION, position);

        //Android动画：转场动画(过度动画) ActivityOptionsCompat - AndroidCQC的博客 - CSDN博客
        //       http://blog.csdn.net/ss1168805219/article/details/53445063
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context, view, adapter.getData().get(position).url);
        ActivityCompat.startActivityForResult(context, intent, REQUEST_VIEW, options.toBundle());
    }

    public abstract void fetch();//确定type，base url和解析数据

    //改变是否在加载数据的状态
    public void changeState(boolean fetching) {
        isFetching = fetching;
        changeRefresh(isFetching);
    }

    @Override
    public void onStop() {
        super.onStop();
        changeState(false);
    }

    @Override
    protected void onCreateView() {
        super.onCreateView();
        log("onCreateView: ", imageType);
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected void initData() {
        images = DataBase.findImages(realm, imageType);
        log("imagetype" + imageType);
        //如果上拉结束后，下拉刷新需要再次开启上拉监听，需要使用setNewData方法填充数据。
        adapter.setNewData(images);
        recyclerView.animate().alpha(1).setStartDelay(200).start();
    }

    private void initFab() {
        if (SpUtil.getBoolean(SettingFragment.SECRET_MODE)) {
            return;
        }
        final FloatingActionButton button = ((MainActivity) getActivity()).fab;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (button.isShown()) {
                    if (dy > 20) {
                        button.hide();
                    }
                } else {
                    if (dy < -20) {
                        button.show();
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        fetch();
        if (adapter.isLoading()) {
            changeRefresh(false);
            SnackUtil.showSnack(rootView, R.string.is_loading);
        }
    }

    public Image getImage(int position) {
        return adapter.getData().get(position);
    }


    public class WrapContentLinearLayoutManager extends StaggeredGridLayoutManager {
        WrapContentLinearLayoutManager(int spanCount, int orientation) {
            super(spanCount, orientation);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("probe", "meet a IOOBE in RecyclerView");
            }
        }
    }


}
