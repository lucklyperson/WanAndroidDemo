package com.example.wu.wanandroiddemo.ui.home;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.bean.BannerEntity;
import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.net.ApiService;
import com.example.wu.wanandroiddemo.net.RetrofitManager;
import com.example.wu.wanandroiddemo.util.LogUtil;


import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wu on 2018/3/21.
 */

public class HomePresenter implements BaseContract.BasePresenter<BaseContract.BaseView> {
    private static final String TAG = "HomePresenter";


    private HomeView mView;

    HomePresenter(BaseContract.BaseView view) {
        this.mView = (HomeView) view;
    }

    void loadBanners() {

        RetrofitManager.create(ApiService.class)
                .getHomeBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<DataResponse<List<BannerEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse<List<BannerEntity>> response) {
                        LogUtil.d(TAG, "banners = " + response.toString());
                        mView.loadBannerDataSuccess(response.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "e= " + e.getMessage());
                    }


                    @Override
                    public void onComplete() {

                    }
                });
    }

    void loadHomeData(final int page) {
        RetrofitManager.create(ApiService.class)
                .getHomeArticles(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse<Article> response) {
                        mView.loadDataSuccess(response.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadDataFail(e.getMessage());
                        mView.refreshCompleted();
                    }

                    @Override
                    public void onComplete() {
                        mView.refreshCompleted();
                    }
                });

    }

    /**
     * 收藏文章
     *
     * @param id 文章id
     */
    void collect(int id, final int position) {
        RetrofitManager.create(ApiService.class)
                .addCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse response) {
                        if (response.getErrorCode() == 0) {
                            mView.collectSuccess(position);
                        } else {
                            mView.collectFail((String) response.getErrorMsg());
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


    void unCollect(int id, final int position) {
        RetrofitManager.create(ApiService.class)
                .removeCollectArticle(id, "-1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse response) {
                        LogUtil.login(TAG, "response = " + response.toString());
                        if (response.getErrorCode() == 0) {
                            mView.removeCollectSuccess(position);
                        } else {
                            mView.removeCollectFail((String) response.getErrorMsg());
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
