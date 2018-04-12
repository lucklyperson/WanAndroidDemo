package com.example.wu.wanandroiddemo.ui.login;

import com.example.wu.wanandroiddemo.base.BaseContract;

/**
 * Created by wu on 2018/3/22.
 */

public interface LoginView extends BaseContract.BaseView {

    String getUserName();

    String getPwd();

    void loginSuccess();

    void loginFail(String msg);


}
