package com.ablingbling.library.logger;

import android.content.Context;
import android.text.TextUtils;

import com.ablingbling.library.logger.util.CrashUtil;
import com.ablingbling.library.logger.util.FileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.pqpo.librarylog4a.Level;
import me.pqpo.librarylog4a.Log4a;
import me.pqpo.librarylog4a.LogData;
import me.pqpo.librarylog4a.appender.AndroidAppender;
import me.pqpo.librarylog4a.appender.FileAppender;
import me.pqpo.librarylog4a.formatter.DateFileFormatter;
import me.pqpo.librarylog4a.interceptor.Interceptor;
import me.pqpo.librarylog4a.logger.AppenderLogger;

public class LogUtil {

    private static final int BUFFER_SIZE = 1024 * 400;

    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final String SPILT_GROUP = "╠GROUP╣";
    public static final String SPILT_CHILD = "╠CHILD╣";

    public static boolean mEnableLog = true;
    private static String mFolderPath;

    /**
     * 在Application中初始化
     *
     * @param context    上下文
     * @param enable     是否打印日志
     * @param catchCrash 是否打印未捕获的异常(当开启打印日志时才有效), 建议不打印, 因为开启的话会忽略到一些异常而不会崩溃，让开发者无法察觉错误
     * @param folderPath 存放日志的文件夹地址
     */
    public static void init(Context context, boolean enable, boolean catchCrash, String folderPath) {
        mEnableLog = enable;
        mFolderPath = folderPath;

        if (mEnableLog) {
            initLogger(context, folderPath);

            if (catchCrash) {
                CrashUtil.getInstance().init();
            }
        }
    }

    public static void init(Context context, boolean enable, String folderPath) {
        init(context, enable, false, folderPath);
    }

    private static void initLogger(Context context, String folderPath) {
        if (!TextUtils.isEmpty(folderPath)) {
            AppenderLogger logger = new AppenderLogger.Builder()
                    .addAppender(createAndroidAppender(Level.VERBOSE))
                    .addAppender(createFileAppender(context, Level.VERBOSE, folderPath))
                    .create();

            Log4a.setLogger(logger);
        }
    }

    public static void clear(Context context) {
        FileUtil.emptyFolder(mFolderPath);
        Log4a.release();
        initLogger(context, mFolderPath);
    }

    private static AndroidAppender createAndroidAppender(int level) {
        return new AndroidAppender.Builder()
                .setLevel(level)
                .addInterceptor(new Interceptor() {

                    @Override
                    public boolean intercept(LogData logData) {
                        logData.msg = "日志如下:\n╔═════════════════════════════════════════════════════════╗\n" + logData.msg + "\n╚═════════════════════════════════════════════════════════╝";
                        return true;
                    }

                })
                .create();
    }

    private static FileAppender createFileAppender(Context context, int level, String folderPath) {
        String time = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(new Date());

        return new FileAppender.Builder(context)
                .setLevel(level)
                .setBufferSize(BUFFER_SIZE)
                .setFormatter(new DateFileFormatter(FORMATTER))
                .setBufferFilePath(new File(folderPath, ".logCache").getAbsolutePath())
                .setLogFilePath(new File(folderPath, time + ".txt").getAbsolutePath())
                .setCompress(false)
                .addInterceptor(new Interceptor() {

                    @Override
                    public boolean intercept(LogData logData) {
                        logData.tag = SPILT_CHILD + logData.tag;
                        logData.msg = SPILT_CHILD + logData.msg + SPILT_GROUP;
                        return true;
                    }

                })
                .create();
    }

    /**
     * debug
     */
    public static void d(String tag, String msg) {
        if (mEnableLog) {
            Log4a.d(tag, msg);
            Log4a.flush();
        }
    }

    /**
     * info
     */
    public static void i(String tag, String msg) {
        if (mEnableLog) {
            Log4a.i(tag, msg);
            Log4a.flush();
        }
    }

    /**
     * warn
     */
    public static void w(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            Log4a.w(tag, msg, t);
            Log4a.flush();
        }
    }

    public static void w(String tag, String msg) {
        if (mEnableLog) {
            Log4a.w(tag, msg);
            Log4a.flush();
        }
    }

    public static void w(String tag, Throwable t) {
        if (mEnableLog) {
            Log4a.w(tag, t);
            Log4a.flush();
        }
    }

    /**
     * error
     */
    public static void e(String tag, String msg, Throwable t) {
        if (mEnableLog) {
            Log4a.e(tag, msg, t);
            Log4a.flush();
        }
    }

    public static void e(String tag, String msg) {
        if (mEnableLog) {
            Log4a.e(tag, msg);
            Log4a.flush();
        }
    }

    public static void e(String tag, Throwable t) {
        if (mEnableLog) {
            Log4a.e(tag, t);
            Log4a.flush();
        }
    }

}