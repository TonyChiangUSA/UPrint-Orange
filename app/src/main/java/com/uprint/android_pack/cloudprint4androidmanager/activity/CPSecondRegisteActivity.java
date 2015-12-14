package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dd.processbutton.FlatButton;
import com.uprint.android_pack.cloudprint4androidmanager.CPBaseActivity;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonPostBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.ActivityUtils;
import com.uprint.android_pack.cloudprint4androidmanager.utils.MD5;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangxiaang on 15/10/10.
 */
public class CPSecondRegisteActivity extends CPBaseActivity {
    public static final String TAG = "CPSecondRegisteActivity";
    private TextView title;

    MaterialEditText password_1;
    MaterialEditText password_2;
    FlatButton registeBtn;
    private String phNumber;
    private String oauthCode;
    private boolean flag;

    private Map<String, String> params_map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_registe_2);

        phNumber = getIntent().getStringExtra("cellphone");
        oauthCode = getIntent().getStringExtra("oauthCode");
        flag = getIntent().getBooleanExtra("flag", false);
        init();
    }

    public void init() {
        params_map = new HashMap<>();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.goback);

        title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        title.setText("确认密码");
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        password_1 = (MaterialEditText) findViewById(R.id.newpaswd);
        password_2 = (MaterialEditText) findViewById(R.id.comfirm_paswd);
        registeBtn = (FlatButton) findViewById(R.id.registe);
        if (flag) {
            registeBtn.setText("提交新密码");
        } else registeBtn.setText("注册");
    }

    public void routes(View view) {
        int resId = view.getId();
        switch (resId) {
            case R.id.registe:
                if (!password_1.getText().toString().equals(password_2.getText().toString())) {
                    Toast.makeText(CPSecondRegisteActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (phNumber.trim().length() != 0 && oauthCode.trim().length() != 0) {
                    params_map.put("username", phNumber);
                    params_map.put("password", MD5.getMD5(password_1.getText().toString()));
                    params_map.put("smsCode", oauthCode);
                    CommonPostBiz biz = new CommonPostBiz(this);
                    biz.request(new ICallBack() {
                        @Override
                        public void displayResult(int status, Object... params) {
                            JSONObject result = (JSONObject) params[0];
                            Log.e(TAG, result.toString());
                            Log.e(TAG, params_map.toString());
                            if (result.getInteger("error") == 0) {
                                Toast.makeText(CPSecondRegisteActivity.this, flag ? "修改成功" : "注册成功", Toast.LENGTH_SHORT).show();
                                SharedPreferenceUtil.storage_Token(getApplicationContext(), result.getJSONArray("results").getJSONObject(0).getString("token"));
                                mIntent = new Intent(CPSecondRegisteActivity.this, CPLoginActivity.class);
                                ActivityUtils.startActivity(CPSecondRegisteActivity.this, mIntent);
                            }
                            if (result.getInteger("error") == -1) {
                                Toast.makeText(CPSecondRegisteActivity.this, result.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, flag ? NetValue.POST_PASSWORD() : NetValue.GET_SIGN_UP(), params_map);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
