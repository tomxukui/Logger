package com.ablingbling.library.logger.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ablingbling.library.logger.LogUtil;
import com.ablingbling.library.logger.R;
import com.ablingbling.library.logger.adapter.DebugLogsAdapter;
import com.ablingbling.library.logger.bean.LogField;
import com.ablingbling.library.logger.bean.LogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by tom on 2016/7/21.
 */
public class DebugLogsActivity extends AppCompatActivity {

    private DebugLogsAdapter mAdapter;

    private LogFile mLogFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_logs);
        initData();
        initActionBar();
        initView();
    }

    private void initData() {
        mLogFile = (LogFile) getIntent().getSerializableExtra("LogFile");
    }

    private void initActionBar() {
        if (getSupportActionBar() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            getSupportActionBar().setTitle(format.format(mLogFile.getDate()));
        }
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new DebugLogsAdapter();
        recyclerView.setAdapter(mAdapter);

        mAdapter.setNewData(getLogFields());
    }

    private List<LogField> getLogFields() {
        List<LogField> logFields = new ArrayList<>();

        StringBuilder contentSb = readFile(mLogFile.getFilePath(), "utf-8");
        if (contentSb != null) {
            String content = contentSb.toString().trim();

            if (content != null && content.length() > LogUtil.Spilt_group.length()) {
                if (content.startsWith(LogUtil.Spilt_group)) {
                    content = content.substring(LogUtil.Spilt_group.length(), content.length());
                }

                String[] groups = content.split(LogUtil.Spilt_group);

                if (groups != null) {
                    for (int i = 0; i < groups.length; i++) {
                        LogField logField = new LogField();

                        String[] childs = groups[i].trim().split(LogUtil.Spilt_child);
                        for (int j = 0; j < childs.length; j++) {
                            String item = childs[j];

                            switch (j) {

                                case 0: {//请求方式
                                    logField.setMethod(item);
                                }
                                break;

                                case 1: {//标签
                                    logField.setTag(item);
                                }
                                break;

                                case 2: {//包名
                                    logField.setClassName(item);
                                }
                                break;

                                case 3: {//日志时间
                                    logField.setDate(item);
                                }
                                break;

                                case 4: {//日志内容
                                    logField.setMsg(item);
                                }
                                break;

                                case 5: {//异常内容
                                    logField.setThrowableMsg(item);
                                }
                                break;

                                default:
                                    break;

                            }

                        }

                        logFields.add(logField);
                    }

                    Collections.reverse(logFields);
                }
            }
        }

        return logFields;
    }

    /**
     * 读取文件
     */
    private StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
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

}