package com.uprint.android_pack.cloudprint4androidmanager.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.dataType.SchoolInfo;

import java.util.ArrayList;

/**
 * Created by zhangxiaang on 15/11/3.
 */
public class SimpleBuildingAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SchoolInfo.DormInfo> data;
    private SparseArray<View> itemView;

    public SimpleBuildingAdapter(Context context, ArrayList<SchoolInfo.DormInfo> data) {
        this.context = context;
        this.data = data;
        itemView = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //注意 这里如果复用view 的话会干扰到checkBox。。。
        if (itemView.get(position) != null) {
            return itemView.get(position);
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.simple_building_item, null);
        ((CheckedTextView) convertView.findViewById(R.id.checkitem)).setText(data.get(position).getName());
        itemView.put(position, convertView);
        return convertView;
    }

    public View getViewByPosition(int position) {
        return itemView.get(position);
    }
}
