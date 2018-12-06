package com.ablingbling.library.logger.adapter;

import android.widget.TextView;

import com.ablingbling.library.logger.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;

/**
 * Created by xukui on 2017/12/4.
 */

public class DebugLogManagerAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    public DebugLogManagerAdapter() {
        super(R.layout.item_list_debug_log_manager);
    }

    @Override
    protected void convert(BaseViewHolder helper, File item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_path = helper.getView(R.id.tv_path);

        tv_name.setText(item.getName());
        tv_path.setText(item.getAbsolutePath());
    }

}