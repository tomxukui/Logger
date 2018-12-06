package com.ablingbling.app.logger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.ablingbling.library.logger.LogUtil;
import com.ablingbling.library.logger.ui.DebugLogManagerActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_d_text).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtil.d("dd", "btn_d_text-" + System.currentTimeMillis());
            }

        });

        findViewById(R.id.btn_info_text).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtil.i("dd", "btn_info_text-" + System.currentTimeMillis());
            }

        });

        findViewById(R.id.btn_warn_text).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtil.w("dd", "btn_warn_text-" + System.currentTimeMillis());
            }

        });

        findViewById(R.id.btn_error_text).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtil.e("dd", "btn_error_text-" + System.currentTimeMillis());
            }

        });

        findViewById(R.id.btn_error_throwable).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtil.e("dd", "btn_error_throwable-" + System.currentTimeMillis(), new IOException());
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
