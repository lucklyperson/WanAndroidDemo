package com.example.wu.wanandroiddemo.dagger2.component;

import android.content.Context;

import com.example.wu.wanandroiddemo.dagger2.module.ApplicationModule;
import com.example.wu.wanandroiddemo.dagger2.scope.PerApp;

import dagger.Component;

/**
 * Created by wu on 2018/3/21.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context getApplication();
}
