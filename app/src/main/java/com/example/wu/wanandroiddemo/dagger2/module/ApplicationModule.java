package com.example.wu.wanandroiddemo.dagger2.module;

import android.content.Context;

import com.example.wu.wanandroiddemo.base.App;
import com.example.wu.wanandroiddemo.dagger2.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wu on 2018/3/19.
 */
@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }

}
