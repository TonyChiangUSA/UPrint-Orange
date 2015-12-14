package com.uprint.android_pack.cloudprint4androidmanager.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * Created by zhangxiaang on 15/9/25.
 */
public class IconfontTypeface {
    public static final String TAG = "IconfontTypeface";
    public static Typeface iconfont = null;

    public static Typeface getTypeface(final Context context) {
        if (iconfont == null) {
            try {
                iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return iconfont;
    }
}
