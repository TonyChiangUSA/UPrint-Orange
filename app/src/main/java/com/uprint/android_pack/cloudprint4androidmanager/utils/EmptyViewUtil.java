package com.uprint.android_pack.cloudprint4androidmanager.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by zhangxiaang on 15/10/14.
 */
public class EmptyViewUtil {
    private static ProgressDialog dialog;
    private static boolean allow_animate = true;

    /**
     * 显示加载中对话框
     */
    public static void showLoadingDialog(Context context, String message, boolean isCancelable) {
        if (allow_animate) {
            dialog = ProgressDialog.show(context, message, "Connecting...", true);
            dialog.setCancelable(isCancelable);
            dialog.show();
        }
    }

    /**
     * 关闭加载对话框
     */
    public static void closeLoadingDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog.cancel();
            }
        }
        dialog = null;
    }

    public static void disallowed() {
        allow_animate = false;
    }

    public static void allowed() {
        allow_animate = true;
    }
}
