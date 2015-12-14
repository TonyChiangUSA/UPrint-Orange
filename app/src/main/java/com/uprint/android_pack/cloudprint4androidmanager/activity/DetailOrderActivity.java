package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.adapter.TaskListAdapter;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonGetBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.EmptyViewUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;
import com.uprint.android_pack.cloudprint4androidmanager.widgets.EmptyRecyclerView;
import com.uprint.android_pack.cloudprint4androidmanager.widgets.FullyLinearLayoutManager;

import java.util.HashMap;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangxiaang on 15/10/20.
 * todo 在数据获取到之前记得设置emptyView
 * 各个属性都更新一下
 */
public class DetailOrderActivity extends CPSwipeActivity {
    public static final String TAG = "DetailOrderActivity";
    private TextView title;

    @Bind(R.id.tasksList)
    EmptyRecyclerView tasksList;
    @Bind(R.id.feeinfo)
    TextView feeinfo;
    @Bind(R.id.comment)
    TextView comment;

    private int orderId;
    private String nick;
    private TaskListAdapter adapter;
    private HashMap param_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_order);
        ButterKnife.bind(this);
        setSwipeAnyWhere(true);
        orderId = getIntent().getIntExtra("orderId", -1);
        nick = getIntent().getStringExtra("nickname");
        EmptyViewUtil.showLoadingDialog(this, "", false);

        init();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.goback);

        title = (TextView) mToolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        SharedPreferenceUtil.checkToken(this);
        param_map = new HashMap();
        param_map.put("token", SharedPreferenceUtil.get_Token(this));
        param_map.put("order_id", orderId);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
        tasksList.setLayoutManager(manager);

        if (orderId != -1) {
            CommonGetBiz biz = new CommonGetBiz(this);
            biz.request(new ICallBack() {
                @Override
                public void displayResult(int status, Object... params) {
                    EmptyViewUtil.closeLoadingDialog();
                    title.setText(nick + "的订单详情");
                    title.setMaxEms(9);
                    JSONObject result = (JSONObject) params[0];
                    if (result.getInteger("error") == 0) {
                        JSONObject obj = result.getJSONArray("results").getJSONObject(0);
                        adapter = new TaskListAdapter(DetailOrderActivity.this, obj.getJSONArray("tasks"));
                        tasksList.setAdapter(adapter);
                        float fee = obj.getFloat("fee");
                        float deli_fee = obj.getFloat("delivery_fee");
                        feeinfo.setText(String.format(Locale.CHINESE, "%5s", "打印费用:") +
                                String.format("%.2f", fee) + "元" + "\n"
                                + String.format(Locale.CHINESE, "%5s", "配送费用:")
                                + String.format("%.2f", deli_fee) + "元"
                                + "\n" + String.format(Locale.CHINESE, "%5s", "总计:")
                                + String.format("%.2f", (fee + deli_fee)) + "元");

                        comment.setText(obj.getString("note"));
                    }
                }
            }, NetValue.DETAIL_ORDER(), param_map);
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
