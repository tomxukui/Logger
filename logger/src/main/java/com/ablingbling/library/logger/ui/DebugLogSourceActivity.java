package com.ablingbling.library.logger.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.ablingbling.library.logger.R;
import com.ablingbling.library.logger.util.FileUtil;

import java.io.File;

/**
 * Created by tom on 2016/7/22.
 */
public class DebugLogSourceActivity extends AppCompatActivity {

    private TextView tv_content;

    private File mLogFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_log_source);
        initData();
        initView();
        readFile();
    }

    private void initData() {
        mLogFile = (File) getIntent().getSerializableExtra("LogFile");
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void readFile() {
        String content = FileUtil.readFile(mLogFile, "utf-8");
        tv_content.setText(TextUtils.isEmpty(content) ? "" : content);
    }

}