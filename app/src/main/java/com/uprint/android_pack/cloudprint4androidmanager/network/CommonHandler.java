package com.uprint.android_pack.cloudprint4androidmanager.network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.RedirectError;
import com.android.volley.TimeoutError;
import com.uprint.android_pack.cloudprint4androidmanager.utils.EmptyViewUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;

import java.util.Map;

/**
 * Created by zhangxiaang on 15/10/13.
 */
public abstract class CommonHandler extends Handler {
    public static final String TAG = "CommonHandler";
    protected Context mContext;
    protected ICallBack mCallBack;
    protected VolleyHelper mNetHelper;

    public CommonHandler(Context mContext) {
        this.mContext = mContext;
    }

    public VolleyHelper getNetHelper() {
        if (mNetHelper == null) {
            mNetHelper = new VolleyHelper(this, mContext);
        }
        return mNetHelper;
    }

    public abstract void handleMainMessage(Message msg);

    public abstract void request(ICallBack callBack, String url);

    public abstract void request(ICallBack callBack, String url, Map<String, String> param_map);

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case NetValue.STATUS_TIMEOUT:
                showToast("time_out");
                break;
            case NetValue.STATUS_NO_NETWORK:
                showToast("无网络");
                break;
            case NetValue.STATUS_URL_ERROR:
                showToast(msg.toString());
                break;
            case NetValue.NOT_LOGIN:
                SharedPreferenceUtil.clear_Token(mContext);
                showToast("请重新登录");
                SharedPreferenceUtil.checkToken(mContext);
                break;
            case NetValue.NO_CONNECTION:
                showToast("后台无链接");
                break;
            case NetValue.REDIRECT_ERROR:
                showToast("重定向错误");
                break;
            case NetValue.SERVER_ERROR:
                Log.e(TAG, "后台服务错误");
                break;
            default:
                handleMainMessage(msg);
                break;
        }
    }

    public void createExceptionMsg(Exception exc) {
        Message msg = this.obtainMessage();
        if (exc instanceof AuthFailureError) {
            msg.what = NetValue.NOT_LOGIN;
            msg.obj = NetValue.TIP_SHOULD_LOGIN;
        }
        if (exc instanceof NetworkError) {
            msg.what = NetValue.STATUS_URL_ERROR;
            msg.obj = NetValue.TIP_URL_ERROR;
        }
        if (exc instanceof TimeoutError) {
            msg.what = NetValue.STATUS_TIMEOUT;
            msg.obj = NetValue.TIP_TIMEOUT;
        }
        if (exc instanceof NoConnectionError) {
            msg.what = NetValue.NO_CONNECTION;
            msg.obj = NetValue.TIP_NO_CONNECTION;
        }
        if (exc instanceof RedirectError) {
            msg.what = NetValue.REDIRECT_ERROR;
            msg.obj = NetValue.TIP_REDIRECT_ERROR;
        } else {
            msg.what = NetValue.SERVER_ERROR;
            msg.obj = NetValue.TIP_SERVER_ERROR;
        }
        sendMessage(msg);
    }

    private void showToast(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
        EmptyViewUtil.closeLoadingDialog();
        Log.e(TAG, "网络异常:" + content);
    }
}
