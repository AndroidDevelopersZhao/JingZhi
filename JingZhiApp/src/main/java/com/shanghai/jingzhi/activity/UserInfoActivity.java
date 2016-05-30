package com.shanghai.jingzhi.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shanghai.jingzhi.BaseActivity;
import com.shanghai.jingzhi.R;
import com.shanghai.jingzhi.jingzhiutils.Default_P;
import com.shanghai.jingzhi.jingzhiutils.Log;
import com.shanghai.jingzhi.jingzhiutils.Utils;
import com.shanghai.jingzhi.model.Data_Resp_DisLogin;

import java.util.HashMap;
import java.util.Map;

import cn.com.xxutils.interfac.OnStartRequestListener;
import cn.com.xxutils.progress.XXSVProgressHUD;
import cn.com.xxutils.util.XXImagesLoader;
import cn.com.xxutils.util.XXSharedPreferences;
import cn.com.xxutils.util.XXUtils;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_userlogo;
    private TextView tv_nickname;
    private ImageView iv_2code;
    private RelativeLayout lt_album;
    private RelativeLayout lt_mymoney;
    private RelativeLayout lt_msg;
    private RelativeLayout lt_setting;
    private Button bt_disLogin;
    private RelativeLayout rl_userinfos;


    private String username = null;
    private String sessionid = null;
    private String nickname = null;
    private String userlogo = null;
    private Button bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView();
        setDataToView();
    }

    private void setDataToView() {
        getUserDataFromSharedFile();
        //set userlogo
        XXImagesLoader imagesLoader = new XXImagesLoader(this, null, true, null, null, null);
        imagesLoader.disPlayImage(userlogo, iv_userlogo);
        //set user nickname
        if (!TextUtils.isEmpty(nickname)) {
            tv_nickname.setText(nickname);
        }
    }

    private void getUserDataFromSharedFile() {
        XXSharedPreferences preferences = Utils.getNewSharedObject(Default_P.FileName_UserState);

        this.username = preferences.get(getApplicationContext(), Default_P.Key_UserName, "").toString();
        this.sessionid = preferences.get(getApplicationContext(), Default_P.Key_Sessionid, "").toString();
        this.nickname = preferences.get(getApplicationContext(), Default_P.Key_UserNickName, "").toString();
        this.userlogo = preferences.get(getApplicationContext(), Default_P.Key_UserLogoUrl, "").toString();
        Log.d("user info from saved shared :\nusername:" + username + "\nnickname:" + nickname + "\nsessionid:" + sessionid + "\nuserlogo:" + userlogo);

    }

    private void initView() {
        iv_userlogo = (ImageView) findViewById(R.id.iv_userlogo);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        iv_2code = (ImageView) findViewById(R.id.iv_2code);
        lt_album = (RelativeLayout) findViewById(R.id.lt_album);
        lt_mymoney = (RelativeLayout) findViewById(R.id.lt_mymoney);
        lt_msg = (RelativeLayout) findViewById(R.id.lt_msg);
        lt_setting = (RelativeLayout) findViewById(R.id.lt_setting);
        bt_disLogin = (Button) findViewById(R.id.bt_disLogin);

        lt_album.setOnClickListener(this);
        lt_mymoney.setOnClickListener(this);
        lt_msg.setOnClickListener(this);
        lt_setting.setOnClickListener(this);
        bt_disLogin.setOnClickListener(this);
        rl_userinfos = (RelativeLayout) findViewById(R.id.rl_userinfos);
        rl_userinfos.setOnClickListener(this);
        bt_back = (Button) findViewById(R.id.bt_back);
        bt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.rl_userinfos:

                break;
            case R.id.lt_album:

                break;
            case R.id.lt_mymoney:

                break;
            case R.id.lt_msg:

                break;
            case R.id.lt_setting:

                break;
            case R.id.bt_disLogin:
                disLogin();
                break;


        }
    }

    private void disLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("reqtype", "2");
        map.put("username", username);
        map.put("sessionid", sessionid);
        XXSVProgressHUD.showWithStatus(UserInfoActivity.this, "正在为您安全退出,请稍后");
        XXUtils.StartRequest(UserInfoActivity.this, map, Default_P.URL, "dislogin", new OnStartRequestListener() {
            @Override
            public void Succ(String data) {
                Utils.dissmiss(UserInfoActivity.this);
                if (TextUtils.isEmpty(data)) {
                    Utils.Toast(getApplicationContext(), "注销失败,请稍后再试");
                    return;
                }
                Log.d("注销返回数据," + data);
                Data_Resp_DisLogin resp_disLogin = new Gson().fromJson(data, Data_Resp_DisLogin.class);
                if (resp_disLogin.getCode() == 9000) {
                    Utils.Toast(getApplicationContext(), "注销成功");
                    XXSharedPreferences sharedPreferences = Utils.getNewSharedObject(Default_P.FileName_UserState);
//                    sharedPreferences.clear(getApplicationContext());
                    sharedPreferences.put(getApplicationContext(),Default_P.Key_UserName,"");
                    sharedPreferences.put(getApplicationContext(),Default_P.Key_Sessionid,"");
                    Default_P.isLogin=false;
                    setResult(0x01);
                    finish();

                } else {
                    Utils.Toast(getApplicationContext(), "注销失败," + resp_disLogin.getRespMsg());
                }
            }

            @Override
            public void Error(String errorMsg) {
                Utils.dissmiss(UserInfoActivity.this);
                Utils.Toast(getApplicationContext(), "注销失败," + errorMsg);
            }
        });
    }
}
