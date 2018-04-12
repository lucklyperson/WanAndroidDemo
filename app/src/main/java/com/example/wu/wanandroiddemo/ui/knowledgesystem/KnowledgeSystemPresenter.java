package com.example.wu.wanandroiddemo.ui.knowledgesystem;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.bean.SystemKnowledge;
import com.example.wu.wanandroiddemo.net.ApiService;
import com.example.wu.wanandroiddemo.net.RetrofitManager;
import com.example.wu.wanandroiddemo.util.LogUtil;


import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wu on 2018/3/26.
 */

public class KnowledgeSystemPresenter implements BaseContract.BasePresenter {

    private static final String TAG = "KnowledgeSystemPresenter";

    private KnowledgeSystemView mView;

    KnowledgeSystemPresenter(KnowledgeSystemView view) {
        mView = view;
    }

    void getKnowledgeSystem() {
        RetrofitManager.create(ApiService.class)
                .getKnowledgeSystems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse<List<SystemKnowledge>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse<List<SystemKnowledge>> response) {
                        if (response.getErrorCode() == 0) {
                            mView.loadKnowledgeSystem(response.getData());
                        } else {
                            mView.loadFail((String) response.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "e = " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
