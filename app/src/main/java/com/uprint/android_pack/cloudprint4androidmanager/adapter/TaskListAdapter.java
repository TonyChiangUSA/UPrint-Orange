package com.uprint.android_pack.cloudprint4androidmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.uprint.android_pack.cloudprint4androidmanager.R;

/**
 * Created by tonychiang on 15/10/20.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskItemHolder> {
    private Context context;
    private LayoutInflater inflater;
    private JSONArray data;

    public TaskListAdapter(Context context, JSONArray array) {
        this.inflater = LayoutInflater.from(context);
        this.data = array;
    }

    @Override
    public TaskListAdapter.TaskItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskItemHolder(inflater.inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.TaskItemHolder holder, int position) {
        if (this.data != null || this.data.size() != 0) {
            int bothside = this.data.getJSONObject(position).getInteger("bothside");
            int colorful = this.data.getJSONObject(position).getInteger("colorful");
            int copies = this.data.getJSONObject(position).getInteger("copies");
            int pages = this.data.getJSONObject(position).getInteger("pages");
            holder.fileName.setText("文件名: " + this.data.getJSONObject(position).getString("file_name"));
            holder.fileProp.setText("页数:" + pages + "\n" + (bothside == 0 ? "单面|" : "双面|")
                    + (colorful == 0 ? "黑白" : "彩色") + "\n" + "打印份数:" + copies + "份");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class TaskItemHolder extends RecyclerView.ViewHolder {
        TextView fileName;
        TextView fileProp;

        public TaskItemHolder(View itemView) {
            super(itemView);
            fileProp = (TextView) itemView.findViewById(R.id.properties);
            fileName = (TextView) itemView.findViewById(R.id.filename);
        }
    }
}
