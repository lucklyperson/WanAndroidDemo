package com.example.wu.wanandroiddemo.util;

import android.util.Log;

import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.bean.User;
import com.example.wu.wanandroiddemo.net.ApiService;
import com.example.wu.wanandroiddemo.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wu on 2018/3/21.
 */

public class LogUtil {


    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }


    public static Observable<DataResponse<User>> login(String username, String pwd) {
        Observable<DataResponse<User>> observable = RetrofitManager.create(ApiService.class).login(username, pwd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

}


