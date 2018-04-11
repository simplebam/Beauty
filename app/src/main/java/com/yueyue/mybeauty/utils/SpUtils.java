package com.yueyue.mybeauty.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yueyue.mybeauty.base.App;

/**
 * author : yueyue on 2018/1/6 15:29
 * quote  : 1.封装SharedPreferences的工具类 - CSDN博客
 * http://blog.csdn.net/miaozhenzhong/article/details/53391831
 */

public class SpUtils {

    private static SharedPreferences sp = PreferenceManager
            .getDefaultSharedPreferences(App.getAppContext());

    private SpUtils() {
    }

    /**
     * 把数据保存到SharedPerference中
     */
    public static void put(String key, Object value) {
        //获取Editor对象
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        //提交保存数据
        editor.apply();
    }


    /**
     * 根据key和类型取出数据
     *
     * @param key
     * @return
     */
    public static Object getValue(String key, DataType type) {
        switch (type) {
            case INTEGER:
                return sp.getInt(key, -1);
            case FLOAT:
                return sp.getFloat(key, -1.0f);
            case BOOLEAN:
                return sp.getBoolean(key, false);
            case LONG:
                return sp.getLong(key, -1L);
            case STRING:
                return sp.getString(key, null);
            case STRING_SET:
                return sp.getStringSet(key, null);
            default: // 默认取出String类型的数据
                return null;
        }
    }

    public static enum DataType {
        INTEGER, FLOAT, BOOLEAN, LONG, STRING, STRING_SET
    }

}
