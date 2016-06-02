package com.shanghai.jingzhi.jingzhiutils;

import android.graphics.Bitmap;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class Default_P {
    //    public static final String URL = "http://221.228.88.249:8080/IISService_2/index";
public static final String URL = "http://192.168.12.142:8080/IISService_2/index";
//    public static final String URL = "http://192.168.13.135:8080/IISService_2/index";
    public static final String BaiDuAK = "IdjQsrKRaioz8cEyrSh8rIGXrTZRAP6S";


    public static boolean isLogin = false;//用户登录状态
    public static final String FileName_Login_Check = "FileName_Login_Check";//该文件名为保存登录页面的记住密码按钮下的内容
    public static final String FileName_UserState = "FileName_UserState";//该文件名为保存用户的登录状态

    public static final String Key_UserName = "username";//key
    public static final String Key_PassWord = "password";//key
    public static final String Key_Sessionid = "sessionid";//key
    public static final String Key_UserNickName = "usernickname";//key
    public static final String Key_UserLogoUrl = "userlogo";//key
    public static Bitmap bitmap = null;//百度定位的位置图片,不断更新
    public static Bitmap finalImage = null;//最后上传到服务器的图片
    public static String lat = null;
    public static String lnt = null;
    public static String city = null;


}
