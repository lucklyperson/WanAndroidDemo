package com.example.wu.wanandroiddemo.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.util.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wu on 2018/3/8.
 * Activity基类
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity implements BaseContract.BaseView {

    private static final String TAG = "BaseActivity";

    private Unbinder unbinder;

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract String setToolbar();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        try {
            initToolBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化toolbar
     */
    protected void initToolBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("toolbar can not be null");
        }
        //获取标题
        String title = setToolbar();
        LogUtil.e(TAG, "title = " + title);
        //设置标题（如果标题为空,则设置为app_name）
        if (title == null || TextUtils.isEmpty(title)) {
            mToolbar.setTitle(getApplication().getString(R.string.app_name));
        } else {
            mToolbar.setTitle(title);
        }
        //设置阴影
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(0);
        }
        LogUtil.e(TAG, "title = " + mToolbar.getTitle());
        setSupportActionBar(mToolbar);
        //关于返回键的设置
        if (backIconIsVisible()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(setHomeAsUpIndicator());
        }
    }

    /**
     * 是否显示backIcon
     *
     * @return false
     */
    protected boolean backIconIsVisible() {
        return false;
    }

    /**
     * 设置backIcon图标
     *
     * @return drawable
     */
    protected int setHomeAsUpIndicator() {
        return R.drawable.ic_arrow_back_black_24dp;
    }

    /**
     * 界面的跳转
     *
     * @param clazz    clazz
     * @param isFinish 是否结束当前界面
     */
    protected void intentMethod(Class clazz, boolean isFinish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (isFinish) finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void onRetry() {

    }

    @Override
    public void refreshCompleted() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
