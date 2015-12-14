package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.uprint.android_pack.cloudprint4androidmanager.CPBaseActivity;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.adapter.SimpleBuildingAdapter;
import com.uprint.android_pack.cloudprint4androidmanager.dataType.SchoolInfo;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonGetBiz;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonPostBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.BitmapUtils;
import com.uprint.android_pack.cloudprint4androidmanager.utils.EmptyViewUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.RegexUtils;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tonychiang on 15/10/16.
 */
public class ImproveInfoActivity extends CPBaseActivity {
    public static final String TAG = "ImproveInfoActivity";
    private TextView title;
    @Bind(R.id.nickname)
    MaterialEditText name;
    @Bind(R.id.qqaccount)
    MaterialEditText qq;
    @Bind(R.id.school)
    MaterialAutoCompleteTextView school;
    @Bind(R.id.email)
    MaterialEditText email;
    @Bind(R.id.zhifubao)
    MaterialEditText zhifubao;
    @Bind(R.id.graduatedYear)
    MaterialEditText graduatedYear;
    @Bind(R.id.dormname)
    MaterialEditText dromname;
    @Bind(R.id.studentCard)
    ImageView studentCardImage;
    @Bind(R.id.select_shop)
    MaterialEditText buildings;
    @Bind(R.id.sendBuilding)
    MaterialEditText sendBuilding;
    @Bind(R.id.radioGroup)
    RadioGroup group;
    @Bind(R.id.idnum)
    MaterialEditText idnum;
    private Bitmap studentCardBtmp;

    private String encode_studentCardBtmp;

    private int is_girl;//0->boy 1->girl default ->boy
    private int is_printer = 1;//0->printer 1->sender default->sender

    private int single_choice;
    private Map params_map;
    private ArrayList<SchoolInfo> schoolInfos;
    private ArrayList<SchoolInfo.DormInfo> infos;

