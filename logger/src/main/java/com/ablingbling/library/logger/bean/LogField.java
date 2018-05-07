package com.ablingbling.library.logger.bean;

import java.io.Serializable;

/**
 * Created by tom on 2016/8/2.
 */
public class LogField implements Serializable {

    private String method;
    private String tag;
    private String className;
    private String date;
    private String msg;
    private String throwableMsg;

    public LogField() {
    }

    public LogField(String method, String tag, String className, String date, String msg, String throwableMsg) {
        this.method = method;
        this.tag = tag;
        this.className = className;
        this.date = date;
        this.msg = msg;
        this.throwableMsg = throwableMsg;
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

    public String getThrowableMsg() {
        return throwableMsg;
    }

    public void setThrowableMsg(String throwableMsg) {
        this.throwableMsg = throwableMsg;
    }
}
