package com.ablingbling.library.logger.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tom on 2016/7/22.
 */
public class LogFile implements Serializable {

    private String fileName;
    private String filePath;
    private Date date;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
