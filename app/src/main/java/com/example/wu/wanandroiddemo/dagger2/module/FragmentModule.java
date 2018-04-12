package com.example.wu.wanandroiddemo.dagger2.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.wu.wanandroiddemo.dagger2.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wu on 2018/3/19.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @PerFragment
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @PerFragment
    Activity proviceActivity() {
        return mFragment.getActivity();
    }

}
