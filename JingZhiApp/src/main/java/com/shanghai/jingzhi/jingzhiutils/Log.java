package com.shanghai.jingzhi.jingzhiutils;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class Log {
    public static final String TAG = "JingZhiApp";

    public static void d(String msg) {
        android.util.Log.d(TAG, msg);
    }

    public static void e(String msg) {
        android.util.Log.e(TAG, msg);
    }

    public static void w(String msg) {
        android.util.Log.w(TAG, msg);
    }
}
