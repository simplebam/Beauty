package com.yueyue.mybeauty.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.bugtags.library.Bugtags;
import com.yueyue.mybeauty.MainActivity;
import com.yueyue.mybeauty.R;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * author : yueyue on 2017/12/25 19:09
 * desc   : BaseActivity includes a base layoutId, init its toolbar (if the layout has one)
 * quote  : http://blog.csdn.net/MeloDev/article/details/53406019
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    public Realm realm;
    public Toolbar toolbar;
    private boolean isShowToolbar = true;
    private Fragment currentFragment;

    protected abstract int initLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
    }

    /**
     * MUST override and call the SUPER method
     */
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setContentView(initLayoutId());
        ButterKnife.bind(this);
        initAppBar();
        initSDK();
    }

    private void initSDK() {
        realm = Realm.getDefaultInstance();
    }


    public void initAppBar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            //设置显示返回箭头
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    public void setToolbarTitle(String title) {
        if (toolbar != null) toolbar.setTitle(title);
    }

    public void toggleToolbar() {
        if (isShowToolbar) {
            hideToolbar();
        } else {
            showToolbar();
        }
    }

    /**
     * 自定义控件三部曲之动画篇（二）——Interpolator插值器 - CSDN博客:
     * http://blog.csdn.net/harvic880925/article/details/40049763
     */
    public void hideToolbar() {
        if (toolbar != null) {
            isShowToolbar = false;
            toolbar.animate()
                    .translationY(-toolbar.getBottom())
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
        }
    }

    /**
     * 自定义控件三部曲之动画篇（二）——Interpolator插值器 - CSDN博客:
     * http://blog.csdn.net/harvic880925/article/details/40049763
     */
    public void showToolbar() {
        if (toolbar != null) {
            isShowToolbar = true;
            toolbar.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
    }

    public void setMainFragment(int id, SparseArray<Fragment> array, boolean first) {
        Fragment fragment = array.get(id);
//        if (fragment == null || currentFragment == fragment) {
//            return;
//        }
//        Fragment old = currentFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //Main fragment 不需要动画
        //transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//        if (old != null) {
//            transaction.hide(old);
//        }
//        if (fragment.isAdded()) {
//            transaction.show(fragment);
//            transaction.commit();
//            return;
//        }
        if (first) {
            transaction.add(R.id.container, fragment, String.valueOf(id));
        } else {
            transaction.replace(R.id.container, fragment, String.valueOf(id));
        }
        transaction.commit();

        this.currentFragment = fragment;
    }

    public void switchMenu(final int id, SparseArray<Fragment> array) {
        final Fragment fragment = array.get(id);
        if (fragment == null || currentFragment == fragment) {
            return;
        }
        Fragment old = currentFragment;
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        if (old != null) {
            transaction.hide(old);
        }

        if (fragment.isAdded()) {
            transaction.show(fragment);
            transaction.commit();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    transaction.add(R.id.container, fragment, String.valueOf(id));
                    transaction.commit();
                }
            }, MainActivity.DRAWER_CLOSE_DELAY);

        }
        currentFragment = fragment;
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：Bugtags回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }



}