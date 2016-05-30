package com.shanghai.jingzhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.jingzhi.BaseActivity;
import com.shanghai.jingzhi.R;
import com.shanghai.jingzhi.jingzhiutils.Default_P;
import com.shanghai.jingzhi.jingzhiutils.Log;
import com.shanghai.jingzhi.jingzhiutils.Utils;
import com.shanghai.jingzhi.model.Data_BackRegister;

import java.util.HashMap;
import java.util.Map;

import cn.com.xxutils.interfac.OnStartRequestListener;
import cn.com.xxutils.progress.XXSVProgressHUD;
import cn.com.xxutils.util.XXUtils;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_back;
    private Button bt_submit;
    private Button bt_cancle;
    private EditText et_username;
    private EditText et_password;
    private EditText et_password_2;
    private EditText et_birthday;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        bt_back = (Button) findViewById(R.id.bt_back);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_cancle = (Button) findViewById(R.id.bt_cancle);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_2 = (EditText) findViewById(R.id.et_password_2);
        et_birthday = (EditText) findViewById(R.id.et_birthday);
        et_email = (EditText) findViewById(R.id.et_email);

        bt_back.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        bt_cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                this.finish();
                break;
            case R.id.bt_submit:
                if (TextUtils.isEmpty(et_username.getText().toString())
                        || TextUtils.isEmpty(et_password.getText().toString())
                        || TextUtils.isEmpty(et_password_2.getText().toString())
                        || TextUtils.isEmpty(et_birthday.getText().toString())
                        || TextUtils.isEmpty(et_email.getText().toString())) {
                    Utils.Toast(getApplicationContext(), "参数不能为空");
                    return;
                }
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String password_2 = et_password_2.getText().toString();
                String birthday = et_birthday.getText().toString();
                String email = et_email.getText().toString();
                if (!password.equals(password_2)) {
                    Utils.Toast(getApplicationContext(), "密码输入不一致,请重新输入");
                    return;
                } else {
                    if (et_password.getText().toString().trim().length() < 6) {
                        Utils.Toast(getApplicationContext(), "密码太简单,请重新输入");
                        return;
                    }
                }
                if (!XXUtils.checkEmailValid(email)) {
                    Utils.Toast(getApplicationContext(), "邮箱不符合要求,请重新输入");
                    return;
                }
                startRegister(username, password, birthday, email);
                break;
            case R.id.bt_cancle:

                break;
        }
    }

    private void startRegister(final String username, final String password, String birthday, final String email) {
        Log.d("start register number");
        XXSVProgressHUD.showWithStatus(RegisterActivity.this, "正在注册账号,请稍后...");
        Map<String, String> map = new HashMap<>();
        map.put("reqtype", "5");
        map.put("username", username);
        map.put("password", password);
        map.put("birthday", birthday);
        map.put("email", email);
        XXUtils.StartRequest(this, map, Default_P.URL, "Register", new OnStartRequestListener() {
            @Override
            public void Succ(String data) {
                Utils.dissmiss(RegisterActivity.this);
                Log.d("注册返回数据," + data);
                if (!TextUtils.isEmpty(data)) {
                    Data_BackRegister register = new Gson().fromJson(data, Data_BackRegister.class);
                    if (register.getCode() == 9000) {
                        Utils.Toast(getApplicationContext(), "注册成功");
                        Intent intent = new Intent();
                        intent.putExtra(Default_P.Key_UserName, username);
                        intent.putExtra(Default_P.Key_PassWord, password);
                        setResult(0x01, intent);
                        finish();
                    } else {
                        Utils.Toast(getApplicationContext(), "注册失败," + register.getRespMsg());
                    }
                } else {
                    Utils.Toast(getApplicationContext(), "注册失败,请稍后再试");
                }
            }

            @Override
            public void Error(String errorMsg) {
                Utils.dissmiss(RegisterActivity.this);
                Utils.Toast(getApplicationContext(), errorMsg);
                Log.e("注册失败," + errorMsg);
            }
        });
    }
}
