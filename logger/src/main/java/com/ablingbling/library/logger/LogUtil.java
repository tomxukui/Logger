package com.ablingbling.library.logger;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ablingbling.library.logger.bean.Log;
import com.ablingbling.library.logger.bean.Type;
import com.ablingbling.library.logger.util.CrashUtil;
import com.ablingbling.library.logger.util.GsonUtil;

import org.apache.log4j.Level;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class LogUtil {

    public static final String SPILT_START = "_@#x@k#@_";
    public static final String SPILT_END = "_@#x_k#@_";

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

                Log log = new Log("%p", "%c", "%C", "%d", "%m", "%r", "%t", "%l", null);
                String filePattern = SPILT_START + GsonUtil.toJson(log) + SPILT_END;
                configurator.setFilePattern(filePattern);

                configurator.setLogCatPattern("%m%n");
                configurator.setRootLevel(Level.ALL);
                configurator.setLevel("org.apache", Level.ERROR);
                configurator.configure();
            }
        }
    }

    /**
     * debug
     *
     * @param tag
     * @param msg
     * @param t
     */
    public static void d(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            new LogTask().execute(Type.D, tag, msg, t);
        }
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    /**
     * info
     *
     * @param tag
     * @param msg
     * @param t
     */
    public static void i(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            new LogTask().execute(Type.I, tag, msg, t);
        }
    }

    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }

    /**
     * warn
     *
     * @param tag
     * @param msg
     * @param t
     */
    public static void w(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            new LogTask().execute(Type.W, tag, msg, t);
        }
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    /**
     * error
     *
     * @param tag
     * @param msg
     * @param t
     */
    public static void e(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            new LogTask().execute(Type.E, tag, msg, t);
        }
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

}