package com.example.wu.wanandroiddemo.ui.register;

import com.example.wu.wanandroiddemo.base.BaseContract;

/**
 * Created by wu on 2018/3/22.
 */

public interface RegisterView extends BaseContract.BaseView {
    String getUsername();

    String getPwd();

    String getPwdAgain();

    void registerSuccess();

    void registerFail(String msg);


}
