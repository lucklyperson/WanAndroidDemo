package com.example.wu.wanandroiddemo.dagger2.module;

import android.app.Activity;
import android.content.Context;

import com.example.wu.wanandroiddemo.dagger2.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wu on 2018/3/19.
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    Context provideActivity() {
        return mActivity;
    }

}
