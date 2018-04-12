package com.example.wu.wanandroiddemo.ui.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.base.BaseActivity;
import com.example.wu.wanandroiddemo.event.LoginEvent;
import com.example.wu.wanandroiddemo.ui.register.RegisterActivity;
import com.example.wu.wanandroiddemo.util.RxBus;
import com.example.wu.wanandroiddemo.util.ToastUtil;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_pwd)
    TextInputEditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter = new LoginPresenter(this);
    }

    private void setListener() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    protected String setToolbar() {
        return "登录";
    }

    @Override
    protected boolean backIconIsVisible() {
        return true;
    }

    @Override
    public String getUserName() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPwd() {
        return etPwd.getText().toString();
    }

    @Override
    public void loginSuccess() {
        RxBus.getInstance().post(new LoginEvent());
        ToastUtil.toast(this, "登录成功");
        finish();
    }

    @Override
    public void loginFail(String msg) {
        ToastUtil.toast(this, msg);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(getUserName())) {
                    ToastUtil.toast(this, "用户名不可以为空");
                    return;
                }
                if (TextUtils.isEmpty(getPwd())) {
                    ToastUtil.toast(this, "密码不可以为空");
                    return;
                }
                mPresenter.login(getUserName(), getPwd());
                break;
            case R.id.btn_register:
                intentMethod(RegisterActivity.class, false);
                break;
            default:
                break;

        }
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }
}
