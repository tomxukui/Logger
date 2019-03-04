package com.ablingbling.library.logger.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ablingbling.library.logger.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xukui on 2017/12/4.
 */

public class DebugLogManagerAdapter extends RecyclerView.Adapter<DebugLogManagerAdapter.ViewHolder> {

    private List<File> mFiles;

    private OnItemClickListener mOnItemClickListener;

    public DebugLogManagerAdapter() {
        mFiles = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_debug_log_manager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final File file = mFiles.get(position);

        holder.tv_name.setText(file.getName());
        holder.tv_path.setText(file.getAbsolutePath());

        holder.linear_container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, file);
                }
            }

        });
    }

    public void setNewData(List<File> files) {
        mFiles.clear();
        if (files != null) {
            mFiles.addAll(files);
        }

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linear_container;
        TextView tv_name;
        TextView tv_path;

        public ViewHolder(View itemView) {
            super(itemView);
            linear_container = itemView.findViewById(R.id.linear_container);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_path = itemView.findViewById(R.id.tv_path);
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position, File file);

    }

}