package com.uprint.android_pack.cloudprint4androidmanager.network;

import android.content.Context;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.RedirectError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uprint.android_pack.cloudprint4androidmanager.CPApplication;
import com.uprint.android_pack.cloudprint4androidmanager.utils.EmptyViewUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.MapFormatUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.NetworkUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangxiaang on 15/10/13.
 */
public class VolleyHelper {
    private RequestQueue requestQueue;
    private CommonHandler mHandler;
    private Context mContext;

    public VolleyHelper(CommonHandler mHandler, Context mContext) {
        this.mHandler = mHandler;
        this.mContext = mContext;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(CPApplication.getmInstance());
        }
        return requestQueue;
    }

    public void getAsString(String url) {
        if (!NetworkUtil.checkNetWork(mContext)) {
            mHandler.createExceptionMsg(new NetworkError());
            NetworkUtil.noNetWork(mContext);
            return;
        }
        EmptyViewUtil.allowed();
        StringRequest request = new StringRequest(Request.Method.GET, url
                , resListener, errorListener);
        getRequestQueue().add(request);
    }

    public void postAsJson(String url, Map<String, String> params_map) {
        if (!NetworkUtil.checkNetWork(mContext)) {
            NetworkUtil.noNetWork(mContext);
            mHandler.createExceptionMsg(new NetworkError());
            return;
        }
        EmptyViewUtil.allowed();
        JSONObject jsonObject = new JSONObject(params_map);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject
                , jresListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        getRequestQueue().add(request);
    }

    public void getWithParams(String url, final Map<String, String> request_params) {
        if (!NetworkUtil.checkNetWork(mContext)) {
            NetworkUtil.noNetWork(mContext);
            mHandler.createExceptionMsg(new NetworkError());
            return;
        }
        EmptyViewUtil.allowed();
        StringRequest request = new StringRequest(Request.Method.GET, MapFormatUtil.urlEncodeUTF8(url, request_params)
                , resListener, errorListener);
        getRequestQueue().add(request);
    }

    private Response.Listener<JSONObject> jresListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Message msg = mHandler.obtainMessage();
            msg.what = NetValue.STATUS_SUCCESS;
            msg.obj = JSON.parseObject(response.toString());
            mHandler.handleMessage(msg);
        }
    };

    private Response.Listener<String> resListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Message msg = mHandler.obtainMessage();
            msg.what = NetValue.STATUS_SUCCESS;
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);
            msg.obj = jsonObject;
            mHandler.handleMessage(msg);
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof TimeoutError) {
                mHandler.createExceptionMsg(new TimeoutError());
            }
            if (error instanceof NetworkError) {
                mHandler.createExceptionMsg(new NetworkError());
            }
            if (error instanceof AuthFailureError) {
                mHandler.createExceptionMsg(new AuthFailureError("请重新登录"));
            }

            if (error instanceof NoConnectionError) {
                mHandler.createExceptionMsg(new NoConnectionError());
            }
            if (error instanceof RedirectError) {
                mHandler.createExceptionMsg(new RedirectError());
            } else
                mHandler.createExceptionMsg(new ServerError());
        }
    };
}
