package com.uprint.android_pack.cloudprint4androidmanager;

import android.app.ActionBar;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.uprint.android_pack.cloudprint4androidmanager.receiver.NetWorkBroadCast;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by zhangxiaang on 15/10/7.
 */

public class CPBaseActivity extends AppCompatActivity {
    public static final String TAG = "CPBaseActivity";
    private NetWorkBroadCast myReceiver;

    public boolean isDestoryed = false;
    protected Toolbar mToolbar;
    protected Intent mIntent;
    protected EventBus uiBus = EventBus.builder()
            .eventInheritance(false)
            .logSubscriberExceptions(BuildConfig.DEBUG)
            .throwSubscriberException(BuildConfig.DEBUG)
            .sendSubscriberExceptionEvent(false)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setIcon(android.R.color.transparent);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.theme_color_primary_dark));
        }
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        this.isDestoryed = true;
        unRegisterReceiver();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //subPage just return last page
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public EventBus getUiBus() {
        return this.uiBus;
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new NetWorkBroadCast();
        this.registerReceiver(myReceiver, filter);
    }

    public HashMap getTokenMap() {
        HashMap params_map = new HashMap<>();
        SharedPreferenceUtil.checkToken(this);
        params_map.put("token", SharedPreferenceUtil.get_Token(this));
        return params_map;
    }

    private void unRegisterReceiver() {
        this.unregisterReceiver(myReceiver);
    }
}
