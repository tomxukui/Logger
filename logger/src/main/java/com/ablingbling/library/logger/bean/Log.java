package com.ablingbling.library.logger.bean;

import java.io.Serializable;

/**
 * Created by tom on 2016/8/2.
 */
public class Log implements Serializable {

    private String method;
    private String tag;
    private String className;
    private String date;
    private String msg;
    private String thread;
    private String position;
    private String throwable;

    public Log(String method, String tag, String className, String date, String msg, String thread, String position, String throwable) {
        this.method = method;
        this.tag = tag;
        this.className = className;
        this.date = date;
        this.msg = msg;
        this.thread = thread;
        this.position = position;
        this.throwable = throwable;
    }

    public Log() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getThrowable() {
        return throwable;
    }

    public void setThrowable(String throwable) {
        this.throwable = throwable;
    }

}