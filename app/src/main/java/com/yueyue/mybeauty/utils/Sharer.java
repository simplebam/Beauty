package com.yueyue.mybeauty.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.blankj.utilcode.util.IntentUtils;

/**
 * author : yueyue on 2018/1/28 15:06
 * desc   :
 */

public class Sharer {
    public static void shareText(Context context, String text) {
        context.startActivity(
                Intent.createChooser(IntentUtils.getShareTextIntent(text),
                        "分享到"));
    }

    public static void shareImage(Context context, Uri uri) {
        if (uri == null) {
            return;
        }
        context.startActivity(
                Intent.createChooser(IntentUtils.getShareImageIntent("", uri),
                        "分享到"));
    }
}
