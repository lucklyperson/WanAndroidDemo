package com.example.wu.wanandroiddemo.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.wu.wanandroiddemo.util.LogUtil;

/**
 * Created by wu on 2018/3/23.
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    private static final String TAG = "VIEW";

    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.e(TAG, "refresh_dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtil.e(TAG, "refresh_onTouchEvent");
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.e(TAG, "refresh_onInterceptTouchEvent");
        //return super.onInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

}
