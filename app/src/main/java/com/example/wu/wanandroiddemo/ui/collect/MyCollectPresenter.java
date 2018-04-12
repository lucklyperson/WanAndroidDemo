package com.example.wu.wanandroiddemo.ui.collect;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.net.ApiService;
import com.example.wu.wanandroiddemo.net.RetrofitManager;
import com.example.wu.wanandroiddemo.util.LogUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wu on 2018/3/29.
 */

public class MyCollectPresenter implements BaseContract.BasePresenter {
    private static final String TAG = "MyCollectPresenter";

    private MyCollectView mView;


    MyCollectPresenter(BaseContract.BaseView view) {
        mView = (MyCollectView) view;
    }

    void loadData(int page) {
        RetrofitManager.create(ApiService.class)
                .getCollectArticles(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse<Article> response) {
                        LogUtil.e(TAG, "result = " + response.toString());
                        if (response.getErrorCode() == 0) {
                            mView.loadData(response.getData().getDatas());
                        } else {
                            mView.loadFail((String) response.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void unCollect(int id, String originId) {
        RetrofitManager.create(ApiService.class)
                .removeCollectArticle(id, originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse response) throws Exception {
                        if (response.getErrorCode() == 0) {
                            mView.unCollectSuccess();
                        } else {
                            mView.unCollectFail((String) response.getErrorMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        LogUtil.e(TAG, "error = " + e.getMessage());
                    }
                });
    }


}
