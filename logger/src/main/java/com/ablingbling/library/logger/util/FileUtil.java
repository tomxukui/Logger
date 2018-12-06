package com.ablingbling.library.logger.util;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by xukui on 2018-12-06.
 */
public class FileUtil {

    /**
     * 读取文件
     */
    public static String readFile(File file, String charsetName) {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }

        String result = "";
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!"".equals(result)) {
                    result += "\r\n";
                }
                result += line;
            }
            reader.close();
            return result;

        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);

        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * 清空文件夹
     */
    public static void emptyFolder(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        File folder = new File(path);
        if (folder == null || !folder.exists() || !folder.isDirectory()) {
            return;
        }

        for (File f : folder.listFiles()) {
            if (f != null && f.exists()) {
                if (f.isFile()) {
                    f.delete();
                }
            }
        }
    }

}
