package com.uprint.android_pack.cloudprint4androidmanager.network.biz;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import com.uprint.android_pack.cloudprint4androidmanager.network.CommonHandler;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;

import java.util.Map;

/**
 * Created by zhangxiaang on 15/10/25.
 */
public class CommonPostBiz extends CommonHandler {

    private Context context;
    private boolean flag;

    public CommonPostBiz(Context mContext) {
        super(mContext);
        this.context = mContext;
    }

    @Override
    public void handleMainMessage(Message msg) {
        if (flag) {
            Toast.makeText(this.context, "发送成功", Toast.LENGTH_SHORT).show();
        }
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
        getNetHelper().postAsJson(url, param_map);
    }


    public void isOauth(boolean flag) {
        this.flag = flag;
    }
}
