package com.shanghai.jingzhi;

import android.app.Application;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
            option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
            option.setScanSpan(2000);//设置发起定位请求的间隔时间为5000ms
            option.setIsNeedAddress(true);//返回的定位结果包含地址信息
            option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
            App.mLocationClient.setLocOption(option);
            Log.d("百度SDK相关数据初始化成功");
        } catch (Throwable throwable) {
            Log.e("百度SDK相关数据初始化失败,抛出异常," + throwable.getMessage());
        }
    }

}
