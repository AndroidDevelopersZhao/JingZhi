package com.shanghai.jingzhi.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shanghai.jingzhi.R;
import com.shanghai.jingzhi.jingzhiutils.Default_P;
import com.shanghai.jingzhi.jingzhiutils.Log;
import com.shanghai.jingzhi.jingzhiutils.Utils;
import com.shanghai.jingzhi.model.Data_Resp_GetImages;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.com.xxutils.adapter.XXListViewAdapter;
import cn.com.xxutils.gridviewinfo.DynamicHeightImageView;
import cn.com.xxutils.gridviewinfo.StaggeredGridView;
import cn.com.xxutils.interfac.OnStartRequestListener;
import cn.com.xxutils.util.XXImagesLoader;
import cn.com.xxutils.util.XXUtils;
import cn.com.xxutils.view.XXRoundImageView;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class F_Home extends Fragment {
    private View view;
    private StaggeredGridView gv_home;
    private Random mRandom = new Random();
    private XXListViewAdapter<Data_Resp_GetImages.RespDataBean.ImagesBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = view == null ? inflater.inflate(R.layout.f_home, null) : view;
        Log.d("F_Home-onCreateView,view-" + view);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("F_Home-onActivityCreated");
        getDataAndSetToView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("F_Home-onStart");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("F_Home-onCreate");
    }

    private void getDataAndSetToView() {
        Map<String, String> map = new HashMap<>();
        map.put("reqtype", "11");
        map.put("city", "上海市");
        XXUtils.StartRequest(getActivity(), map, Default_P.URL, "GetImagesUseCity", new OnStartRequestListener() {
            @Override
            public void Succ(String data) {
                Log.d("根据城市获取图片返回:" + data);
                if (data.length() == 0) {
                    Utils.Toast(getActivity(), "获取失败,请稍后再试");
                    return;
                }
                Data_Resp_GetImages images = new Gson().fromJson(data, Data_Resp_GetImages.class);

                if (images.getCode() == 9000) {
                    for (int i = 0; i < images.getRespData().getImages().size(); i++) {
                        Log.d("nickname:" + images.getRespData().getImages().get(i).getNickName());
                        Log.d("imageUrl:" + images.getRespData().getImages().get(i).getUrl());
                        Log.d("userlogo:" + images.getRespData().getImages().get(i).getUserlogo());
                        adapter.addItem(images.getRespData().getImages().get(i));
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Utils.Toast(getActivity(), "获取失败," + images.getRespMsg());
                }
            }

            @Override
            public void Error(String errorMsg) {
                Log.e("请求图片错误," + errorMsg);
            }
        });
    }
    private void initView(View view) {
        gv_home = (StaggeredGridView) view.findViewById(R.id.gv_home);
        adapter = new XXListViewAdapter<Data_Resp_GetImages.RespDataBean.ImagesBean>(getActivity(), R.layout.item_gv_home) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView tv_home_nickname = (TextView) convertView.findViewById(R.id.tv_home_nickname);
                tv_home_nickname.setText(getItem(position).getNickName());
                XXRoundImageView iv_gv_userlogo = (XXRoundImageView) convertView.findViewById(R.id.iv_gv_userlogo);
                DynamicHeightImageView iv_home_gv = (DynamicHeightImageView) convertView.findViewById(R.id.iv_home_gv);
                XXImagesLoader imagesLoader = new XXImagesLoader(getActivity(), null, true, null, null, null);
                double positionHeight = getPositionRatio(position);
                iv_home_gv.setHeightRatio(positionHeight);
                imagesLoader.disPlayImage(getItem(position).getUrl(), iv_home_gv);
                iv_gv_userlogo.setImageResource(R.drawable.wait);
            }
        };
        gv_home.setAdapter(adapter);
    }

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
}
