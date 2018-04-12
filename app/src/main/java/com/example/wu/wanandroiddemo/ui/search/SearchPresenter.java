package com.example.wu.wanandroiddemo.ui.search;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.net.ApiService;
import com.example.wu.wanandroiddemo.net.RetrofitManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wu on 2018/3/24.
 */

public class SearchPresenter implements BaseContract.BasePresenter {
    private SearchBaseView mView;

    SearchPresenter(SearchBaseView view) {
        mView = view;
    }


    void search(final int page, String key) {
        RetrofitManager.create(ApiService.class)
                .getSearchArticles(page, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse<Article>>() {
                    @Override
                    public void onComplete() {
                        mView.refreshCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.refreshCompleted();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse<Article> response) {
                        if (response.getErrorCode() == 0) {
                            mView.loadDataSuccess(response.getData().getDatas());
                        } else {
                            mView.loadFail((String) response.getErrorMsg());
                        }
                    }
                });

    }


}
