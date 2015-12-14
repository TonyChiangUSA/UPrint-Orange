package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.SaveCallback;
import com.dd.processbutton.FlatButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.uprint.android_pack.cloudprint4androidmanager.CPBaseActivity;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonPostBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.ActivityUtils;
import com.uprint.android_pack.cloudprint4androidmanager.utils.EmptyViewUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.MD5;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tonychiang on 15/10/7.
 */
public class CPLoginActivity extends CPBaseActivity {
    @Bind(R.id.accountText)
    MaterialEditText accountTextView;
    @Bind(R.id.passwordText)
    MaterialEditText passwordTextView;
    @Bind(R.id.logInBtn)
    FlatButton logInBtn;
    @Bind(R.id.forgetPassword)
    TextView forgetPaswd;
    private TextView title;
    private Map<String, String> params_map;
    Intent intent;

    String installationId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loggedlayout);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        params_map = new HashMap<>();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        title.setText("登陆");
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        accountTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {
                    passwordTextView.requestFocus();
                    return true;
                }
                return false;
            }
        });
        accountTextView.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        passwordTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {
                    logInBtn.requestFocus();
                    return true;
                }
                return false;
            }
        });
        intent = new Intent(CPLoginActivity.this, CPRegisteActivity.class);
        forgetPaswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("flag", true);
                startActivity(intent);
            }
        });
    }

    public void LoginRoute(View view) {
        int resId = view.getId();
        switch (resId) {
            case R.id.logInBtn:
                String account = accountTextView.getText().toString().replace(" ", "").replace("-", "");
                String paswd = passwordTextView.getText().toString();
                if (account == null || paswd == null || account.trim().length() == 0 || paswd.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "账号 密码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                params_map.put("username", account);
                params_map.put("password", MD5.getMD5(paswd));
                //todo 提醒后台判断user 机型
                AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
//                            String result = AVInstallation.getCurrentInstallation().get("deviceType").toString()
//                                    + AVInstallation.getCurrentInstallation().get("timeZone").toString();
                            AVInstallation.getCurrentInstallation().saveInBackground();
                            installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                        }
                    }
                });
                //后台根据这个来进行组装uri推送数据 (新的订单以及推送审核通过的state)
                //{"data":{"alert": "Hello From LeanCloud~~~~"},"where":{"installationId":"cb219ee8-c745-48ca-870f-ae5f1e9f3e56"}}
                params_map.put("device_token", installationId);

                CommonPostBiz biz = new CommonPostBiz(this);
                biz.request(new ICallBack() {
                    @Override
                    public void displayResult(int status, Object... params) {
                        WeakReference<CPLoginActivity> ref = new WeakReference<>(CPLoginActivity.this);
                        EmptyViewUtil.closeLoadingDialog();
                        JSONObject result = (JSONObject) params[0];
                        if (result.getInteger("error") != 0) {
                            Toast.makeText(ref.get(), result.getString("msg"), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject info = result.getJSONArray("results").getJSONObject(0);
                        SharedPreferenceUtil.storage_Token(ref.get(),
                                info.getString("token"));
                        int statusState = info.getInteger("state");
                        SharedPreferenceUtil.storageUserState(ref.get(), statusState);
                        checkoutUserState(statusState);
                    }
                }, NetValue.LOGIN(), params_map);
                EmptyViewUtil.showLoadingDialog(this, "", false);
                break;
            case R.id.regisBtn:
                intent.putExtra("flag", false);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void checkoutUserState(int state) {
        switch (state) {
            case -1:
                Toast.makeText(CPLoginActivity.this, "您审核未通过", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                mIntent = new Intent(CPLoginActivity.this, ImproveInfoActivity.class);
                ActivityUtils.startActivity(CPLoginActivity.this, mIntent);
                break;
            case 1:
                Toast.makeText(CPLoginActivity.this, "您正处于审核期，通过后我们会及时通知您", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                mIntent = new Intent(CPLoginActivity.this, MainActivity.class);
                ActivityUtils.startActivity(CPLoginActivity.this, mIntent);
                break;
            case 3:
                Toast.makeText(CPLoginActivity.this, "您已辞职", Toast.LENGTH_SHORT).show();
                break;
            default:
                //还有什么情况?
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EmptyViewUtil.closeLoadingDialog();
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
