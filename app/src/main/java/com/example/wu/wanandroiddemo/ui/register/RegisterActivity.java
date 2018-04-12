package com.example.wu.wanandroiddemo.ui.register;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.base.BaseActivity;
import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.util.ToastUtil;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity<BaseContract.BasePresenter> implements RegisterView, View.OnClickListener {

    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_pwd)
    TextInputEditText etPwd;
    @BindView(R.id.et_pwd_again)
    TextInputEditText etPwdAgain;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnRegister.setOnClickListener(this);
        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String setToolbar() {
        return "注册";
    }

    @Override
    protected boolean backIconIsVisible() {
        return true;
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPwd() {
        return etPwd.getText().toString();
    }

    @Override
    public String getPwdAgain() {
        return etPwdAgain.getText().toString();
    }


    @Override
    public void registerSuccess() {
        ToastUtil.toast(this, "注册成功，请重新登录");
        finish();
    }

    @Override
    public void registerFail(String msg) {
        ToastUtil.toast(this, msg);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(getUsername())) {
            ToastUtil.toast(this, "用户名不可以为空");
            return;
        }
        if (TextUtils.isEmpty(getPwd()) || TextUtils.isEmpty(getPwdAgain())) {
            ToastUtil.toast(this, "密码不可以为空");
            return;
        }
        if (!TextUtils.equals(getPwd(), getPwdAgain())) {
            ToastUtil.toast(this, "两次的密码不一致");
            return;
        }

        registerPresenter.register(getUsername(), getPwd());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter = null;
    }
}
