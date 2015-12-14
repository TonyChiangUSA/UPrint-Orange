package com.uprint.android_pack.cloudprint4androidmanager.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by zhangxiaang on 15/11/27.
 * 获取本地app版本的工具
 */
public class CPackageManageUtils {
    public static int getAppVersion(Context context) {
        int version = -1;
        try {
            version = context.getPackageManager().getPackageInfo("com.uprint.android_pack.cloudprint4androidmanager", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("NameNotFoundException", e.toString());
        }
        return version;
    }

    public static String getAppVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    "com.uprint.android_pack.cloudprint4androidmanager", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("NameNotFoundException", e.getMessage());
        }
        return verName;
    }
}
