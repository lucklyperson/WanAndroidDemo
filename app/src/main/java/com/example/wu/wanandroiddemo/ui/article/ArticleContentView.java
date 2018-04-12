package com.example.wu.wanandroiddemo.ui.article;

import com.example.wu.wanandroiddemo.base.BaseContract;

/**
 * Created by wu on 2018/3/21.
 */

public interface ArticleContentView extends BaseContract.BaseView {
    @Override
    void showLoading();

    @Override
    void hideLoading();

    @Override
    void showNoNet();

    @Override
    void onRetry();

    @Override
    void refreshCompleted();
}
