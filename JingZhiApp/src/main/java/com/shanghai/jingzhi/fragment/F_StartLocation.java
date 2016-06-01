package com.shanghai.jingzhi.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.shanghai.jingzhi.App;
import com.shanghai.jingzhi.R;
import com.shanghai.jingzhi.jingzhiutils.Log;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class F_StartLocation extends Fragment implements View.OnClickListener {
    private View view;
    public static MapView bmapView;
    private BaiduMap baiduMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.f_startlocation, null);
        Log.d("F_StartLocation-onCreateView,view-" + view);
        initView();
        {
            //测试百度地图相关代码
            baiduMap = bmapView.getMap();
            baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置为普通视图
            // 开启定位图层
            App.mLocationClient.registerLocationListener(new BDLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    Log.d("定位成功:" + bdLocation.getCity());
                    Log.d("定位成功:" + bdLocation.getAddrStr());
                    Log.d("定位成功:" + bdLocation.getCountry());
                    Log.d("定位成功:" + bdLocation.getProvince());
                    Log.d("定位成功:" + bdLocation.getAltitude());
                    Log.d("经度:" + bdLocation.getLongitude());
                    Log.d("weidu:" + bdLocation.getLatitude());
                    baiduMap.setMyLocationEnabled(true);
// 构造定位数据
                    MyLocationData locData = new MyLocationData.Builder()
                            .accuracy(bdLocation.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(100).latitude(bdLocation.getLatitude())
                            .longitude(bdLocation.getLongitude()).build();
// 设置定位数据
                    baiduMap.setMyLocationData(locData);
// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                    MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_geo));
                    baiduMap.setMyLocationConfigeration(config);
                    LatLng ll = new LatLng(bdLocation.getLatitude(),
                            bdLocation.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    //888888888888888
                    LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_marka);
//构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);
//在地图上添加Marker，并显示
                    baiduMap.addOverlay(option);

                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
// 当不需要定位图层时关闭定位图层
                    baiduMap.setMyLocationEnabled(false);
                }
            });
            App.mLocationClient.start();

        }
        return view;
    }

    private void initView() {

        bmapView = (MapView) view.findViewById(R.id.bmapView);
        bmapView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bmapView:

                break;
        }
    }
}
