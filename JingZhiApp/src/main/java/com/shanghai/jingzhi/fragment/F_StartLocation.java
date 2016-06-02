package com.shanghai.jingzhi.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
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
import com.shanghai.jingzhi.jingzhiutils.Default_P;
import com.shanghai.jingzhi.jingzhiutils.Log;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class F_StartLocation extends Fragment {
    private View view;
    public static MapView bmapView;
    private BaiduMap baiduMap;
    boolean s = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_startlocation, null);
        Log.d("F_StartLocation-onCreateView,view-" + view);
        s = true;
        initView();
        //定位并在地图上标记出当前位置
        locationToMap();
//        {
//            //测试百度地图相关代码
//
//
//            // 开启定位图层
//            App.mLocationClient.registerLocationListener(new BDLocationListener() {
//                @Override
//                public void onReceiveLocation(BDLocation bdLocation) {
//                    Log.d("定位成功:" + bdLocation.getCity());
//                    Log.d("定位成功:" + bdLocation.getAddrStr());
//                    Log.d("定位成功:" + bdLocation.getCountry());
//                    Log.d("定位成功:" + bdLocation.getProvince());
//                    Log.d("定位成功:" + bdLocation.getAltitude());
//                    Log.d("经度:" + bdLocation.getLongitude());
//                    Log.d("weidu:" + bdLocation.getLatitude());
//                    baiduMap.setMyLocationEnabled(true);
//                    // 构造定位数据
//                    MyLocationData locData = new MyLocationData.Builder()
//                            .accuracy(bdLocation.getRadius())
//                            // 此处设置开发者获取到的方向信息，顺时针0-360
//                            .direction(100).latitude(bdLocation.getLatitude())
//                            .longitude(bdLocation.getLongitude()).build();
//                    // 设置定位数据
//                    baiduMap.setMyLocationData(locData);
//                    // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//                    MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, BitmapDescriptorFactory
//                            .fromResource(R.drawable.icon_geo));
//                    baiduMap.setMyLocationConfigeration(config);
//                    LatLng ll = new LatLng(bdLocation.getLatitude(),
//                            bdLocation.getLongitude());
//                    MapStatus.Builder builder = new MapStatus.Builder();
//                    builder.target(ll).zoom(38.0f);
//                    //888888888888888
//                    baiduMap.clear();
//                    LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//                    //构建Marker图标
//                    BitmapDescriptor bitmap = BitmapDescriptorFactory
//                            .fromResource(R.drawable.icon_marka);
//                    //构建MarkerOption，用于在地图上添加Marker
//                    OverlayOptions option = new MarkerOptions()
//                            .position(point)
//                            .icon(bitmap);
//                    //在地图上添加Marker，并显示
//                    baiduMap.addOverlay(option);
//
//
//                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                    // 当不需要定位图层时关闭定位图层
//                    baiduMap.setMyLocationEnabled(false);
//                }
//            });
//            App.mLocationClient.start();
//
//        }
        return view;
    }

    private void locationToMap() {
        //注册定位监听
        App.mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(final BDLocation bdLocation) {
                Log.d("定位成功,当前所在城市:" + bdLocation.getCity() + "\n当前位置的经度:" + bdLocation.getLatitude() + "\n当前位置的纬度:" + bdLocation.getLongitude());
//                Log.d("清除地图上的标记");
//                baiduMap.clear();
                Log.d("开启检测位置信息");
                baiduMap.setMyLocationEnabled(true);
                Log.d("设置定位的数据到map");
                setLocationToMap(bdLocation);
//                Log.d("在地图上标记当前位置");
//                overLayToMap(bdLocation);
                Log.d("一个操作过程成功(每一次定位成功均为一个操作过程)");

                baiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        Log.d("截屏成功,已赋值Default-P:bitmap");
                        Default_P.bitmap = bitmap;
                        Log.d("当前得到的bitmap:" + bitmap);
                        if (s) {
                            Default_P.city = bdLocation.getCity();
                            Default_P.lnt = bdLocation.getLatitude() + "";
                            Default_P.lat = bdLocation.getLongitude() + "";
                            s = false;
                        }
                    }
                });
            }


        });
        //开启定位
        App.mLocationClient.start();
    }

    private void overLayToMap(BDLocation bdLocation) {
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
    }

    private void setLocationToMap(BDLocation bdLocation) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())//设置地图精确信息(单位:米)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        // 设置定位数据
        baiduMap.setMyLocationData(locData);
        Log.d("设置定位图层信息");
//        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_gcoding),0,0);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null, 0, 0);
        baiduMap.setMyLocationConfigeration(config);
        LatLng ll = new LatLng(bdLocation.getLatitude(),
                bdLocation.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(20.0f);//设置地图缩放级别
        Log.d("设置定位后地图成像的动画");
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private void initView() {

        bmapView = (MapView) view.findViewById(R.id.bmapView);
        //设置地图信息
        setMapInfos();
    }

    private void setMapInfos() {
        baiduMap = bmapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置为普通视图
    }
}
