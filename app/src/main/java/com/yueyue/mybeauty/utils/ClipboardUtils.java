package com.yueyue.mybeauty.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.blankj.utilcode.util.Utils;


/**
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/9/25
 * desc  : 剪贴板相关工具类
 *         目前项目中用到的blankj.utilcode中的1.1版本已经剔除了该类,唯有在以前版本中copy一份过来了
 */

public class ClipboardUtils {

    private ClipboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 复制文本到剪贴板
     *
     * @param text 文本
     */
    public static void copyText(CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) throw new NullPointerException("clipboard is null");
        clipboard.setPrimaryClip(ClipData.newPlainText("text", text));
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static CharSequence getText() {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) throw new NullPointerException("clipboard is null");
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(Utils.getApp());
        }
        return null;
    }

    /**
     * 复制uri到剪贴板
     *
     * @param uri uri
     */
    public static void copyUri(Uri uri) {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) throw new NullPointerException("clipboard is null");
        clipboard.setPrimaryClip(ClipData.newUri(Utils.getApp().getContentResolver(), "uri", uri));
    }

    /**
     * 获取剪贴板的uri
     *
     * @return 剪贴板的uri
     */
    public static Uri getUri() {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) throw new NullPointerException("clipboard is null");
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     *
     * @param intent 意图
     */
    public static void copyIntent(Intent intent) {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) throw new NullPointerException("clipboard is null");
        clipboard.setPrimaryClip(ClipData.newIntent("intent", intent));
    }

    /**
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    public static Intent getIntent() {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) throw new NullPointerException("clipboard is null");
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }
}