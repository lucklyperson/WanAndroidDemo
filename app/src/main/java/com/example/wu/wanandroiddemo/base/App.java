package com.example.wu.wanandroiddemo.base;

import android.app.Application;


/**
 * Created by wu on 2018/3/19.
 */

public class App extends Application {

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
