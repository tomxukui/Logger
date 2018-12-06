package com.ablingbling.library.logger.bean;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by tom on 2016/8/2.
 */
public class Log implements Serializable {

    private String level;
    private String tag;
    private String date;
    private String msg;

    public String getLevelName() {
        if ("I".equals(level)) {
            return "INFO";

        } else if ("D".equals(level)) {
            return "DEBUG";

        } else if ("W".equals(level)) {
            return "WARN";

        } else if ("E".equals(level)) {
            return "ERROR";

        } else {
            return "UNKOWN";
        }
    }

    public int getLevelColor() {
        if ("I".equals(level)) {
            return Color.parseColor("#4C4C4C");

        } else if ("D".equals(level)) {
            return Color.parseColor("#3D8CFF");

        } else if ("W".equals(level)) {
            return Color.parseColor("#FDAF3D");

        } else if ("E".equals(level)) {
            return Color.parseColor("#FF4B4B");

        } else {
            return Color.parseColor("#1A1A1A");
        }
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}