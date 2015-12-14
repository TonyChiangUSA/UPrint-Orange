package com.uprint.android_pack.cloudprint4androidmanager.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uprint.android_pack.cloudprint4androidmanager.CPBaseActivity;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.activity.CPArrivedOrdersActivity;
import com.uprint.android_pack.cloudprint4androidmanager.activity.CPLoginActivity;
import com.uprint.android_pack.cloudprint4androidmanager.activity.ChangePasswordActivity;
import com.uprint.android_pack.cloudprint4androidmanager.activity.MineInfoActivity;
import com.uprint.android_pack.cloudprint4androidmanager.dataType.ArrivedOrders;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonGetBiz;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonPostBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.ActivityUtils;
import com.uprint.android_pack.cloudprint4androidmanager.utils.NetworkUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tonychiang on 15/10/7.
 */
public class MineFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_page_2, container, false);
        view.findViewById(R.id.mineIfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(getActivity(), new Intent(getActivity(), MineInfoActivity.class));
            }
        });
        view.findViewById(R.id.complains).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap params_map = ((CPBaseActivity) getActivity()).getTokenMap();
                params_map.put("lastID", SharedPreferenceUtil.getComplainsLastId(getActivity()));
                CommonGetBiz biz = new CommonGetBiz(getActivity());
                biz.request(new ICallBack() {
                    @Override
                    public void displayResult(int status, Object... params) {
                        JSONObject result = (JSONObject) params[0];
                        if (result.getInteger("error") == 0) {
                            JSONArray array = result.getJSONArray("results");
                            //TODO  获取到第一个obj的id作为最新的id  展示投诉的list
                            if (array.size() > 0) {
                                SharedPreferenceUtil.setUserComplainsLastId(getActivity(), array.getJSONObject(0).getInteger("ID"));
                                List<ArrivedOrders> results = new ArrayList<>();
                                for (int i = 0; i < array.size(); i++) {
                                    ArrivedOrders orders = JSON.parseObject(array.getJSONObject(i).toJSONString(), ArrivedOrders.class);
                                    results.add(orders);
                                }
                            } else
                                Toast.makeText(getActivity(), "您还没有收到任何投诉哟", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, NetValue.COMPLAINS(), params_map);
            }
        });
        view.findViewById(R.id.totalOrders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.checkNetWork(getActivity())) {
                    Intent intent = new Intent(getActivity(), CPArrivedOrdersActivity.class);
                    ActivityUtils.startActivity(getActivity(), intent);
                }

            }
        });
        view.findViewById(R.id.forgetPaswd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(getActivity(), new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });
        view.findViewById(R.id.unresigned).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("确认要辞职吗?")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CommonPostBiz biz = new CommonPostBiz(getActivity());
                                HashMap params_map = ((CPBaseActivity) getActivity()).getTokenMap();
                                biz.request(new ICallBack() {
                                    @Override
                                    public void displayResult(int status, Object... params) {
                                        JSONObject result = (JSONObject) params[0];
                                        if (result.getInteger("error") != 0) {
                                            Toast.makeText(getActivity(), result.getString("msg"), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, NetValue.RESIGN(), params_map);
                            }
                        }).setNeutralButton("点错啦", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("确认要退出吗?")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HashMap params_map = ((CPBaseActivity) getActivity()).getTokenMap();
                                CommonPostBiz biz = new CommonPostBiz(getActivity());
                                biz.request(new ICallBack() {
                                    @Override
                                    public void displayResult(int status, Object... params) {
                                        JSONObject result = (JSONObject) params[0];
                                        if (result.getInteger("error") == 0) {
                                            SharedPreferenceUtil.clear_Token(getActivity());
                                            SharedPreferenceUtil.checkToken(getActivity());
                                            SharedPreferenceUtil.clearUserOrderFirstId(getActivity());
                                            SharedPreferenceUtil.clearUserArrivedOrderFirstId(getActivity());
                                            ActivityUtils.startActivity(getActivity(), new Intent(getActivity(), CPLoginActivity.class));
                                        }
                                    }
                                }, NetValue.LOGOUT(), params_map);
                            }
                        }).setNeutralButton("点错啦", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        return view;
    }
}
