package com.yueyue.mybeauty.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;

import com.blankj.utilcode.util.ToastUtils;
import com.yueyue.mybeauty.BuildConfig;
import com.yueyue.mybeauty.R;
import com.yueyue.mybeauty.base.App;
import com.yueyue.mybeauty.config.Constants;

import java.util.List;

import moe.feng.alipay.zerosdk.AlipayZeroSdk;

/**
 * Created by Dante on 2016/2/19.
 */
public class AppUtil {

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static void openAppInfo(Context context) {
        //redirect user to app Settings
        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getApplicationContext().getPackageName()));
        context.startActivity(i);
    }

    public static boolean isIntentSafe(Intent intent) {
        PackageManager packageManager = App.getAppContext().getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }

    public static void goMarket(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID));
            activity.startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.coolapk.com/apk/" + BuildConfig.APPLICATION_ID));
            activity.startActivity(intent);
        }
    }


    public static void donate(final Activity activity) {
        if (AlipayZeroSdk.hasInstalledAlipayClient(activity.getApplicationContext())) {
            String deviceId = SpUtil.getString("deviceId");
            ClipboardUtils.copyText(deviceId);
            ToastUtils.showShort(R.string.device_id_copied);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlipayZeroSdk.startAlipayClient(activity, Constants.ALI_PAY);
                }
            },500);
        } else {
            ToastUtils.showShort(R.string.alipay_not_found);
        }
    }
}
