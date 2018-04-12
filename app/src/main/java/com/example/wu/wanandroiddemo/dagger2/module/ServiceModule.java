package com.example.wu.wanandroiddemo.dagger2.module;

import android.app.Service;
import android.content.Context;

import com.example.wu.wanandroiddemo.dagger2.scope.PerService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wu on 2018/3/19.
 */
@Module
public class ServiceModule {

    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @PerService
    Context provideServiceContext() {
        return mService;
    }
}
