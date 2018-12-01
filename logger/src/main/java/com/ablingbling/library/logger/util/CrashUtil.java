package com.ablingbling.library.logger.util;

import com.ablingbling.library.logger.LogUtil;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 拦截未捕获的异常
 */
public class CrashUtil implements UncaughtExceptionHandler {

    private static final String TAG = "CrashUtil";

    private static CrashUtil instance;

    private UncaughtExceptionHandler uncaughtExceptionHandler;

    private CrashUtil() {
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static CrashUtil getInstance() {
        if (instance == null) {
            instance = new CrashUtil();
        }
        return instance;
    }

    public void init() {
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        LogUtil.e(TAG, "未捕获的异常", throwable);
        uncaughtExceptionHandler.uncaughtException(thread, throwable);
    }

}