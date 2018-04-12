package com.example.wu.wanandroiddemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.wu.wanandroiddemo.util.LogUtil;

/**
 * Created by wu on 2018/3/23.
 */

public class MyRecyclerView extends RecyclerView {
    private static final String TAG = "VIEW";

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i(TAG, "recycler_dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        LogUtil.i(TAG, "recycler_onTouchEvent");
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        LogUtil.i(TAG, "recycler_onInterceptTouchEvent");
        return super.onInterceptTouchEvent(e);
    }
}
