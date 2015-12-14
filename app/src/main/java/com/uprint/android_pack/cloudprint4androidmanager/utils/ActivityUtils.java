package com.uprint.android_pack.cloudprint4androidmanager.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.uprint.android_pack.cloudprint4androidmanager.CPApplication;
import com.uprint.android_pack.cloudprint4androidmanager.activity.MainActivity;

/**
 * Created by zhangxiaang on 15/9/27.
 */
public class ActivityUtils {
    /**
     * get main activity
     *
     * @return
     */
    public static Class<?> getMainActivity() {
        return MainActivity.class;
    }

    /**
     * @param paramObject
     * @param paramIntent
     * @param paramInt
     * @param paramString for log all the startActivity info of paramIntent
     */
    public static void logStartActivity(Object paramObject, Intent paramIntent, int paramInt, String paramString) {
        String str = paramObject.getClass().getName();
        StringBuilder builder = new StringBuilder(paramString);
        builder.append("\n  caller:").append(str);

        if (paramIntent.getClass() != null)
            builder.append("\n class:").append(paramIntent.getClass().toString());
        if (paramIntent.getComponent() != null)
            builder.append("\n component:").append(paramIntent.getComponent().toString());
        if (paramIntent.getPackage() != null)
            builder.append("\n Package:").append(paramIntent.getPackage());
        if (paramIntent.getAction() != null)
            builder.append("\n Action:").append(paramIntent.getAction());
        if (paramIntent.getType() != null)
            builder.append("\n Action:").append(paramIntent.getType());
        if (paramInt != 0)
            builder.append("\n requestCode:").append(paramInt);

        Log.d("logStartActivity", builder.toString());
    }

    /**
     * for package the start action
     *
     * @param paramActivity
     * @param paramIntent
     */
    public static void startActivity(Activity paramActivity, Intent paramIntent) {
        startActivity(paramActivity, paramIntent, 0);
    }

    public static void startActivity(Activity paramActivity, Intent paramIntent, int requestCode) {
        try {
            logStartActivity(paramActivity, paramIntent, requestCode, "start activity");
            paramIntent.setFlags(paramIntent.getFlags() | 0x20000000);
            paramActivity.startActivity(paramIntent);
        } catch (Exception e) {
            Log.e("startActivityError", e.getMessage());
        }
    }

    public static void startActivityForResult(Activity paramActivity, Intent paramIntent, int paramInt) {
        logStartActivity(paramActivity, paramIntent, paramInt, "Start activity for result:");
        paramIntent.setFlags(paramIntent.getFlags() | 0x20000000);
        paramActivity.startActivityForResult(paramIntent, paramInt);
    }

    public static void startInternalActivity(Activity paramActivity, Intent paramIntent) {
        paramIntent.setPackage(CPApplication.getmInstance().getPackageName());
        startActivity(paramActivity, paramIntent);
    }

    public static void jumpToLoginPage(Activity paramActivity, Intent paramIntent) {
        SharedPreferenceUtil.clear_Token(paramActivity);
        startActivity(paramActivity, paramIntent);
    }

}
