package com.ablingbling.app.logger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.ablingbling.library.logger.LogUtil;
import com.ablingbling.library.logger.ui.DebugLogManagerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.create();

        findViewById(R.id.btn_log).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtil.e("dd", "这是一条日志-" + System.currentTimeMillis());
            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (LogUtil.mEnableLog && keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && event.getAction() == KeyEvent.ACTION_DOWN) {//音量-
            startActivity(new Intent(MainActivity.this, DebugLogManagerActivity.class));
            return true;

        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
