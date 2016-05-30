package com.shanghai.jingzhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.jingzhi.BaseActivity;
import com.shanghai.jingzhi.R;
import com.shanghai.jingzhi.jingzhiutils.Default_P;
import com.shanghai.jingzhi.jingzhiutils.Log;
import com.shanghai.jingzhi.jingzhiutils.Utils;
import com.shanghai.jingzhi.model.RespData;

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
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_back;
    private ImageView iv_userlogo;
    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_rememberpsw;
    private Button bt_login;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        bt_back = (Button) findViewById(R.id.bt_back);
        iv_userlogo = (ImageView) findViewById(R.id.iv_userlogo);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        cb_rememberpsw = (CheckBox) findViewById(R.id.cb_rememberpsw);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);

        bt_back.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        try {
            XXSharedPreferences s = Utils.getNewSharedObject(Default_P.FileName_Login_Check);
            String uname_shared = "";
            String psw_shared = "";
            uname_shared = s.get(getApplicationContext(), Default_P.Key_UserName, "").toString();
            psw_shared = s.get(getApplicationContext(), Default_P.Key_PassWord, "").toString();
            if (!psw_shared.equals("") || !uname_shared.equals("")) {
                et_username.setText(uname_shared);
                et_password.setText(psw_shared);
                Log.d("检测到有记住的密码,设置到view成功");
            } else {
                Log.e("未检测到有记住的密码");
            }
            //set user logo
            XXSharedPreferences ss_userstate = Utils.getNewSharedObject(Default_P.FileName_UserState);
            String ulogo = ss_userstate.get(getApplicationContext(), Default_P.Key_UserLogoUrl, "").toString();
            Log.d("即将设置的默认用户头像:"+ulogo);
            XXImagesLoader il = new XXImagesLoader(this, null, true, null, null,null);
            il.disPlayImage(ulogo, iv_userlogo);
        } catch (Throwable throwable) {
            Log.e("未检测到有记住的密码");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.bt_login:

                if (TextUtils.isEmpty(et_password.getText().toString())
                        || TextUtils.isEmpty(et_username.getText().toString())) {
                    Utils.Toast(this, "用户名或密码不可为空");
                    return;
                }
                XXSVProgressHUD.showWithStatus(LoginActivity.this, "正在登录...");
                final String username = et_username.getText().toString();
                final String password = et_password.getText().toString();

                //登录操作
                Map<String, String> map = new HashMap<>();
                map.put("reqtype", "1");
                map.put("username", username);
                map.put("password", password);
                XXUtils.StartRequest(this, map, Default_P.URL, "Login", new OnStartRequestListener() {
                    @Override
                    public void Succ(String data) {
                        Utils.dissmiss(LoginActivity.this);
                        Log.d("登录返回数据:" + data);
                        if (data != null
                                && !TextUtils.isEmpty(data)) {
                            RespData respData = new Gson().fromJson(data, RespData.class);
                            if (respData.getCode() == 9000) {
                                Utils.Toast(getApplicationContext(), "登录成功");
                                Log.d("isCheck :" + cb_rememberpsw.isChecked());
                                Log.d(cb_rememberpsw.isChecked() ? "isChecked=true,开始记住密码操作" : "isChecked=false,不执行记住密码操纵");
                                if (cb_rememberpsw.isChecked()) {
                                    //记住密码
                                    saveUserInfosToShared(respData.getRespData().getUsername(), password);
                                }
                                savaUserStateToSharedFile(respData.getRespData().getUsername(),
                                        respData.getRespData().getNickname(),
                                        respData.getRespData().getSessionid(),
                                        respData.getRespData().getUserlogo());
//                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                XXImagesLoader imagesLoader = new XXImagesLoader(LoginActivity.this, null, true, null, null, null);
//                                imagesLoader.disPlayImage("http://b.hiphotos.baidu.com/image/h%3D200/sign=954a2073cfef7609230b9e9f1edfa301/810a19d8bc3eb135aa449355a21ea8d3fc1f4458.jpg",iv_userlogo);
                                imagesLoader.disPlayImage(respData.getRespData().getUserlogo(), iv_userlogo);


                                finish();

                            } else {
                                Utils.Toast(getApplicationContext(), "登录失败," + respData.getRespMsg());
                            }
                        } else {
                            Utils.Toast(getApplicationContext(), "登录失败,请稍后再试");
                        }

                    }

                    @Override
                    public void Error(String errorMsg) {
                        Log.e("登录失败," + errorMsg);
                        Utils.dissmiss(LoginActivity.this);
                        Utils.Toast(getApplicationContext(), "登录失败," + errorMsg);
                    }
                });


                break;
            case R.id.bt_register:
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 0x01);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult,requestCode:" + requestCode);
        if (requestCode == 0x01) {
            if (data == null) {
                return;
            }
            et_username.setText(data.getExtras().getString(Default_P.Key_UserName));
            et_password.setText(data.getExtras().getString(Default_P.Key_PassWord));
        }
    }

    private void savaUserStateToSharedFile(String username, String nickname, String sessionid, String userlogo) {
        Log.d("will saved userinfo:");
        Log.d("will saved to shared file user infos:\nusername:" + username + "\nnickname:" + nickname + "\nsessionid:" + sessionid + "\nuserlogo:" + userlogo);

        XXSharedPreferences sharedPreferences_userstate = Utils.getNewSharedObject(Default_P.FileName_UserState);
        sharedPreferences_userstate.put(LoginActivity.this, Default_P.Key_UserName, username);
        sharedPreferences_userstate.put(LoginActivity.this, Default_P.Key_UserNickName, nickname);
        sharedPreferences_userstate.put(LoginActivity.this, Default_P.Key_Sessionid, sessionid);
        sharedPreferences_userstate.put(LoginActivity.this, Default_P.Key_UserLogoUrl, userlogo);
        Default_P.isLogin = true;
        Log.d("用户身份信息保存成功,isLogon:" + Default_P.isLogin);
    }

    private void saveUserInfosToShared(String username, String password) {
        XXSharedPreferences preferences = Utils.getNewSharedObject(Default_P.FileName_Login_Check);
        preferences.put(this, Default_P.Key_UserName, username);
        preferences.put(this, Default_P.Key_PassWord, password);
        Log.d("已将用户名与密码保存至共享参数:" + Default_P.FileName_Login_Check);
    }

}
