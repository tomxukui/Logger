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

import static com.ablingbling.library.logger.LogUtil.SPILT_CHILD;
import static com.ablingbling.library.logger.LogUtil.SPILT_GROUP;

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
        List<Log> logs = new ArrayList<>();

        String content = readFile(mLogFile.getFilePath(), "utf-8");

        if (!TextUtils.isEmpty(content)) {
            if (content.startsWith(SPILT_GROUP)) {
                content = content.substring(SPILT_GROUP.length(), content.length());
            }

            String[] groups = content.split(SPILT_GROUP);

            if (groups != null && groups.length > 0) {
                for (String group : groups) {
                    if (!TextUtils.isEmpty(group)) {
                        if (group.endsWith(SPILT_CHILD)) {
                            group = group.substring(0, group.length() - SPILT_CHILD.length());
                        }

                        String[] childs = group.split(SPILT_CHILD);

                        if (childs != null) {
                            Log log = new Log();

                            for (int i = 0; i < childs.length; i++) {
                                String str = childs[i];

                                switch (i) {

                                    case 0: {//请求方式
                                        log.setMethod(str);
                                    }
                                    break;

                                    case 1: {//标签
                                        log.setTag(str);
                                    }
                                    break;

                                    case 2: {//包名
                                        log.setClassName(str);
                                    }
                                    break;

                                    case 3: {//日志时间
                                        log.setDate(str);
                                    }
                                    break;

                                    case 4: {//日志内容
                                        log.setMsg(str);
                                    }
                                    break;

                                    case 5: {//线程
                                        log.setThread(str);
                                    }
                                    break;

                                    case 6: {//异常内容
                                        log.setThrowable(str);
                                    }
                                    break;

                                    default:
                                        break;

                                }
                            }

                            logs.add(log);
                        }
                    }
                }
            }

            Collections.reverse(logs);
        }

        return logs;
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