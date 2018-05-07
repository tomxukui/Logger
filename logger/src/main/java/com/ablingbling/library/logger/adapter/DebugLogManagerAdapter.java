package com.ablingbling.library.logger.adapter;

import android.widget.TextView;

import com.ablingbling.library.logger.R;
import com.ablingbling.library.logger.bean.LogFile;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by xukui on 2017/12/4.
 */

public class DebugLogManagerAdapter extends BaseQuickAdapter<LogFile, BaseViewHolder> {

    private SimpleDateFormat mFormat;

    public DebugLogManagerAdapter() {
        super(R.layout.item_list_debug_log_manager);
        mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    @Override
    protected void convert(BaseViewHolder helper, LogFile item) {
        TextView nameText = helper.getView(R.id.nameText);
        TextView dateText = helper.getView(R.id.dateText);

        nameText.setText(item.getFileName());
        dateText.setText(mFormat.format(item.getDate()));
    }

}