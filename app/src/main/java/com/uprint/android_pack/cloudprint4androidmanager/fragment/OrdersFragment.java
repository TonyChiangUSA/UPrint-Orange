package com.uprint.android_pack.cloudprint4androidmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.activity.DetailOrderActivity;
import com.uprint.android_pack.cloudprint4androidmanager.adapter.OrdersListAdapter;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonGetBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.EmptyViewUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.ItemClickSupport;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;
import com.uprint.android_pack.cloudprint4androidmanager.widgets.EmptyRecyclerView;

import java.util.HashMap;

/**
 * Created by zhangxiaang on 15/10/7.
 * TODO data save before recycled
 * 检查下adapter 的数据顺序吧
 */
public class OrdersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private EmptyRecyclerView recyclerView;
    private LinearLayoutManager manager;
    private OrdersListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String token;
    private HashMap undelivered_params;
    private boolean hasToken;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        undelivered_params = new HashMap<>();
        SharedPreferenceUtil.checkToken(getActivity());
        token = SharedPreferenceUtil.get_Token(getActivity());
        undelivered_params.put("token", token);
        if (token != "") {
            hasToken = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.order_page, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        TextView emptyView = (TextView) view.findViewById(R.id.empty_view);
        recyclerView.setEmptyView(emptyView);
        //todo checkout the ItemAnimation result of the white gap
//        recyclerView.setItemAnimator(new SlideInOutRightItemAnimator(recyclerView));
        if (hasToken) {
            EmptyViewUtil.showLoadingDialog(getActivity(), "加载订单中...", false);
            CommonGetBiz biz = new CommonGetBiz(getActivity());
            biz.request(new ICallBack() {
                @Override
                public void displayResult(int status, Object... params) {
                    EmptyViewUtil.closeLoadingDialog();
                    JSONObject result = (JSONObject) params[0];
                    if (result.getInteger("error") == 0) {
                        if (result.getJSONArray("results").size() != 0) {
                            adapter = new OrdersListAdapter(getActivity(), result.getJSONArray("results"));
                            SharedPreferenceUtil.setUserOrderFirstId(getActivity().
                                    getApplicationContext(), result.getJSONArray("results").getJSONObject(0).getInteger("ID"));
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(getActivity(), "当前无最新订单", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }, NetValue.GET_ORDERS(), undelivered_params);
        }
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (adapter.getJsonObject(position) != null) {
                    int orderId = adapter.getJsonObject(position).getInteger("ID");
                    Intent intent = new Intent(getActivity(), DetailOrderActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("nickname", adapter.getJsonObject(position).getString("nickname"));
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_right);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //todo get data
            String result = savedInstanceState.getString("adapterValue", "");
            if (result.length() > 0) {
                if (adapter != null) {
                    adapter.clear();
                }
                adapter = new OrdersListAdapter(getActivity(), JSON.parseArray(result));
                recyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                HashMap map = undelivered_params;
                map.put("firstID", SharedPreferenceUtil.getUserOderFirstId(getActivity()));
                CommonGetBiz biz = new CommonGetBiz(getActivity());
                biz.request(new ICallBack() {
                    @Override
                    public void displayResult(int status, Object... params) {
                        JSONObject result = (JSONObject) params[0];
                        if (result.getInteger("error") == 0) {
                            if (result.getJSONArray("results").size() != 0) {
                                int first_id = result.getJSONArray("results").getJSONObject(0).getInteger("ID");
                                SharedPreferenceUtil.setUserOrderFirstId(getActivity(), first_id);

                                if (adapter == null) {
                                    adapter = new OrdersListAdapter(getActivity(), result.getJSONArray("results"));
                                } else
                                    adapter.addAll(result.getJSONArray("results"));
                                int size = result.getJSONArray("results").size();
                                recyclerView.setAdapter(adapter);
                                Toast.makeText(getActivity(), "获取到" + size + "单", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getActivity(), "当前没有最新订单啦", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, NetValue.GET_ORDERS(), map);
            }
        }, 2000);
    }

    public boolean isRefreshing() {
        if (swipeRefreshLayout != null) {
            return swipeRefreshLayout.isRefreshing();
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            outState.putString("adapterValue", adapter.getData().toString());
        }
    }
}
