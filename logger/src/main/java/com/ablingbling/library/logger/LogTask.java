package com.ablingbling.library.logger;

import android.os.AsyncTask;

import com.ablingbling.library.logger.bean.Type;

import org.apache.log4j.Logger;

/**
 * Created by xukui on 2018-12-01.
 */
public class LogTask extends AsyncTask<Object, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Object... objects) {
        Type type = (Type) objects[0];
        String tag = (String) objects[1];
        String msg = (String) objects[2];
        Throwable throwable = (Throwable) objects[3];

        Logger logger = Logger.getLogger(tag);

        switch (type) {

            case D: {
                logger.debug(msg, throwable);
            }
            break;

            case I: {
                logger.info(msg, throwable);
            }
            break;

            case W: {
                logger.warn(msg, throwable);
            }
            break;

            case E: {
                logger.error(msg, throwable);
            }
            break;

            default:
                break;

        }

        return true;
    }

}
