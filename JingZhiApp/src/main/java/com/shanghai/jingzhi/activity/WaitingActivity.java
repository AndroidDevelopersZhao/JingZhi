package com.shanghai.jingzhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.shanghai.jingzhi.BaseActivity;
import com.shanghai.jingzhi.jingzhiutils.Log;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class WaitingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WaitingActivity-onCreate");
        SystemClock.sleep(2000);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("WaitingActivity-onDestroy");
    }
}
