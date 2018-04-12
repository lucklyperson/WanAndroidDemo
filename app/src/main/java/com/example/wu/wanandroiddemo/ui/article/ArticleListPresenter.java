package com.example.wu.wanandroiddemo.ui.article;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.net.ApiService;
import com.example.wu.wanandroiddemo.net.RetrofitManager;
import com.example.wu.wanandroiddemo.util.LogUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wu on 2018/3/27.
 */

public class ArticleListPresenter implements BaseContract.BasePresenter {

    private static final String TAG = "ArticleListPresenter";

    private ArticleListView mView;

    public ArticleListPresenter(BaseContract.BaseView view) {
        mView = (ArticleListView) view;
    }

    public void loadDataSuccess(int page, int id) {
        RetrofitManager.create(ApiService.class)
                .getKnowledgeSystemArticles(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mView.refreshCompleted();
                    }


                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "e = " + e.getMessage());
                        mView.refreshCompleted();
                    }

                    @Override
                    public void onNext(DataResponse<Article> response) {
                        LogUtil.e(TAG, "result = " + response.toString());
                        if (response.getErrorCode() == 0) {
                            mView.loadDataSuccess(response.getData());
                        } else {
                            mView.loadFail((String) response.getErrorMsg());
                        }
                    }
                });
    }


}
