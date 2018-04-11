package com.yueyue.mybeauty.picture;


import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.yueyue.mybeauty.R;
import com.yueyue.mybeauty.base.BaseFragment;
import com.yueyue.mybeauty.config.Constants;
import com.yueyue.mybeauty.utils.SpUtil;

import butterknife.BindView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * All fragments have recyclerView & swipeRefresh must implement this.
 */
public abstract class RecyclerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    boolean firstFetch;   //whether is first time to enter fragment
    String imageType;               // imageType of recyclerView's content
    int lastPosition;       //last visible position
    int firstPosition;      //first visible position
    Subscription subscription;
    CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_recycler;
    }

    /**
     * Fragment的onViewStateRestored onViewCreated 函数 - CSDN博客
     * http://blog.csdn.net/sfshine/article/details/36186483
     * <p>
     * 这种方法已经弃用,正确做法
     * https://inthecheesefactory.com/blog/fragment-state-saving-best-practices/en
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) {
            //restoring position when reentering app.
            lastPosition = SpUtil.getInt(imageType + Constants.POSITION);
            if (lastPosition > 0) {
                log("restore lastPosition", lastPosition);
                recyclerView.smoothScrollToPosition(lastPosition);
            }
        }
    }

    @Override
    public void onDestroy() {
        compositeSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);

        //android--SwipeRefreshLayout 设置下拉刷新进度条颜色变化没效果 - qq_33703877的博客 - CSDN博客
        //        http://blog.csdn.net/qq_33703877/article/details/54692461
        swipeRefresh.setColorSchemeColors(getColor(R.color.colorPrimary),
                getColor(R.color.colorPrimaryDark), getColor(R.color.colorAccent)); //设置进度的颜色

        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        int viewPosition = SpUtil.getInt(imageType + Constants.VIEW_POSITION);
        if (viewPosition > 0) {
            recyclerView.smoothScrollToPosition(viewPosition);
            SpUtil.remove(imageType + Constants.VIEW_POSITION);
        }
    }

    public void changeRefresh(final boolean refreshState) {
        //实现Material风格的滑动刷新Swipe to Refresh - 泡在网上的日子
        //       http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1215/2166.html
        if (swipeRefresh != null) swipeRefresh.setRefreshing(refreshState);

    }

    public int getColor(int resId) {
        return ResourcesCompat.getColor(getResources(), resId, null);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}
