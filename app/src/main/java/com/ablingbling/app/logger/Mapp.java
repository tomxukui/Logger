package com.ablingbling.app.logger;

import android.app.Application;

import com.ablingbling.library.logger.LogUtil;

/**
 * Created by xukui on 2018/5/21.
 */
public class Mapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(this, true, FileHelper.getLogDir(this));
    }

}
