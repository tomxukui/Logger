package com.ablingbling.app.logger;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by xukui on 2017/12/24.
 */

public class FileHelper {

    /**
     * 判断是否挂载sdcard
     */
    public static boolean isMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取缓存目录
     */
    private static File getCacheDir(Context context) {
        File cacheDir = isMounted() ? context.getExternalCacheDir() : null;

        if (cacheDir == null) {
            cacheDir = context.getCacheDir();
        }

        if (cacheDir != null && !cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        return cacheDir;
    }

    /**
     * 获取日志缓存目录
     */
    public static String getLogDir(Context context) {
        File folder = new File(getCacheDir(context), "logs");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder.getAbsolutePath();
    }

    /**
     * 获取接口缓存目录
     */
    public static String getApiCacheDir(Context context) {
        File folder = new File(getCacheDir(context), "api");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder.getAbsolutePath();
    }

}