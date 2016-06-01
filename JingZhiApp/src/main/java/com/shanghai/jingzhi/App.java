package com.shanghai.jingzhi;

import android.app.Application;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.shanghai.jingzhi.jingzhiutils.Log;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class App extends Application {
    public static LocationClient mLocationClient;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application-onCreate");
        Log.d("初始化百度SDK相关数据");
        try {
            mLocationClient = new LocationClient(getApplicationContext());
            SDKInitializer.initialize(getApplicationContext());
        } catch (Throwable throwable) {
            Log.e("百度SDK相关数据初始化失败,抛出异常," + throwable.getMessage());
        }
        Log.d("百度SDK相关数据初始化成功");
    }

}
