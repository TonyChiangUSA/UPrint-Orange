package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uprint.android_pack.cloudprint4androidmanager.CPBaseActivity;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.adapter.ArrivedOrdersAdapter;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonGetBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.EmptyViewUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by tonychiang on 15/11/15.
 */
public class CPArrivedOrdersActivity extends CPBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView title;
    private RecyclerView recyclerView;
    private ArrivedOrdersAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrived_orders_layout);
        EmptyViewUtil.showLoadingDialog(this, "正在加载已配送订单", false);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        init();

        HashMap param_map = getTokenMap();
        param_map.put("type", 3);
        //todo 给的数据也是降序的
        CommonGetBiz biz = new CommonGetBiz(this);
        biz.request(new ICallBack() {
            @Override
            public void displayResult(int status, Object... params) {
                JSONObject result = (JSONObject) params[0];
                EmptyViewUtil.closeLoadingDialog();
                if (result.getInteger("error") == 0) {
                    JSONArray array = result.getJSONArray("results");
                    if (array.size() == 0) {
                        Toast.makeText(CPArrivedOrdersActivity.this, "您尚未配送订单", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SharedPreferenceUtil.setUserArrivedOrderFirstId(CPArrivedOrdersActivity.this
                            , array.getJSONObject(0).getInteger("ID"));
                    adapter = new ArrivedOrdersAdapter(CPArrivedOrdersActivity.this, array);
                    recyclerView.setAdapter(adapter);
                }
            }
        }, NetValue.GET_ORDERS(), param_map);
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        title.setText("已配送订单");
        title.setVisibility(View.VISIBLE);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                final WeakReference<Activity> ref = new WeakReference<Activity>(CPArrivedOrdersActivity.this);
                HashMap map = getTokenMap();
                map.put("type", 3);
                map.put("firstID", SharedPreferenceUtil.getUserArrivedOderFirstId(ref.get()));
                CommonGetBiz biz = new CommonGetBiz(ref.get());
                biz.request(new ICallBack() {
                    @Override
                    public void displayResult(int status, Object... params) {
                        JSONObject result = (JSONObject) params[0];
                        if (result.getInteger("error") == 0) {
                            if (result.getJSONArray("results").size() != 0) {
                                int first_id = result.getJSONArray("results").getJSONObject(0).getInteger("ID");
                                SharedPreferenceUtil.setUserArrivedOrderFirstId(ref.get(), first_id);
                                if (adapter == null) {
                                    adapter = new ArrivedOrdersAdapter(ref.get(), result.getJSONArray("results"));
                                } else
                                    adapter.addAll(result.getJSONArray("results"));
                                recyclerView.setAdapter(adapter);
                            } else
                                Toast.makeText(ref.get(), "当前已是所有的配送订单", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, NetValue.GET_ORDERS(), map);
            }
        }, 2000);
    }
}