    private boolean school_select;
    private SimpleBuildingAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.improveinfo);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.goback);

        title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        title.setText("完善楼长信息");
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        params_map = new HashMap<>();

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.boy) {
                    is_girl = 0;
                } else if (checkedId == R.id.girl) {
                    is_girl = 1;
                }
            }
        });
        school.addTextChangedListener(new TextWatcher() {
            WeakReference<ImproveInfoActivity> ref = new WeakReference<>(ImproveInfoActivity.this);

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                school.dismissDropDown();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                school.dismissDropDown();
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyWord = s.toString();
                if (keyWord != null && keyWord.trim().length() != 0 && !school_select) {
                    Map<String, String> params = new HashMap<>();
                    params.put("keyword", keyWord);
                    CommonGetBiz biz = new CommonGetBiz(ref.get());
                    biz.request(new ICallBack() {
                        @Override
                        public void displayResult(int status, Object... params) {
                            Log.e(TAG, params[0].toString());
                            List<String> school_name = new ArrayList<>();
                            schoolInfos = new ArrayList<>();
                            JSONObject result = (JSONObject) params[0];
                            if (result.getInteger("error") != 0) {
                                Toast.makeText(ImproveInfoActivity.this, result.getString("msg"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONArray array = result.getJSONArray("results");
                            if (array.size() == 0) {
                                Toast.makeText(ImproveInfoActivity.this, "抱歉，您的学校暂时未收录", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for (int i = 0; i < array.size(); i++) {
                                SchoolInfo school = JSON.parseObject(array.getJSONObject(i).toJSONString(), SchoolInfo.class);
                                schoolInfos.add(school);
                            }
                            for (int i = 0; i < schoolInfos.size(); i++) {
                                school_name.add(schoolInfos.get(i).getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.school_item_layout, school_name);

                            school.setAdapter(adapter);
                            school.showDropDown();
                        }
                    }, NetValue.GET_SCHOOL(), params);
                }
                school_select = false;
            }
        });
        school.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                infos = schoolInfos.get(position).getBuildings();
                if (infos != null && infos.size() != 0) {
                    HashMap schoolInfo = new HashMap();
                    schoolInfo.put("ID", schoolInfos.get(position).getId());
                    schoolInfo.put("name", schoolInfos.get(position).getName());
                    params_map.put("school", schoolInfo);
                    school_select = true;
                    adapter1 = new SimpleBuildingAdapter(ImproveInfoActivity.this, infos);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ImproveInfoActivity.this);
                    builder.setMessage("您的学校尚无录入相应的宿舍,请联系我们的客服").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }

            }
        });

        buildings.setOnClickListener(new View.OnClickListener() {
            HashMap building = new HashMap();

            @Override
            public void onClick(View v) {
                if (infos == null) {
                    Toast.makeText(ImproveInfoActivity.this, "请先选择学校", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ImproveInfoActivity.this);
                builder.setSingleChoiceItems(adapter1, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        single_choice = which;
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        building.put("ID", infos.get(single_choice).getId());
                        building.put("name", infos.get(single_choice).getName());
                        building.put("status", infos.get(single_choice).getStatus());
                        params_map.put("building", building);
                        buildings.setHint(infos.get(single_choice).getName());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        sendBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infos == null) {
                    Toast.makeText(ImproveInfoActivity.this, "请先选择学校", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ArrayList<SchoolInfo.DormInfo> list = new ArrayList<>(20);
                CharSequence[] names = new CharSequence[infos.size()];
                for (int i = 0; i < infos.size(); i++) {
                    names[i] = infos.get(i).getName();
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(ImproveInfoActivity.this);
                builder.setMultiChoiceItems(names, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            if (list.size() >= 20) {
                                int index = infos.indexOf(list.get(0));
                                ((AlertDialog) dialog).getListView().setItemChecked(index, false);
                                list.remove(0);
                            }
                            list.add(infos.get(which));
                        } else {
                            list.remove(infos.get(which));
                        }
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<HashMap> send_building = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            HashMap map = new HashMap<>();
                            map.put("ID", list.get(i).getId());
                            map.put("status", list.get(i).getStatus());
                            map.put("name", list.get(i).getName());
                            send_building.add(map);
                        }

                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                            builder.append(list.get(i).getName() + ",");
                        }
                        sendBuilding.setHint(getBuildingListName(list));
                        params_map.put("manage_buildings", send_building);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
    }

    private String getBuildingListName(ArrayList<SchoolInfo.DormInfo> list) {
        StringBuilder builder = new StringBuilder();
        for (SchoolInfo.DormInfo info : list) {
            builder.append(info.getName() + ",");
        }
        int length = builder.length();
        builder.deleteCharAt(length - 1);
        return builder.toString();
    }

    public void route1(View view) {
        int resId = view.getId();
        switch (resId) {
            case R.id.improveBtn:
                if (checkoutAllInfo()) {
                    postAllInfo();
                }
                break;
            case R.id.studentcardgroup:
                Intent intent2 = new Intent();
                intent2.setType("image/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent2, 2);
                break;
            default:
                break;
        }
    }

    /**
     * why pick the image cost so much memory
     * how to get the image effect?
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 2) && (resultCode == RESULT_OK)) {
            Uri uri = data.getData();
            try {
                studentCardBtmp = BitmapUtils.createScaledBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri), 500, 500);
            } catch (IOException e) {
                e.printStackTrace();
            }
            studentCardImage.setImageBitmap(studentCardBtmp);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean checkoutAllInfo() {
        name.validateWith(RegexUtils.name());
        qq.validateWith(RegexUtils.qq());
        email.validateWith(RegexUtils.email());
        zhifubao.validateWith(RegexUtils.zhifubao());
        graduatedYear.validateWith(RegexUtils.year());
        idnum.validateWith(RegexUtils.idnum());

        if (name.getText().toString().trim().length() == 0 || !name.validate()) {
            Toast.makeText(this, "2-5个中文", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (school.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "学校信息未填", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (graduatedYear.getText().toString().trim().length() == 0 || !graduatedYear.validate()) {
            Toast.makeText(this, "毕业时间格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (qq.getText().toString().trim().length() == 0 || !qq.validate()) {
            Toast.makeText(this, "qq格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (idnum.getText().toString().trim().length() == 0 || !idnum.validate()) {
            Toast.makeText(this, "身份证号格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dromname.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "宿舍房号未填", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.getText().toString().trim().length() == 0 || !email.validate()) {
            Toast.makeText(this, "邮件格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (zhifubao.getText().toString().trim().length() == 0 || !zhifubao.validate()) {
            Toast.makeText(this, "支付宝账号格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (studentCardBtmp == null) {
            Toast.makeText(this, "学生证证件页未上传", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (params_map.get("building") == null || params_map.get("building").toString().trim().length() == 0) {
            Toast.makeText(this, "未选择宿舍", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void postAllInfo() {
        encode_studentCardBtmp = BitmapUtils.encode_bitmap(studentCardBtmp);
        SharedPreferenceUtil.checkToken(this);
        params_map.put("token", SharedPreferenceUtil.get_Token(this));
        params_map.put("certificate", encode_studentCardBtmp);
        params_map.put("manager_name", name.getText().toString());
        params_map.put("qq", qq.getText().toString());
        params_map.put("sex", is_girl);
        params_map.put("manager_type", is_printer);
        params_map.put("email", email.getText().toString());
        params_map.put("graduate_year", graduatedYear.getText().toString());
        params_map.put("alipay_account", zhifubao.getText().toString());
        params_map.put("dorm", dromname.getText().toString());
        params_map.put("ID_number", idnum.getText().toString());

        CommonPostBiz biz = new CommonPostBiz(this);
        biz.request(new ICallBack() {
            @Override
            public void displayResult(int status, Object... params) {

                JSONObject result = (JSONObject) params[0];
                EmptyViewUtil.closeLoadingDialog();
                if (result.getInteger("error") == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ImproveInfoActivity.this);
                    builder.setMessage(result.getString("msg")).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false).create().show();
                }
            }
        }, NetValue.IMPROVE_INFO(), params_map);
        EmptyViewUtil.showLoadingDialog(this, "UpLoading...", false);
    }

    @Override
    protected void onDestroy() {
        if (studentCardBtmp != null) {
            studentCardBtmp.recycle();
        }
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
