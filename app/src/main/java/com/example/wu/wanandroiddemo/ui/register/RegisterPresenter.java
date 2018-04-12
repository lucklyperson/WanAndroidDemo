package com.example.wu.wanandroiddemo.ui.register;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.bean.User;
import com.example.wu.wanandroiddemo.net.ApiService;
import com.example.wu.wanandroiddemo.net.RetrofitManager;
import com.example.wu.wanandroiddemo.util.LogUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wu on 2018/3/22.
 */

public class RegisterPresenter implements BaseContract.BasePresenter {


    private RegisterView mRegisterView;

    public RegisterPresenter(RegisterView registerView) {
        this.mRegisterView = registerView;
    }

    protected void register(String username, String pwd) {
        RetrofitManager.create(ApiService.class)
                .register(username, pwd, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse<User>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("111", "error=" + e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse<User> response) {
                        LogUtil.d("111", "error=" + response.toString());
                        if (response.getErrorCode() == 0) {
                            mRegisterView.registerSuccess();
                        } else {
                            mRegisterView.registerFail((String) response.getErrorMsg());
                        }
                    }
                });

    }


}
