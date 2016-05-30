package com.shanghai.jingzhi;

import android.app.Application;

import com.shanghai.jingzhi.jingzhiutils.Log;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application-onCreate");
    }

}
