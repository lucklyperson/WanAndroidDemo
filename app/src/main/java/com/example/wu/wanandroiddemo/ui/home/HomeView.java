package com.example.wu.wanandroiddemo.ui.home;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.bean.BannerEntity;

import java.util.List;

/**
 * Created by wu on 2018/3/21.
 */

public interface HomeView extends BaseContract.BaseView {

    //获取初始数据
    void loadDataSuccess(List<Article.DataBean> list);


    //获取banner数据
    void loadBannerDataSuccess(List<BannerEntity> list);


    //获取数据失败
    void loadDataFail(String msg);

    //收藏成功
    void collectSuccess(int position);

    //收藏失败
    void collectFail(String msg);

    //取消收藏成功
    void removeCollectSuccess(int position);

    //取消收藏失败
    void removeCollectFail(String msg);

}
