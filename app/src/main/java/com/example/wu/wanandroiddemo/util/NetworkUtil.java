package com.example.wu.wanandroiddemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/12/14.
 * 判断网络是否连接
 */
public class NetworkUtil {
    public static boolean isNetworkOk(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            return info.isAvailable();
        } else {
            return false;
        }
    }

}
