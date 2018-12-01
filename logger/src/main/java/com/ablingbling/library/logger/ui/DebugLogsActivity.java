package com.ablingbling.library.logger.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.ablingbling.library.logger.R;
import com.ablingbling.library.logger.adapter.DebugLogsAdapter;
import com.ablingbling.library.logger.bean.Log;
import com.ablingbling.library.logger.bean.LogFile;
import com.ablingbling.library.logger.util.GsonUtil;

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

        mAdapter.setNewData(getLogs());
    }

    private List<Log> getLogs() {
        String content = readFile(mLogFile.getFilePath(), "utf-8");

        if (!TextUtils.isEmpty(content)) {
            if (content.endsWith(",")) {
                content = content.substring(0, content.length() - 1);
            }

            List<Log> logs = GsonUtil.listFromJson("[" + content + "]", Log.class);
            Collections.reverse(logs);
            return logs;

        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 读取文件
     */
    private String readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        if (file == null || !file.isFile()) {
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

}