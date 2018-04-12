package com.example.wu.wanandroiddemo.ui.login;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.bean.User;
import com.example.wu.wanandroiddemo.constant.Constant;
import com.example.wu.wanandroiddemo.net.ApiService;
import com.example.wu.wanandroiddemo.net.RetrofitManager;
import com.example.wu.wanandroiddemo.util.LogUtil;
import com.example.wu.wanandroiddemo.util.SharedPreferencesUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wu on 2018/3/22.
 */

public class LoginPresenter implements BaseContract.BasePresenter {

    private static final String TAG = "LoginActivity";

    private LoginView loginView;

    LoginPresenter(LoginView view) {
        this.loginView = view;
    }

    public void login(String username, String pwd) {
        RetrofitManager.create(ApiService.class)
                .login(username, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse<User>>() {

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "e = " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse<User> response) {
                        if (response.getErrorCode() == 0) {
                            loginView.loginSuccess();
                            SharedPreferencesUtil.getInstance().saveUser(Constant.SP_USER, response.getData());
                        } else {
                            loginView.loginFail((String) response.getErrorMsg());
                        }
                    }
                });
    }


}
