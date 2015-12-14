package com.uprint.android_pack.cloudprint4androidmanager.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uprint.android_pack.cloudprint4androidmanager.R;
import com.uprint.android_pack.cloudprint4androidmanager.network.ICallBack;
import com.uprint.android_pack.cloudprint4androidmanager.network.NetValue;
import com.uprint.android_pack.cloudprint4androidmanager.network.biz.CommonPostBiz;
import com.uprint.android_pack.cloudprint4androidmanager.utils.CPTimeUtils;
import com.uprint.android_pack.cloudprint4androidmanager.utils.SharedPreferenceUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by tonychiang on 15/10/13.
 */
public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.OrderViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private JSONArray array;

    public OrdersListAdapter(Context context, JSONArray content) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.array = content;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(inflater.inflate(R.layout.simple_orders_2, parent, false));
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        if (this.array != null) {
            JSONObject result = array.getJSONObject(position);
            holder.print_type.setText(result.getInteger("order_type") == 0 ? "打印" : "复印");
            holder.user_name.setText(result.getString("nickname"));
            holder.command_time.setText(result.getString("demand_time"));
            holder.shop_location.setText(result.getJSONObject("shop").getString("shop_location"));
            //给的是second
            holder.pad_time.setText(CPTimeUtils.formateDate("MM-dd HH:mm", result.getLongValue("paid_at") * 1000));
            holder.user_phone.setText(result.getString("student_phone"));
            holder.user_building.setText(result.getString("student_room"));
        }
    }

    @Override
    public int getItemCount() {
        return this.array.size();
    }

    public JSONObject getJsonObject(int position) {
        if (position >= getItemCount()) {
            return null;
        }
        return this.array.getJSONObject(position);

    }

    //注意这个顺序
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

    public void removeItem(int position) {
        if (position < 0 || position > this.array.size()) {
            return;
        }
        this.array.remove(position);
        notifyItemRemoved(position);
    }


    public void clear() {
        if (this.array != null) {
            this.array = null;
        }
        notifyDataSetChanged();
    }

    public JSONObject getData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("results", this.array);
        return jsonObject;

    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView print_type;
        TextView user_name;
        TextView user_phone;
        TextView user_building;
        TextView pad_time;
        TextView command_time;
        TextView shop_location;
        TextView finish_bt;

        public OrderViewHolder(View itemView) {
            super(itemView);
            final WeakReference<Context> reference = new WeakReference<>(context);
            print_type = (TextView) itemView.findViewById(R.id.orders_type);
            user_name = (TextView) itemView.findViewById(R.id.user2_name);
            user_phone = (TextView) itemView.findViewById(R.id.user_phone);
            user_building = (TextView) itemView.findViewById(R.id.buildingName);
            pad_time = (TextView) itemView.findViewById(R.id.user_upload_time);
            command_time = (TextView) itemView.findViewById(R.id.command_time);
            shop_location = (TextView) itemView.findViewById(R.id.print_shop_name);
            finish_bt = (TextView) itemView.findViewById(R.id.finish_bt);
            //处理发送完成信息
            finish_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int order_id = getJsonObject(getAdapterPosition()).getInteger("ID");
                    SharedPreferenceUtil.checkToken(reference.get());
                    String token = SharedPreferenceUtil.get_Token(reference.get());
                    HashMap map = new HashMap();
                    map.put("token", token);
                    map.put("order_id", order_id);
                    CommonPostBiz biz = new CommonPostBiz(reference.get());
                    biz.request(new ICallBack() {
                        @Override
                        public void displayResult(int status, Object... params) {
                            JSONObject result = (JSONObject) params[0];
                            if (result.getInteger("error") == 0) {
                                Vibrator vibrator = (Vibrator) reference.get().getSystemService(reference.get().VIBRATOR_SERVICE);
                                vibrator.vibrate(80);
                                removeItem(getAdapterPosition());
                            }
                        }
                    }, NetValue.CONFIRM_ORDER(), map);
                }
            });
        }
    }
}
