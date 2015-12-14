package com.uprint.android_pack.cloudprint4androidmanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.uprint.android_pack.cloudprint4androidmanager.utils.EmptyViewUtil;

/**
 * Created by zhangxiaang on 15/11/15.
 */
public class NetWorkBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            EmptyViewUtil.disallowed();
            EmptyViewUtil.closeLoadingDialog();
            Toast.makeText(context, "网络不可以用", Toast.LENGTH_SHORT).show();
        } else {
            EmptyViewUtil.allowed();
        }

    }
}
