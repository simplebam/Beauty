package com.yueyue.mybeauty.base;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import timber.log.Timber;

/**
 * author : yueyue on 2018/1/4 08:19
 * desc   : 管理Log
 * quote  : https://www.jianshu.com/p/39834be3cb6c
 */

public class FileLoggingTree extends Timber.DebugTree {

    private static final String TAG = FileLoggingTree.class.getSimpleName();

    private String mCacheDirPath;

    public FileLoggingTree(String cacheDirPath) {
        this.mCacheDirPath = cacheDirPath;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (TextUtils.isEmpty(mCacheDirPath)) {
            return;
        }
        File file = new File(mCacheDirPath + "/log.txt");
        Log.v("dyp", "file.path:" + file.getAbsolutePath() + ",message:" + message);
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(message);
            bufferedWriter.flush();
        } catch (IOException e) {
            Log.e(TAG, "存储文件失败:" + e.toString());
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    Log.e(TAG, "存储文件失败" + e.toString());
                }
            }
        }
    }

}


