package com.ablingbling.library.logger.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ablingbling.library.logger.R;
import com.ablingbling.library.logger.bean.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xukui on 2017/12/4.
 */

public class DebugLogsAdapter extends RecyclerView.Adapter<DebugLogsAdapter.ViewHolder> {

    private List<Log> mLogs;

    public DebugLogsAdapter() {
        mLogs = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mLogs.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_debug_logs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Log log = mLogs.get(position);

        holder.tv_tag.setText(String.format("%s [%s]", log.getTag(), log.getLevelName()));
        holder.tv_date.setText(log.getDate());
        holder.tv_msg.setText(formatJson(log.getMsg()));
        holder.tv_msg.setTextColor(log.getLevelColor());
    }

    public void setNewData(List<Log> logs) {
        mLogs.clear();
        if (logs != null) {
            mLogs.addAll(logs);
        }

        notifyDataSetChanged();
    }

    /**
     * 格式化json
     */
    private String formatJson(String json) {

        if (json == null) {
            return null;
        }

        int level = 0;
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (level > 0 && '\n' == sb.charAt(sb.length() - 1)) {
                sb.append(getLevelStr(level));
            }

            switch (c) {

                case '{':
                case '[': {
                    sb.append(c + "\n");
                    level++;
                }
                break;

                case ',': {
                    sb.append(c + "\n");
                }
                break;

                case '}':
                case ']': {
                    sb.append("\n");
                    level--;
                    sb.append(getLevelStr(level));
                    sb.append(c);
                }
                break;

                default: {
                    sb.append(c);
                }
                break;

            }
        }

        return sb.toString();
    }

    private String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int i = 0; i < level; i++) {
            levelStr.append("\t\t");
        }
        return levelStr.toString();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_tag;
        TextView tv_date;
        TextView tv_msg;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_tag = itemView.findViewById(R.id.tv_tag);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_msg = itemView.findViewById(R.id.tv_msg);
        }

    }

}