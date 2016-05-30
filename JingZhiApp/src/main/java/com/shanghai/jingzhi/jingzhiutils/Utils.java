package com.shanghai.jingzhi.jingzhiutils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.widget.Toast;

import com.shanghai.jingzhi.R;

import cn.com.xxutils.progress.XXSVProgressHUD;
import cn.com.xxutils.util.XXSharedPreferences;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class Utils {
    public static void replaceFragment(Activity context, Fragment fragment) {
        context.getFragmentManager().beginTransaction().replace(R.id.mainview, fragment).commit();
    }

    public static void Toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据文件名获取该文件中的最新参数对象,当传入文件名不存在共享参数中时该文件将被创建
     *
     * @param fileName
     * @return
     */
    public static XXSharedPreferences getNewSharedObject(String fileName) {
        return new XXSharedPreferences(fileName);
    }

    public static void dissmiss(Context context) {
        if (XXSVProgressHUD.isShowing(context))
            XXSVProgressHUD.dismiss(context);
    }
}
