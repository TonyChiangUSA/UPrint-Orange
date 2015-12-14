package com.uprint.android_pack.cloudprint4androidmanager.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import java.lang.ref.WeakReference;


/**
 * Created by zhangxiaang on 15/10/13.
 */
public class NetworkUtil {
    public static boolean checkNetWork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager == null) {
            noNetWork(context);
            return false;
        } else {
            NetworkInfo[] infos = connManager.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo info : infos) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
            noNetWork(context);
            return false;
        }
    }

    public static void noNetWork(Context context) {
        final WeakReference<Context> ref = new WeakReference<>(context);
        EmptyViewUtil.disallowed();
        new AlertDialog.Builder(context)
                .setMessage("当前无网络服务")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ref.get().startActivity(new Intent(
                                android.provider.Settings.ACTION_WIFI_SETTINGS));

                    }
                }).create().show();
    }
}
