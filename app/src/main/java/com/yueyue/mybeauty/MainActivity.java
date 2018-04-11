package com.yueyue.mybeauty;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.yueyue.mybeauty.base.BaseActivity;
import com.yueyue.mybeauty.helper.Updater;
import com.yueyue.mybeauty.lib.PopupDialogActivity;
import com.yueyue.mybeauty.picture.FavoriteFragment;
import com.yueyue.mybeauty.ui.SettingActivity;
import com.yueyue.mybeauty.ui.SettingFragment;
import com.yueyue.mybeauty.utils.AppUtil;
import com.yueyue.mybeauty.utils.Imager;
import com.yueyue.mybeauty.utils.Sharer;
import com.yueyue.mybeauty.utils.SnackUtil;
import com.yueyue.mybeauty.utils.SpUtil;

import java.util.Date;

import butterknife.BindView;

import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static com.yueyue.mybeauty.net.API.TYPE_A_ANIME;
import static com.yueyue.mybeauty.net.API.TYPE_A_FULI;
import static com.yueyue.mybeauty.net.API.TYPE_A_HENTAI;
import static com.yueyue.mybeauty.net.API.TYPE_A_UNIFORM;
import static com.yueyue.mybeauty.net.API.TYPE_A_ZATU;
import static com.yueyue.mybeauty.net.API.TYPE_DB_BREAST;
import static com.yueyue.mybeauty.net.API.TYPE_DB_BUTT;
import static com.yueyue.mybeauty.net.API.TYPE_DB_LEG;
import static com.yueyue.mybeauty.net.API.TYPE_DB_RANK;
import static com.yueyue.mybeauty.net.API.TYPE_DB_SILK;
import static com.yueyue.mybeauty.net.API.TYPE_GANK;

/**
 * author : yueyue on 2017/12/25 19:09
 * desc   : BaseActivity includes a base layoutId, init its toolbar (if the layout has one)
 * quote  :
 * 1.ToolBar使用心得(如何改变item的位置) - 泡在网上的日子
 * http://www.jcodecraeer.com/plus/view.php?aid=7667
 * 2.Toolbar+DrawerLayout+NavigationView使用心得 - 泡在网上的日子:
 * http://www.jcodecraeer.com/a/anzhuokaifa/2017/0317/7694.html
 * 3.Android ToolBar 使用完全解析 - 简书
 * https://www.jianshu.com/p/ae0013a4f71a
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MAIN_FRAGMENT_TAG = "main";
    public static final int DRAWER_CLOSE_DELAY = 230;


    @BindView(R.id.fab)
    public FloatingActionButton fab;
    public ActionBarDrawerToggle toggle;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.container)
    ViewGroup container;
    @BindView(R.id.reveal)
    FrameLayout revealView;
    @BindView(R.id.root)
    CoordinatorLayout root;
    private boolean backPressed;
    private MenuItem currentMenu;
    private SparseArray<Fragment> fragmentSparseArray;
    private Updater updater;
    private boolean isFirst = true;
    private int placeHolderHeight;
    private boolean secretMode;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDrawer();
        initToolbar();
        updater = Updater.getInstance(this);
        updater.check();
        initNavigationView();
        initFragments(savedInstanceState);
    }


    /**
     * android官方侧滑菜单DrawerLayout详解 - 泡在网上的日子
     * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0925/1713.html
     * 用Android自带的DrawerLayout和ActionBarDrawerToggle实现侧滑效果 - CSDN博客
     * http://blog.csdn.net/miyuexingchen/article/details/52232751
     */
    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        //实现toolbar和Drawer的联动
        toggle.syncState();
    }


    private void initToolbar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            layoutParams.height = BarUtils.getActionBarHeight(this);
            toolbar.setLayoutParams(layoutParams);
        }
        initFab();
    }


    private void initFab() {
        if (secretMode) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SnackbarUtils.with(fab).setMessage(getString(R.string.you_are_vip)).show();
//                    SnackUtil.showSnack(fab, R.string.you_are_vip);
                }
            });
            root.removeView(fab);
            return;
        }

        fab.animate().setStartDelay(0).setDuration(400).scaleY(1).scaleX(1).start();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
                }
            });
            return;
        }


        //Morph transition https://www.jianshu.com/p/37e94f8b6f59
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPopupDialogActivity();
            }
        });

    }

    private void startPopupDialogActivity() {
        Intent login = PopupDialogActivity.getStartIntent(MainActivity.this, PopupDialogActivity.MORPH_TYPE_FAB);
        ActivityOptionsCompat options;
        options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (MainActivity.this, fab, getString(R.string.transition_morph_view));
        startActivity(login, options.toBundle());
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog alertDialog = builder.setTitle(R.string.hint)
                .setMessage(R.string.thanks_for_donation)
                .setPositiveButton(R.string.donate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtil.donate(MainActivity.this);
                    }
                })
                .create();
