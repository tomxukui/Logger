package com.ablingbling.library.logger.adapter;

import android.widget.TextView;

import com.ablingbling.library.logger.R;
import com.ablingbling.library.logger.bean.Log;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by xukui on 2017/12/4.
 */

public class DebugLogsAdapter extends BaseQuickAdapter<Log, BaseViewHolder> {

    public DebugLogsAdapter() {
        super(R.layout.item_list_debug_logs);
    }

    @Override
    protected void convert(BaseViewHolder helper, Log item) {
        TextView tv_tag = helper.getView(R.id.tv_tag);
        TextView tv_date = helper.getView(R.id.tv_date);
        TextView tv_msg = helper.getView(R.id.tv_msg);

        tv_tag.setText(String.format("%s [%s]", item.getTag(), item.getLevelName()));
        tv_date.setText(item.getDate());
        tv_msg.setText(formatJson(item.getMsg()));
        tv_msg.setTextColor(item.getLevelColor());
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

}