package com.shanghai.jingzhi.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.shanghai.jingzhi.App;
import com.shanghai.jingzhi.BaseActivity;
import com.shanghai.jingzhi.R;
import com.shanghai.jingzhi.fragment.F_Home;
import com.shanghai.jingzhi.fragment.F_L_C;
import com.shanghai.jingzhi.fragment.F_Search;
import com.shanghai.jingzhi.fragment.F_Setting;
import com.shanghai.jingzhi.fragment.F_StartLocation;
import com.shanghai.jingzhi.jingzhiutils.Default_P;
import com.shanghai.jingzhi.jingzhiutils.Log;
import com.shanghai.jingzhi.jingzhiutils.Utils;

import cn.com.xxutils.alerterview.OnItemClickListener;
import cn.com.xxutils.alerterview.XXAlertView;
import cn.com.xxutils.util.XXSharedPreferences;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_userinfo;
    private Button bt_home;
    private Button bt_setting;
    private Button bt_l_c;
    private Button bt_location;
    private Button bt_search;
    private Fragment[] fragments = {new F_Home(), new F_Setting(), new F_L_C(), new F_StartLocation(), new F_Search()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        Log.d("MainActivity-onCreate");
        Utils.replaceFragment(this, fragments[0]);
        setUserSate();
        Log.d("数据获取完成,开启定位服务");
        startGetUserLocation();
    }

    private void startGetUserLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        App.mLocationClient.setLocOption(option);
//        App.mLocationClient.registerLocationListener(new BDLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d("定位成功:" + bdLocation.getCity());
//                Log.d("定位成功:" + bdLocation.getAddrStr());
//                Log.d("定位成功:" + bdLocation.getCountry());
//                Log.d("定位成功:" + bdLocation.getProvince());
//                Log.d("定位成功:" + bdLocation.getAltitude());
//                Log.d("经度:" + bdLocation.getLongitude());
//                Log.d("weidu:" + bdLocation.getLatitude());
//            }
//        });
//        App.mLocationClient.start();
    }

    private void setUserSate() {
        XXSharedPreferences ss = Utils.getNewSharedObject(Default_P.FileName_UserState);
        String username = ss.get(getApplicationContext(), Default_P.Key_UserName, "").toString();
        String sessionid = ss.get(getApplicationContext(), Default_P.Key_Sessionid, "").toString();
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(sessionid)) {
            Default_P.isLogin = true;
        } else {
            Default_P.isLogin = false;
        }
        Log.d("setUserSate-isLogin:" + Default_P.isLogin);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setUserSate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity-onActivityResult-resultCode:" + resultCode);
        if (resultCode == 0x01) {
            finish();
        }
    }

    private void initView() {
        bt_userinfo = (Button) findViewById(R.id.bt_userinfo);
        bt_home = (Button) findViewById(R.id.bt_home);
        bt_setting = (Button) findViewById(R.id.bt_setting);
        bt_l_c = (Button) findViewById(R.id.bt_l_c);
        bt_location = (Button) findViewById(R.id.bt_location);
        bt_search = (Button) findViewById(R.id.bt_search);

        bt_userinfo.setOnClickListener(this);
        bt_home.setOnClickListener(this);
        bt_setting.setOnClickListener(this);
        bt_l_c.setOnClickListener(this);
        bt_location.setOnClickListener(this);
        bt_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_userinfo:
                Log.d("userinfo - isLogin:" + Default_P.isLogin);
                if (Default_P.isLogin) {
                    startActivityForResult(new Intent(this, UserInfoActivity.class), 0x01);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.bt_home:
                if (!fragments[0].isVisible())
                    Utils.replaceFragment(this, fragments[0]);

                break;
            case R.id.bt_setting:
                if (!fragments[1].isVisible())
                    Utils.replaceFragment(this, fragments[1]);

                break;
            case R.id.bt_l_c:
                if (!fragments[2].isVisible())
                    Utils.replaceFragment(this, fragments[2]);

                break;
            case R.id.bt_location:
                if (!fragments[3].isVisible())
                    Utils.replaceFragment(this, fragments[3]);

                break;
            case R.id.bt_search:
                if (!fragments[4].isVisible())
                    Utils.replaceFragment(this, fragments[4]);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        new XXAlertView("提示", "是否退出", "取消", new String[]{"退出"}, null, MainActivity.this, XXAlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                Log.d("position:" + position);
                if (position == 0) {
                    finish();
                }
            }
        }).show();
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        try {
            F_StartLocation.bmapView.onDestroy();
        } catch (Throwable throwable) {
            Log.e("F_StartLocation.bmapView.onDestroy()执行失败,可能是该view还未被创建");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理

        try {
            F_StartLocation.bmapView.onResume();
        } catch (Throwable throwable) {
            Log.e(" F_StartLocation.bmapView.onResume()执行失败,可能是该view还未被创建");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理

        try {
            F_StartLocation.bmapView.onPause();
        } catch (Throwable throwable) {
            Log.e(" F_StartLocation.bmapView.onPause()执行失败,可能是该view还未被创建");
        }
    }
}
