package com.ablingbling.library.logger;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class LogUtil {

    public static final String Spilt_group = "_@#x@k#@_";
    public static final String Spilt_child = "_@#x_k#@_";

    public static boolean mEnableLog = true;
    public static String mFolderPath;

    /**
     * 在Application中初始化
     */
    public static void init(boolean enable, @NonNull String folderPath) {
        if (TextUtils.isEmpty(folderPath)) {
            throw new NullPointerException();
        }

        mEnableLog = enable;
        mFolderPath = folderPath;

        CrashUtil.getInstance().init();
    }

    /**
     * 初始化产生一条日志文件，用来记录日志内容，如果没有文件，则不记录
     */
    public static void create() {
        if (mEnableLog) {
            if (!TextUtils.isEmpty(mFolderPath)) {
                LogConfigurator configurator = new LogConfigurator();
                configurator.setFileName(mFolderPath + File.separator + System.currentTimeMillis() + ".log");

                StringBuffer sb = new StringBuffer();
                sb.append(Spilt_group);
                sb.append("%p");
                sb.append(Spilt_child);
                sb.append("%c");
                sb.append(Spilt_child);
                sb.append("%C");
                sb.append(Spilt_child);
                sb.append("%d");
                sb.append(Spilt_child);
                sb.append("%m");
                sb.append(Spilt_child);
                sb.append("%t");
                configurator.setFilePattern(sb.toString());

                configurator.setLogCatPattern("%m%n");
                configurator.setRootLevel(Level.ALL);
                configurator.setLevel("org.apache", Level.ERROR);
                configurator.configure();
            }
        }
    }

    public static void d(String tag, String msg) {
        if (mEnableLog) {
            Logger.getLogger(tag).debug(msg);
        }
    }

    public static void d(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            Logger.getLogger(tag).debug(msg, t);
        }
    }

    public static void i(String tag, String msg) {
        if (mEnableLog) {
            Logger.getLogger(tag).info(msg);
        }
    }

    public static void i(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            Logger.getLogger(tag).info(msg, t);
        }
    }

    public static void w(String tag, String msg) {
        if (mEnableLog) {
            Logger.getLogger(tag).warn(msg);
        }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            Logger.getLogger(tag).warn(msg, t);
        }
    }

    public static void e(String tag, String msg) {
        if (mEnableLog) {
            Logger.getLogger(tag).error(msg);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            Logger.getLogger(tag).error(msg, t);
        }
    }

}