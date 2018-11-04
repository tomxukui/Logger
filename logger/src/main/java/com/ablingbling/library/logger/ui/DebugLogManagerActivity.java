package com.ablingbling.library.logger.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ablingbling.library.logger.LogUtil;
import com.ablingbling.library.logger.R;
import com.ablingbling.library.logger.adapter.DebugLogManagerAdapter;
import com.ablingbling.library.logger.bean.LogFile;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by tom on 2016/7/22.
 */
public class DebugLogManagerActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {

    private DebugLogManagerAdapter listAdapter;

    private final class ComparatorValues implements Comparator<LogFile> {

        @Override
        public int compare(LogFile object1, LogFile object2) {
            Date date1 = object1.getDate();
            Date date2 = object2.getDate();

            if (date1.before(date2)) {
                return 1;

            } else if (date1.after(date2)) {
                return -1;

            } else {
                return 0;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_log_manager);
        initView();

        readLogFiles();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new DebugLogManagerAdapter();
        listAdapter.setOnItemClickListener(this);
        listAdapter.setOnItemLongClickListener(this);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private List<LogFile> getLogFiles() {
        List<LogFile> logFiles = new ArrayList<>();

        File dir = new File(LogUtil.mFolderPath);

        if (dir != null && dir.exists()) {
            File[] files = dir.listFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];

                    if (file != null && file.exists() && file.isFile()) {
                        LogFile logFile = new LogFile();
                        logFile.setFileName(file.getName());
                        logFile.setFilePath(file.getAbsolutePath());
                        logFile.setDate(new Date(Long.parseLong(file.getName().replace(".log", "").split("\\.")[0])));

                        logFiles.add(logFile);
                    }
                }
            }
        }

        //排序
        if (logFiles != null && logFiles.size() > 0) {
            Collections.sort(logFiles, new ComparatorValues());
        }

        return logFiles;
    }

    /**
     * 清空日志文件并新建一个日志文件
     */
    private void clearLogFiles() {
        emptyFolder(LogUtil.mFolderPath);
        LogUtil.create();
        readLogFiles();
    }

    /**
     * 读取文件目录
     */
    private void readLogFiles() {
        listAdapter.setNewData(getLogFiles());
    }

    /**
     * 清空文件夹
     */
    private void emptyFolder(String path) {
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

    /**
     * 删除日志文件
     */
    private void deleteLogFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        File file = new File(path);
        if (file == null || !file.exists()) {
            return;
        }

        if (file.isFile()) {
            file.delete();
        }
    }

    /******************************************
     * 监听事件
     ********************************************/
    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        final LogFile item = listAdapter.getItem(position);

        new AlertDialog.Builder(this).setMessage("是否删除该文件?").setPositiveButton("删除", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteLogFile(item.getFilePath());

                readLogFiles();

                if (listAdapter.getData().size() == 0) {
                    LogUtil.create();
                }
            }

        }).setNegativeButton("取消", null).show();

        return true;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LogFile item = listAdapter.getItem(position);

        Intent intent = new Intent(this, DebugLogsActivity.class);
        intent.putExtra("LogFile", item);
        startActivity(intent);
    }

}