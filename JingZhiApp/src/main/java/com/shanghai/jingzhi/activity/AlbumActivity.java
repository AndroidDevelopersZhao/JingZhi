package com.shanghai.jingzhi.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shanghai.jingzhi.BaseActivity;
import com.shanghai.jingzhi.R;
import com.shanghai.jingzhi.jingzhiutils.Default_P;
import com.shanghai.jingzhi.jingzhiutils.Log;
import com.shanghai.jingzhi.jingzhiutils.Utils;
import com.shanghai.jingzhi.model.Data_Resp_GetImages;

import java.util.HashMap;
import java.util.Map;

import cn.com.xxutils.adapter.XXListViewAdapter;
import cn.com.xxutils.interfac.OnStartRequestListener;
import cn.com.xxutils.util.XXImagesLoader;
import cn.com.xxutils.util.XXListViewAnimationMode;
import cn.com.xxutils.util.XXSharedPreferences;
import cn.com.xxutils.util.XXUtils;
import cn.com.xxutils.view.XXListView;
import cn.com.xxutils.view.XXRoundImageView;

/**
 * Created by zhaowenyun on 16/6/1.
 */
public class AlbumActivity extends BaseActivity implements View.OnClickListener {
    private XXRoundImageView iv_userlogo;
    private XXSharedPreferences sharedPreferences = new XXSharedPreferences(Default_P.FileName_UserState);
    private TextView tv_nickname;
    private ImageView iv_back;
    private XXListView lv_album;
    private String sessionid;
    private String username;
    private XXImagesLoader l;
    private XXListViewAdapter<Data_Resp_GetImages.RespDataBean.ImagesBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initView();
        String userlogo = sharedPreferences.get(this, Default_P.Key_UserLogoUrl, "").toString();
        String nickName = sharedPreferences.get(this, Default_P.Key_UserNickName, "").toString();
        sessionid = sharedPreferences.get(this, Default_P.Key_Sessionid, "").toString();
        username = sharedPreferences.get(this, Default_P.Key_UserName, "").toString();

        l = new XXImagesLoader(this, null, true, null, null, null);
        l.disPlayImage(userlogo, iv_userlogo);
        tv_nickname.setText(nickName);
        Log.d("AlbumActivity-onCreate");
        getDataFromService();
    }

    private void getDataFromService() {
        Map<String, String> map = new HashMap<>();
        map.put("reqtype", "12");
        map.put("username", username);
        map.put("sessionid", sessionid);
        XXUtils.StartRequest(this, map, Default_P.URL, "GetImagesFromServiceUseUsername", new OnStartRequestListener() {
            @Override
            public void Succ(String data) {
                Log.d("数据获取成功," + data);
                if (data.length() > 0) {
                    Data_Resp_GetImages resp_getImages = new Gson().fromJson(data, Data_Resp_GetImages.class);
                    if (resp_getImages.getCode() == 9000) {
                        for (int i = 0; i < resp_getImages.getRespData().getImages().size(); i++) {
                            adapter.addItem(resp_getImages.getRespData().getImages().get(i));
                            adapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(lv_album);
                        }
                    } else {
                        Utils.Toast(AlbumActivity.this, resp_getImages.getRespMsg());
                    }
                } else {
                    Utils.Toast(AlbumActivity.this, "数据异常,请稍后再试");
                    return;
                }

            }

            @Override
            public void Error(String errorMsg) {
                Log.e("数据获取失败," + errorMsg);
            }
        });
    }

    private void initView() {
        iv_userlogo = (XXRoundImageView) findViewById(R.id.iv_userlogo);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        lv_album = (XXListView) findViewById(R.id.lv_album);
        adapter = new XXListViewAdapter<Data_Resp_GetImages.RespDataBean.ImagesBean>(this, R.layout.item_lv_album) {
            @Override
            public void initGetView(int position, View convertView, ViewGroup parent) {
                TextView tv_item_likenumber = (TextView) convertView.findViewById(R.id.tv_item_likenumber);
                TextView tv_item_location = (TextView) convertView.findViewById(R.id.tv_item_location);
                ImageView iv_item_album = (ImageView) convertView.findViewById(R.id.iv_item_album);
                tv_item_likenumber.setText(getItem(position).getLikeNumbers());
                tv_item_location.setText(getItem(position).getCity());
                l.disPlayImage(getItem(position).getUrl(), iv_item_album);
            }
        };
        lv_album.setListViewAnimation(adapter, XXListViewAnimationMode.ANIIMATION_BOTTOM_RIGHT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(0x01);
                Log.d("返回按钮被点击");
                this.finish();
                break;
            case R.id.iv_userlogo:
                Utils.Toast(this, "用户头像被点击");
                break;
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
