package com.shanghai.jingzhi;

import android.app.Activity;
import android.os.Bundle;

import com.shanghai.jingzhi.jingzhiutils.Log;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity-onCreate");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BaseActivity-onDestroy");
    }
}