//                alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlideDialog;
        alertDialog.show();
    }

    public void toggleToolbarFlag(boolean scroll) {
        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        if (scroll) {
            p.setScrollFlags(SCROLL_FLAG_SCROLL | SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
        } else {
            p.setScrollFlags(0);
        }
        toolbar.setLayoutParams(p);
    }

    private void collapseToolbar() {
//        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(toolbar, "height", placeHolderHeight, BarUtils.getActionBarHeight(this));
//        objectAnimator.setDuration(3000);
//        objectAnimator.start();
//        toolbar.requestLayout();

        ValueAnimator animator = ValueAnimator.ofInt(placeHolderHeight, BarUtils.getActionBarHeight(this));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                Log.d(TAG, "collapseToolbar: " + layoutParams.height);
                toolbar.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.d(TAG, "onAnimationEnd: ");
                initFab();
            }
        });
        animator.start();
    }

    //https://www.jianshu.com/p/45cc7775a44b
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (currentMenu != null) {
//            outState.putInt("itemId", currentMenu.getItemId());
//        }
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        int itemId = savedInstanceState.getInt("itemId", R.id.nav_beauty);
//        MenuItem currentMenu = navView.getMenu().findItem(itemId);
//        navView.setCheckedItem(itemId);
//        if (currentMenu != null) {
//            onNavigationItemSelected(currentMenu);
//        }
//    }
    @TargetApi(Build.VERSION_CODES.M)
    private void initNavigationView() {
        //        Colorful.config(this)
//                .translucent(true)
//                .apply();

        //load headerView's image
        ImageView head = navView.getHeaderView(0).findViewById(R.id.headImage);
        Imager.load(this, R.drawable.head, head);
        head.setOnClickListener(new View.OnClickListener() {
            int index;
            long now;
            long lastTime;

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + now + " - " + lastTime);
                now = new Date().getTime();
                if (now - lastTime < 500) {
                    if (index < 3) {
                        index++;
                    } else {
                        ToastUtils.showShort(R.string.head_view_hint);
                    }
                }
                lastTime = now;
            }
        });
        navView.setNavigationItemSelectedListener(this);
        boolean isSecretOn = SpUtil.getBoolean(SettingFragment.SECRET_MODE);
        navView.inflateMenu(R.menu.menu_main);
//        navView.setCheckedItem(R.id.nav_beauty);
        Menu menu = navView.getMenu();
        menu.getItem(0).setChecked(true);
        menu.getItem(0).setIcon(new IconicsDrawable(this).
                icon(GoogleMaterial.Icon.gmd_face)
                .color(ContextCompat.getColor(this, R.color.pink)));

        menu.getItem(1).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_collections));

        menu.getItem(2).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_favorite)
                .color(Color.RED));

        Menu sub = menu.getItem(3).getSubMenu();
        sub.getItem(0).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_share)
                .color(Color.DKGRAY));
        sub.getItem(1).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_settings)
                .color(Color.GRAY));
    }

    private void initFragments(Bundle savedInstanceState) {
        if (fragmentSparseArray == null) {
            String[] titles, types;
            fragmentSparseArray = new SparseArray<>();
            String[] all = getResources().getStringArray(R.array.db_titles);

//            secretMode = true;
            Log.d(TAG, "initFragments: secret " + secretMode + " " + SpUtil.getString("deviceId"));
            if (secretMode) {
                //Gank & Douban
                titles = all;
                types = new String[]{TYPE_GANK, TYPE_DB_RANK, TYPE_DB_BREAST,
                        TYPE_DB_BUTT, TYPE_DB_LEG, TYPE_DB_SILK};
            } else {
                titles = new String[]{all[0]};
                types = new String[]{TYPE_GANK};
            }
            fragmentSparseArray.put(R.id.nav_beauty, MainTabsFragment.newInstance(titles, types));

            //二次元
            titles = getResources().getStringArray(R.array.a_titles);
            types = new String[]{TYPE_A_ANIME, TYPE_A_FULI, TYPE_A_HENTAI,
                    TYPE_A_UNIFORM, TYPE_A_ZATU};
            fragmentSparseArray.put(R.id.nav_a, MainTabsFragment.newInstance(titles, types));
            //favorite
            fragmentSparseArray.put(R.id.nav_favorite, new FavoriteFragment());
        }
        setMainFragment(R.id.nav_beauty, fragmentSparseArray, savedInstanceState == null);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_setting:
                // FIXME: 2018/1/28 这里还存在SettingFragment问题,先把下面问题看了先
                new Handler().postDelayed(() -> {
                    changeDrawer(false);
                    startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                }, DRAWER_CLOSE_DELAY);
                break;
            case R.id.nav_share:
                String text = SpUtil.get(Updater.SHARE_APP, getString(R.string.share_app_description));
                Sharer.shareText(this, text);
                break;
            default:
                currentMenu = item;
                setToolbarTitle(getCurrentMenuTitle());
                switchMenu(id, fragmentSparseArray);
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    public void changeDrawer(boolean enable) {
        //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动
        drawerLayout.setDrawerLockMode(enable ?
                DrawerLayout.LOCK_MODE_UNLOCKED : LOCK_MODE_LOCKED_CLOSED);

    }

    public String getCurrentMenuTitle() {
        if (currentMenu == null) {
            currentMenu = navView.getMenu().getItem(0);
        }
        return currentMenu.getTitle().toString();
    }

    /**
     * DrawerLayout侧滑菜单 - summerjing - 博客园
     * https://www.cnblogs.com/blogljj/p/5016588.html?utm_source=tuicool&utm_medium=referral
     */
    public void changeNavigator(boolean enable) {
        if (toggle == null) return;
        if (enable) {
            toggle.setDrawerIndicatorEnabled(true);//true的时候会走系统的逻辑，展示的是系统图标
        } else {
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        doublePressBackToQuit();
    }

    /**
     * 双击退出
     */
    private void doublePressBackToQuit() {
        if (backPressed) {
            super.onBackPressed();
            return;
        }
        backPressed = true;
        SnackUtil.showSnack(root, R.string.leave_app);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressed = false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        if (updater != null) updater.release();
        super.onDestroy();
    }
}
