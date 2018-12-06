package com.ablingbling.library.logger.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.ablingbling.library.logger.R;
import com.ablingbling.library.logger.adapter.DebugLogsAdapter;
import com.ablingbling.library.logger.bean.Log;
import com.ablingbling.library.logger.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ablingbling.library.logger.LogUtil.SPILT_CHILD;
import static com.ablingbling.library.logger.LogUtil.SPILT_GROUP;

/**
 * Created by tom on 2016/7/21.
 */
public class DebugLogsActivity extends AppCompatActivity {

    private DebugLogsAdapter mAdapter;

    private File mLogFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_logs);
        initData();
        initActionBar();
        initView();
        readLogs();
    }

    private void initData() {
        mLogFile = (File) getIntent().getSerializableExtra("LogFile");
    }

    private void initActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mLogFile.getName());
        }
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new DebugLogsAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_debug_logs, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_source) {
            Intent intent = new Intent(this, DebugLogSourceActivity.class);
            intent.putExtra("LogFile", mLogFile);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 读取日志
     */
    private void readLogs() {
        mAdapter.setNewData(getLogs());
    }

    private List<Log> getLogs() {
        List<Log> logs = new ArrayList<>();
        String content = FileUtil.readFile(mLogFile, "utf-8");

        if (!TextUtils.isEmpty(content)) {
            String[] groups = content.split(SPILT_GROUP);

            if (groups != null) {
                for (String group : groups) {
                    if (!TextUtils.isEmpty(group)) {
                        String[] childs = group.split(SPILT_CHILD);

                        if (childs != null && childs.length == 3) {
                            String s0 = childs[0];
                            String s1 = childs[1];
                            String s2 = childs[2];

                            Log log = new Log();

                            if (s0 != null) {
                                String sign = "\r\n";
                                if (s0.startsWith(sign)) {
                                    s0 = s0.substring(sign.length(), s0.length());
                                }

                                if (s0.length() > 3) {
                                    String date = s0.substring(0, s0.length() - 3);
                                    log.setDate(date);

                                    String level = s0.substring(s0.length() - 2, s0.length() - 1);
                                    log.setLevel(level);
                                }
                            }

                            if (s1 != null) {
                                String tag = s1.trim();
                                if (tag.endsWith(":")) {
                                    tag = tag.substring(0, s1.length() - 1);
                                }
                                log.setTag(tag);
                            }

                            log.setMsg(s2 == null ? "" : s2);

                            logs.add(0, log);
                        }
                    }
                }
            }
        }

        return logs;
    }

}