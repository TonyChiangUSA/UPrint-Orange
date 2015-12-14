package com.uprint.android_pack.cloudprint4androidmanager.network.biz;

import android.content.Context;
import android.os.Message;

import com.uprint.android_pack.cloudprint4androidmanager.network.CommonHandler;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;

import java.util.Map;

/**
 * Created by tonychiang on 15/10/25.
 */
public class CommonGetBiz extends CommonHandler {
    private Context context;

    public CommonGetBiz(Context mContext) {
        super(mContext);
        this.context = mContext;
    }

    @Override
    public void handleMainMessage(Message msg) {
        mCallBack.displayResult(ICallBack.SUCCESS, msg.obj);
    }

    @Override
    public void request(ICallBack callBack, String url) {
        this.mCallBack = callBack;
        getNetHelper().getAsString(url);
    }

    @Override
    public void request(ICallBack callBack, String url, Map<String, String> param_map) {
        this.mCallBack = callBack;
        getNetHelper().getWithParams(url, param_map);
    }

}
