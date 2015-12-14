package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dd.processbutton.FlatButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.uprint.android_pack.cloudprint4androidmanager.CPBaseActivity;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonPostBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.ActivityUtils;
import com.uprint.android_pack.cloudprint4androidmanager.utils.CountDownButtonHelper;
import com.uprint.android_pack.cloudprint4androidmanager.utils.RegexUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tonychiang on 15/10/8.
 */
public class CPRegisteActivity extends CPBaseActivity {
    public static final String TAG = "CPRegisteActivity";

    MaterialEditText phoneAccount;
    MaterialEditText oauthCode;
    FlatButton oauthBtn;
    FlatButton secondBtn;
    public String phString;

    private TextView title;
    private Map<String, String> params_map;
    private CountDownButtonHelper helper;
    private String oauth_code;
    private boolean flag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_registe);
        flag = getIntent().getBooleanExtra("flag", false);
        init();

    }

    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.goback);

        title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        if (flag) {
            title.setText("忘记密码");
        } else title.setText("获取验证码");
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        phoneAccount = (MaterialEditText) findViewById(R.id.newAccounts);
        oauthCode = (MaterialEditText) findViewById(R.id.oauthCode);
        oauthBtn = (FlatButton) findViewById(R.id.oauthBtn);
        secondBtn = (FlatButton) findViewById(R.id.regisBtn);
        if (flag) {
            secondBtn.setText("修改密码");
        }
        helper = new CountDownButtonHelper(oauthBtn, "发送验证码", 60, 1);
        params_map = new HashMap<>();
    }

    public void route(View view) {
        int resId = view.getId();
        switch (resId) {
            case R.id.oauthBtn:
                phoneAccount.validateWith(RegexUtils.phone());
                if (!TextUtils.isEmpty(phString = phoneAccount.getText().toString())) {
                    params_map.put("username", phString);
                    CommonPostBiz biz = new CommonPostBiz(this);
                    biz.isOauth(true);
                    biz.request(new ICallBack() {
                        @Override
                        public void displayResult(int status, Object... params) {
                            if (params[0] instanceof JSONObject) {
                                JSONObject jsonObject = (JSONObject) params[0];
                                if (jsonObject.getInteger("error") == -1) {
                                    Toast.makeText(CPRegisteActivity.this, "该手机号已经注册过", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }, NetValue.GET_SMS_CODE(), params_map);
                    helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
                        @Override
                        public void finish() {
                            //do nothing
                        }
                    });
                    helper.start();

                } else {
                    Toast.makeText(this, "手机不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.regisBtn:
                phString = phoneAccount.getText().toString().replace(" ", "").replace("-", "");
                if (!TextUtils.isEmpty(oauth_code = oauthCode.getText().toString())) {
                    mIntent = new Intent(this, CPSecondRegisteActivity.class);
                    mIntent.putExtra("cellphone", phString);
                    mIntent.putExtra("oauthCode", oauth_code);
                    mIntent.putExtra("flag", flag);

                    ActivityUtils.startActivity(this, mIntent);
                } else {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        helper.stop();
        super.onDestroy();
    }
}