package com.yueyue.mybeauty.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yueyue.mybeauty.BuildConfig;
import com.yueyue.mybeauty.R;
import com.yueyue.mybeauty.config.Constants;
import com.yueyue.mybeauty.model.AppInfo;
import com.yueyue.mybeauty.net.API;
import com.yueyue.mybeauty.net.NetService;
import com.yueyue.mybeauty.ui.SettingFragment;
import com.yueyue.mybeauty.utils.AppUtil;
import com.yueyue.mybeauty.utils.SpUtil;

import java.util.Arrays;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Update app helper.
 */

public class Updater {
    public static final String SHARE_APP = "share_app";
    private static final String TAG = "Updater";
    private static CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final Activity context;
    private DownloadHelper helper;

    private Updater(Activity context) {
        this.context = context;
    }

    public static Updater getInstance(Activity context) {
        return new Updater(context);

    }

    private void downloadAndInstall(final AppInfo appInfo) {
        Subscription subscription = new RxPermissions(context)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .filter(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean) {
                        return aBoolean;
                    }
                }).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        String url = API.DOWNLOAD_BASE + "/" + appInfo.getVersion() +
                                "/" + appInfo.getApkName();
                        helper = new DownloadHelper(context, url);
                        helper.downWithDownloadManager(getApkName(appInfo.getVersion()),
                                getApkName(appInfo.getFormerVersion()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });
        compositeSubscription.add(subscription);
    }

    public void check() {
        Subscription subscription = new RxPermissions(context)
                .request(Manifest.permission.READ_PHONE_STATE)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (SpUtil.getString("deviceId").isEmpty()) {
                            if (aBoolean) {
                                String device = EncryptUtils.encryptMD5ToString(PhoneUtils.getIMEI(), Constants.DANTE);
                                SpUtil.save("deviceId", device);
                            } else {
                                ToastUtils.showShort(R.string.permission_statement);
                            }
                        }

                    }
                });

        compositeSubscription.add(subscription);

        NetService.getInstance(API.GITHUB_RAW).getAppApi().getAppInfo()
                .filter(new Func1<AppInfo, Boolean>() {
                    @Override
                    public Boolean call(AppInfo appInfo) {
                        Log.d(TAG, "check: " + appInfo.toString());
                        SpUtil.save("vip", Arrays.toString(appInfo.getVip().toArray()));
                        return appInfo.getVersionCode() > BuildConfig.VERSION_CODE;//版本有更新
                    }
                })
                .doOnNext(new Action1<AppInfo>() {
                    @Override
                    public void call(AppInfo appInfo) {
                        SpUtil.save(Updater.SHARE_APP, appInfo.getShareApp());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AppInfo>() {
                    @Override
                    public void call(AppInfo appInfo) {
                        showDialog(appInfo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

//        NetService.getInstance(API.GITHUB_RAW).getAppApi().getAppInfo()
//                .filter(appInfo -> {
//                    Log.d(TAG, "check: " + appInfo.toString());
//                    SpUtil.save("vip", Arrays.toString(appInfo.getVip().toArray()));
//                    return appInfo.getVersionCode() > BuildConfig.VERSION_CODE;//版本有更新
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::showDialog, Throwable::printStackTrace);
    }

    private void showDialog(final AppInfo appInfo) {
        boolean needUpdate = appInfo.isForceUpdate() || SpUtil.getBoolean(SettingFragment.CHECK_VERSION);
//                .setCancelable(!needUpdate)//需要更新就不可取消
        new AlertDialog.Builder(context).setTitle(R.string.new_version)
                .setMessage(String.format(context.getString(R.string.update_message), appInfo.getMessage()))
                .setNeutralButton(R.string.go_market, (dialog, which) -> AppUtil.goMarket(context))
                .setPositiveButton(R.string.update, (dialog, which) -> downloadAndInstall(appInfo)).show();
    }

    private String getApkName(String version) {
        return "Beauty_" + version + ".apk";
    }

    public void release() {
        if (helper != null) {
            helper.release();
        }
        compositeSubscription.unsubscribe();
    }
}
