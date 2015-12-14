package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.uprint.android_pack.cloudprint4androidmanager.dataType.ManagerInfo;
import com.uprint.android_pack.cloudprint4androidmanager.dataType.SchoolInfo;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonGetBiz;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonPostBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.EmptyViewUtil;
import com.uprint.android_pack.cloudprint4androidmanager.utils.RegexUtils;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tonychiang on 15/10/13.
 * todo the roles group has some problem can't react for the role changed
 */
public class MineInfoActivity extends CPBaseActivity {
    public static final String TAG = "MineInfoActivity";

    private TextView title;
    private Map<String, String> params_map;
    private HashMap update_params_map;
    @Bind(R.id.managerName)
    TextView managerName;
    @Bind(R.id.QQ)
    TextView qq;
    @Bind(R.id.managerId)
    TextView managerId;
    @Bind(R.id.buildingName)
    TextView buildingName;
    @Bind(R.id.dorm)
    TextView dorm;
    @Bind(R.id.idcard)
    TextView idcard;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.email)
    TextView email;
    @Bind(R.id.graduated)
    TextView graduated;
    @Bind(R.id.alipay)
    TextView alipay;

    public ManagerInfo managerInfo;
    private boolean school_select;
    private ArrayList<SchoolInfo> schoolInfos;
    private ArrayList<SchoolInfo.DormInfo> infos;
    private SimpleBuildingAdapter adapter1;
    private int single_choice;
    private boolean getUserInfo;
    private int is_girl;

    MaterialEditText _name;
    MaterialEditText _qq;
    MaterialAutoCompleteTextView _school;
    MaterialEditText _email;
    MaterialEditText _alipay;
    MaterialEditText _graduatedYear;
    MaterialEditText _dromname;
    MaterialEditText _buildings;
    MaterialEditText _sendBuilding;
    MaterialEditText _idnum;
    RadioGroup _group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mineinfolayout_2);
        EmptyViewUtil.showLoadingDialog(this, "Loading..", true);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        params_map = new HashMap<>();
        update_params_map = new HashMap();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.goback);

        title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        title.setText("个人信息");
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        SharedPreferenceUtil.checkToken(this);
        params_map.put("token", SharedPreferenceUtil.get_Token(getApplicationContext()));

        initPage();
    }

    public void initPage() {
        SharedPreferenceUtil.checkToken(this);
        update_params_map.put("token", SharedPreferenceUtil.get_Token(getApplicationContext()));
        //update the info
        CommonGetBiz biz = new CommonGetBiz(this);
        biz.request(new ICallBack() {
            @Override
            public void displayResult(int status, Object... params) {
                EmptyViewUtil.closeLoadingDialog();
                JSONObject result = (JSONObject) params[0];
                JSONObject info = result.getJSONArray("results").getJSONObject(0);
                if (result.getInteger("error") != 0 || info.getInteger("state") != 2) {
                    new AlertDialog.Builder(MineInfoActivity.this)
                            .setMessage("尚未填写个人,去完善个人信息")
                            .setCancelable(false)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(), ImproveInfoActivity.class));
                                }
                            }).create().show();
                    return;
                }
                getUserInfo = true;

                managerInfo = JSON.toJavaObject(info, ManagerInfo.class);
                managerInfo.setID_number(info.getString("ID_number"));
                managerInfo.getBuilding().setID(info.getJSONObject("building").getInteger("ID"));
                managerInfo.getSchool().setID(info.getJSONObject("school").getInteger("ID"));
                for (int i = 0; i < info.getJSONArray("manage_buildings").size(); i++) {
                    managerInfo.getManage_buildings().get(i).setID(info.getJSONArray("manage_buildings")
                            .getJSONObject(i).getInteger("ID"));
                }
                managerInfoView(managerInfo);
            }
        }, NetValue.IMPROVE_INFO(), params_map);
    }

    public void managerInfoView(ManagerInfo managerInfo) {
        managerName.setText(managerInfo.getManager_name());
        qq.setText(managerInfo.getQq());
        sex.setText(managerInfo.getSex() == 0 ? "男" : "女");
        email.setText(managerInfo.getEmail());
        alipay.setText(managerInfo.getAlipay_account());
        graduated.setText(managerInfo.getGraduate_year());
        idcard.setText(managerInfo.getID_number());
        dorm.setText(managerInfo.getDorm());
        buildingName.setText(managerInfo.getBuilding().getName());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < managerInfo.getManage_buildings().size(); i++) {
            builder.append(managerInfo.getManage_buildings().get(i).getName() + "\n");
        }
        int size = builder.length();
        if (size > 0) {
            builder.deleteCharAt(size - 1);
            managerId.setText(builder.toString());
        } else managerId.setText("尚未选择");

    }

    public void initDialogManagerInfo(ManagerInfo managerInfo) {
        _name.setText(managerInfo.getManager_name());
        _qq.setText(managerInfo.getQq());
        _school.setHint("请重新输入您的学校信息");
        _email.setText(managerInfo.getEmail());
        _graduatedYear.setText(managerInfo.getGraduate_year());
        _dromname.setText(managerInfo.getDorm());
        _alipay.setText(managerInfo.getAlipay_account());
        _idnum.setText(managerInfo.getID_number());

        if (managerInfo.getSex() == 0) {
            _group.check(R.id.boy);
        } else _group.check(R.id.girl);
    }

    public void initContentView(View contentView) {
        _name = (MaterialEditText) contentView.findViewById(R.id.nickname);
        _qq = (MaterialEditText) contentView.findViewById(R.id.qqaccount);
        _school = (MaterialAutoCompleteTextView) contentView.findViewById(R.id.school);
        _email = (MaterialEditText) contentView.findViewById(R.id.email);
        _alipay = (MaterialEditText) contentView.findViewById(R.id.zhifubao);
        _graduatedYear = (MaterialEditText) contentView.findViewById(R.id.graduatedYear);
        _dromname = (MaterialEditText) contentView.findViewById(R.id.dormname);
        _buildings = (MaterialEditText) contentView.findViewById(R.id.select_shop);
        _sendBuilding = (MaterialEditText) contentView.findViewById(R.id.sendBuilding);
        _idnum = (MaterialEditText) contentView.findViewById(R.id.idnum);
        _group = (RadioGroup) contentView.findViewById(R.id.radioGroup);
        //init the value
        initDialogManagerInfo(managerInfo);


        _group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.boy:
                        is_girl = 0;
                        break;
                    case R.id.girl:
                        is_girl = 1;
                        break;
                    default:
                        break;
                }
            }
        });

        _school.addTextChangedListener(new TextWatcher() {
            WeakReference<MineInfoActivity> ref = new WeakReference<>(MineInfoActivity.this);

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _school.dismissDropDown();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _school.dismissDropDown();
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
                            List<String> school_name = new ArrayList<>();
                            schoolInfos = new ArrayList<>();
                            JSONObject result = (JSONObject) params[0];
                            if (result.getInteger("error") != 0) {
                                Toast.makeText(ref.get(), result.getString("msg"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONArray array = result.getJSONArray("results");
                            if (array.size() == 0) {
                                Toast.makeText(ref.get(), "抱歉，您的学校暂时未收录", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for (int i = 0; i < array.size(); i++) {
                                SchoolInfo school = JSON.parseObject(array.getJSONObject(i).toJSONString(), SchoolInfo.class);
                                schoolInfos.add(school);
                            }
                            for (int i = 0; i < schoolInfos.size(); i++) {
                                school_name.add(schoolInfos.get(i).getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ref.get(), R.layout.school_item_layout, school_name);

                            _school.setAdapter(adapter);
                            _school.showDropDown();
                        }
                    }, NetValue.GET_SCHOOL(), params);
                }
                school_select = false;
            }
        });

        _school.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                infos = schoolInfos.get(position).getBuildings();
                if (infos != null && infos.size() > 0) {
                    HashMap schoolInfo = new HashMap();
                    schoolInfo.put("ID", schoolInfos.get(position).getId());
                    schoolInfo.put("name", schoolInfos.get(position).getName());
                    update_params_map.put("school", schoolInfo);
                    school_select = true;

                    adapter1 = new SimpleBuildingAdapter(MineInfoActivity.this, infos);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MineInfoActivity.this);
                    builder.setMessage("您的学校尚无录入相应的宿舍,请联系我们的客服").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }

            }
        });
        _buildings.setOnClickListener(new View.OnClickListener() {
            HashMap building = new HashMap();

            @Override
            public void onClick(View v) {
                if (infos == null) {
                    Toast.makeText(MineInfoActivity.this, "请先选择学校", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MineInfoActivity.this);
                AlertDialog dialog = builder.setSingleChoiceItems(adapter1, -1, new DialogInterface.OnClickListener() {
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
                        update_params_map.put("building", building);
                        _buildings.setText(infos.get(single_choice).getName());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                dialog.show();
            }
        });
        _sendBuilding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (infos == null) {
                    Toast.makeText(MineInfoActivity.this, "请先选择学校", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ArrayList<SchoolInfo.DormInfo> list = new ArrayList<>(20);
                CharSequence[] names = new CharSequence[infos.size()];
                for (int i = 0; i < infos.size(); i++) {
                    names[i] = infos.get(i).getName();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MineInfoActivity.this);
                AlertDialog dialog = builder.setMultiChoiceItems(names, null, new DialogInterface.OnMultiChoiceClickListener() {
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
                        _sendBuilding.setText(getBuildingListName(list));
                        update_params_map.put("manage_buildings", send_building);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                dialog.show();
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

    public boolean validateForm() {
        if (_name.validateWith(RegexUtils.name()) &&
                _graduatedYear.validateWith(RegexUtils.year()) &&
                _qq.validateWith(RegexUtils.qq()) &&
                _idnum.validateWith(RegexUtils.idnum()) &&
                _email.validateWith(RegexUtils.email()) &&
                _alipay.validateWith(RegexUtils.zhifubao())) {
            return true;
        }
        return false;
    }

    public void changeInfo(View view) {
        if (getUserInfo) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View contentView = LayoutInflater.from(this).inflate(R.layout.changeinfo, null, false);
            initContentView(contentView);

            AlertDialog dialog = builder.setView(contentView).setCancelable(true).setPositiveButton("提交", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!validateForm()) {
                        //todo 如何不取消当前的view？
                    }
                    postUpdateParams();
                }
            }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            dialog.show();
        } else Toast.makeText(this, "尚未获取到信息", Toast.LENGTH_SHORT).show();
    }

    public void postUpdateParams() {
        updateParamsMap();
        CommonPostBiz biz = new CommonPostBiz(this);
        biz.request(new ICallBack() {
            @Override
            public void displayResult(int status, Object... params) {
                JSONObject result = (JSONObject) params[0];
                if (result.getInteger("error") == 0) {
                    Toast.makeText(MineInfoActivity.this, result.getString("msg"), Toast.LENGTH_SHORT).show();
                    updatePageAgain();
                }
            }
        }, NetValue.UPDATE_MANAGER_INFO(), update_params_map);
    }

    private void updatePageAgain() {
        EmptyViewUtil.showLoadingDialog(this, "更新信息...", false);
        initPage();
    }

    //TODO 检测参数
    private void updateParamsMap() {
        update_params_map.put("manager_name", _name.getText().toString());
        update_params_map.put("qq", _qq.getText().toString());
        update_params_map.put("sex", is_girl);
        update_params_map.put("email", _email.getText().toString());
        update_params_map.put("graduate_year", _graduatedYear.getText().toString());
        update_params_map.put("alipay_account", _alipay.getText().toString());
        update_params_map.put("ID_number", _idnum.getText().toString());
        update_params_map.put("dorm", _dromname.getText().toString());
        update_params_map.put("state", managerInfo.getState());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
