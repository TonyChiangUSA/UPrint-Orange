package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.PushService;
import com.uprint.android_pack.cloudprint4androidmanager.CPBaseActivity;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.fragment.MineFragment;
import com.uprint.android_pack.cloudprint4androidmanager.fragment.OrdersFragment;
import com.uprint.android_pack.cloudprint4androidmanager.widgets.MenuItemLayout;

public class MainActivity extends CPBaseActivity {
    public static final String TAG = "MainActivity";
    private long mPressedTime;
    private ViewGroup navigation;
    private MenuItemLayout myOrders;
    private MenuItemLayout mine;
    private OrdersFragment ordersFragment;
    private MineFragment mineFragment;

    private TextView title;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushService.setDefaultPushCallback(this, MainActivity.class);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            ordersFragment = (OrdersFragment) fragmentManager.getFragment(savedInstanceState, "ordersFragment");
            mineFragment = (MineFragment) fragmentManager.getFragment(savedInstanceState, "mineFragment");
        }
        init();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        title.setText("未配送");
        title.setVisibility(View.VISIBLE);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        navigation = (ViewGroup) findViewById(R.id.bottom_navigation);
        myOrders = (MenuItemLayout) navigation.findViewById(R.id.myOrders);
        mine = (MenuItemLayout) navigation.findViewById(R.id.mine);
        myOrders.onPressed();

        initFragment(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO 当重建的时候所有的view都要恢复默认状态
    }

    private void initFragment(int postion) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (postion) {
            case 0:
                if (ordersFragment == null) {
                    ordersFragment = new OrdersFragment();
                    transaction.add(R.id.contentFragment, ordersFragment, "ordersFragment");
                } else {
                    transaction.show(ordersFragment);
                }
                break;
            case 1:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.contentFragment, mineFragment, "mineFragment");
                } else {
                    transaction.show(mineFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (ordersFragment != null) {
            transaction.hide(ordersFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    public void route(View view) {
        int layoutId = view.getId();
        switch (layoutId) {
            case R.id.myOrders:
                myOrders.onPressed();
                mine.resume();
                title.setText("未配送");
                initFragment(0);
                break;
            case R.id.mine:
                //void not completed but change
                if (ordersFragment.isRefreshing()) {
                    Toast.makeText(this, "正在获取订单...", Toast.LENGTH_SHORT).show();
                    break;
                }
                mine.onPressed();
                myOrders.resume();
                title.setVisibility(View.VISIBLE);
                title.setText("我的");
                initFragment(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
            return;
        }
        Toast.makeText(this, "再点一下退出", Toast.LENGTH_SHORT).show();
        mPressedTime = System.currentTimeMillis();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (ordersFragment != null) {
            fragmentManager.putFragment(outState, "ordersFragment", ordersFragment);
        }
        if (mineFragment != null) {
            fragmentManager.putFragment(outState, "mineFragment", mineFragment);
        }
    }

    public void call_user(View view) {
        int resId = view.getId();
        if (resId == R.id.user_phone) {
            TextView tView = (TextView) view;
            final String phoneNum = tView.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确认拨打给" + phoneNum + "吗?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent phoneIntent = new Intent("android.intent.action.CALL",
                            Uri.parse("tel:" + phoneNum));
                    startActivity(phoneIntent);
                }
            }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
    }
}
