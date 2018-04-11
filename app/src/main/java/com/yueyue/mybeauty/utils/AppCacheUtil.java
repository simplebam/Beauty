package com.yueyue.mybeauty.utils;

import android.content.Context;
import android.os.Environment;

/**
 * author : yueyue on 2018/1/10 15:09
 * desc   :
 * quote  : http://www.cnblogs.com/zhaoyanjun/p/4530155.html
 */

public class AppCacheUtil {

    /**
     * 获得App缓存路径
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        //Environment.getExtemalStorageState() 获取SDcard的状态
        //Environment.MEDIA_MOUNTED 手机装有SDCard,并且可以进行读写
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //  此时获取到这个路径   /sdcard/Android/data/<application package>/cache
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            //  SD卡不存在获取到这个路径  /data/data/<application package>/cache
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
