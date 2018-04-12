package com.example.wu.wanandroiddemo.ui.hot;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.bean.Friend;
import com.example.wu.wanandroiddemo.bean.HotKey;
import com.example.wu.wanandroiddemo.net.ApiService;
import com.example.wu.wanandroiddemo.net.RetrofitManager;
import com.example.wu.wanandroiddemo.util.LogUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wu on 2018/3/24.
 */

public class HotPresenter implements BaseContract.BasePresenter {
    private static final String TAG = "HotPresenter";

    private HotView mView;

    HotPresenter(HotView view) {
        mView = view;
    }

    /**
     * 获取热门标签
     */
    void getHotTag() {
        RetrofitManager.create(ApiService.class)
                .getHotKeys()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse<List<HotKey>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DataResponse<List<HotKey>> response) {
                        LogUtil.e(TAG, "e = " + response.toString());
                        if (response.getErrorCode() == 0) {
                            mView.loadHotTag(response.getData());
                        } else {
                            mView.loadFail((String) response.getErrorMsg());
                        }

                    }
                });
    }


    void getHotWebSite() {
        LogUtil.e(TAG, "getTag");
        RetrofitManager.create(ApiService.class)
                .getHotFriends()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse<List<Friend>>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "e = " + e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse<List<Friend>> response) {
                        LogUtil.e(TAG, "e = " + response.toString());
                        if (response.getErrorCode() == 0) {
                            mView.loadHotWebSite(response.getData());
                        } else {
                            mView.loadFail((String) response.getErrorMsg());
                        }
                    }
                });
    }


}
