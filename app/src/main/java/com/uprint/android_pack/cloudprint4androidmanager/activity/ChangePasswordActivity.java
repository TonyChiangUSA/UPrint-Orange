package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.uprint.android_pack.cloudprint4androidmanager.CPBaseActivity;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonPostBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.MD5;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangxiaang on 15/10/28.
 */
public class ChangePasswordActivity extends CPBaseActivity {
    private TextView title;
    private HashMap<String, String> parmas_map;
    @Bind(R.id.oldpas)
    MaterialEditText oldpas;
    @Bind(R.id.newpass)
    MaterialEditText newpass;
    @Bind(R.id.newpass2)
    MaterialEditText newpass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.goback);

        title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        title.setText("修改个人信息");
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        SharedPreferenceUtil.checkToken(this);
        parmas_map = new HashMap<>();
        parmas_map.put("token", SharedPreferenceUtil.get_Token(this));

    }

    public boolean checkoutParams() {
        return newpass.getEditableText().toString().equals(newpass2.getEditableText().toString());
    }

    public void pushPas(View view) {
        if (!checkoutParams()) {
            Toast.makeText(this, "两次密码不一样", Toast.LENGTH_SHORT).show();
            return;
        }
        parmas_map.put("oldPassword", MD5.getMD5(oldpas.getText().toString()));
        parmas_map.put("newPassword", MD5.getMD5(newpass.getText().toString()));

        CommonPostBiz biz = new CommonPostBiz(this);
        biz.request(new ICallBack() {
            @Override
            public void displayResult(int status, Object... params) {

                JSONObject result = (JSONObject) params[0];
                if (result.getInteger("error") != 0) {
                    Toast.makeText(ChangePasswordActivity.this, result.getString("msg"), Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                builder.setMessage("修改成功").setCancelable(false).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).create().show();
            }
        }, NetValue.PASSWORD_UPDATE(), parmas_map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
