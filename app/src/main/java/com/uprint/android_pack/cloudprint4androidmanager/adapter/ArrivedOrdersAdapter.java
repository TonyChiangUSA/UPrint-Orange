package com.uprint.android_pack.cloudprint4androidmanager.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.widgets.CPRatingBar;

import java.lang.ref.WeakReference;

/**
 * Created by tonychiang on 15/11/15.
 * for show the arrived orders
 */
public class ArrivedOrdersAdapter extends RecyclerView.Adapter<ArrivedOrdersAdapter.OrderViewHolder> {
    private LayoutInflater inflater;
    private JSONArray array;
    private Context context;

    public ArrivedOrdersAdapter(Context context, JSONArray data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.array = data;
    }

    @Override
    public ArrivedOrdersAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArrivedOrdersAdapter.OrderViewHolder(inflater.inflate(R.layout.finished_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ArrivedOrdersAdapter.OrderViewHolder holder, int position) {
        if (this.array != null) {
            JSONObject result = array.getJSONObject(position);
            int rate = result.getInteger("rate");
            holder.student_room.setText(result.getString("student_room"));
            holder.student_phone.setText(result.getString("student_phone"));
            holder.nickname.setText(result.getString("nickname"));
            holder.demand_time.setText(result.getString("demand_time"));
            holder.fee.setText("订单价格:  " + result.getFloat("print_fee") + " 元");
            if (rate == 0) {
                holder.ratingBar.setStar(5);
            } else
                holder.ratingBar.setStar(rate);
            holder.orders_type.setText("订单类型: " + (result.getInteger("order_type") == 0 ? "打印" : "复印"));
        }
    }

    @Override
    public int getItemCount() {
        return this.array.size();
    }

    public void addAll(JSONArray array1) {
        synchronized (this) {
            for (int i = array1.size() - 1; i > -1; i--) {
                this.array.add(0, array1.getJSONObject(i));
            }
            notifyItemRangeInserted(0, array1.size());
        }
        if (array1.size() != 0) {
            Toast.makeText(context, "最新订单有:" + array1.size() + "单", Toast.LENGTH_SHORT).show();
        }
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView student_room;
        TextView student_phone;
        TextView nickname;
        TextView demand_time;
        TextView fee;
        TextView orders_type;
        CPRatingBar ratingBar;
        WeakReference<Context> ref = new WeakReference<>(context);

        public OrderViewHolder(View itemView) {
            super(itemView);
            student_room = (TextView) itemView.findViewById(R.id.buildingName);
            student_phone = (TextView) itemView.findViewById(R.id.user_phone);
            nickname = (TextView) itemView.findViewById(R.id.user2_name);
            demand_time = (TextView) itemView.findViewById(R.id.command_time);
            fee = (TextView) itemView.findViewById(R.id.totalFee);
            orders_type = (TextView) itemView.findViewById(R.id.orders_type);
            ratingBar = (CPRatingBar) itemView.findViewById(R.id.ratingbar);

            student_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String phoneNum = ((TextView) v).getText().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ref.get());
                    builder.setMessage("确认拨打给" + phoneNum + "吗?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent phoneIntent = new Intent("android.intent.action.CALL",
                                    Uri.parse("tel:" + phoneNum));
                            ref.get().startActivity(phoneIntent);
                        }
                    }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }
            });
        }
    }
}
