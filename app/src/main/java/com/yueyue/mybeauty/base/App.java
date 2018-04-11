package com.yueyue.mybeauty.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.bugtags.library.Bugtags;
import com.yueyue.mybeauty.utils.AppCacheUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.BuildConfig;
import timber.log.Timber;

/**
 * author : yueyue on 2017/12/25
 * desc   : Custom application for libs init etc.
 */

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        //使用blankj的android-utils-code的,这里进行初始化
        Utils.init(this);

        //初始化Bugtags
        Bugtags.start("7eaf09370377125f4ba358eaa3a776a9",
                this,
                Bugtags.BTGInvocationEventBubble);

        //初始化Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        //在这里先使用Timber.plant注册一个Tree，然后调用静态的.d .v 去使用
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new FileLoggingTree(AppCacheUtil.getDiskCacheDir(this)));
        }
    }


    public static Context getAppContext() {
        return context;
    }
}
