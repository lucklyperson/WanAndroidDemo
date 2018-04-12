package com.example.wu.wanandroiddemo.base;

/**
 * Created by wu on 2018/3/8.
 */

public interface BaseContract {

    interface BasePresenter<T extends BaseContract.BaseView> {


    }

    interface BaseView {

        //显示进度中
        void showLoading();

        //隐藏进度
        void hideLoading();

        //显示当前网络不可用
        void showNoNet();

        //重试
        void onRetry();

        //refresh完成
        void refreshCompleted();

    }


}
