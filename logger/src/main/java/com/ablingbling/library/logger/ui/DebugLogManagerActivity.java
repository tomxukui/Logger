package com.ablingbling.library.logger.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.ablingbling.library.logger.LogUtil;
import com.ablingbling.library.logger.R;
import com.ablingbling.library.logger.adapter.DebugLogManagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.pqpo.librarylog4a.Log4a;
import me.pqpo.librarylog4a.appender.Appender;
import me.pqpo.librarylog4a.appender.FileAppender;
import me.pqpo.librarylog4a.logger.AppenderLogger;
import me.pqpo.librarylog4a.logger.Logger;

/**
 * Created by tom on 2016/7/22.
 */
public class DebugLogManagerActivity extends AppCompatActivity {

    private DebugLogManagerAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_log_manager);
        initView();
        Log4a.flush();
        readLogFiles();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new DebugLogManagerAdapter();
        listAdapter.setOnItemClickListener(new DebugLogManagerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position, File file) {
                Intent intent = new Intent(DebugLogManagerActivity.this, DebugLogsActivity.class);
                intent.putExtra("LogFile", file);
                startActivity(intent);
            }

        });
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_debug_log_manager, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear) {
            clearLogFiles();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<File> getLogFiles() {
        List<File> logFiles = new ArrayList<>();

        Logger logger = Log4a.getLogger();
        if (logger != null) {
            if (logger instanceof AppenderLogger) {
                List<Appender> appenders = ((AppenderLogger) logger).getAppenderList();

                if (appenders != null) {
                    for (Appender appender : appenders) {
                        if (appender instanceof FileAppender) {
                            FileAppender fileAppender = (FileAppender) appender;
                            String logFile = fileAppender.getLogPath();

                            if (!TextUtils.isEmpty(logFile)) {
                                File file = new File(logFile);

                                if (file != null && file.exists() && file.isFile()) {
                                    logFiles.add(0, file);//倒序, 越新越前面
                                }
                            }
                        }
                    }
                }
            }
        }

        return logFiles;
    }

    /**
     * 清空日志文件并新建一个日志文件
     */
    private void clearLogFiles() {
        LogUtil.clear(this);
        readLogFiles();
    }

    /**
     * 读取文件目录
     */
    private void readLogFiles() {
        listAdapter.setNewData(getLogFiles());
    }

}