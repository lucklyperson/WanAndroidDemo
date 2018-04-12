package com.example.wu.wanandroiddemo.ui.collect;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.Article;

import java.util.List;

/**
 * Created by wu on 2018/3/29.
 */

public interface MyCollectView extends BaseContract.BaseView {


    void loadData(List<Article.DataBean> response);

    void loadFail(String msg);

    void unCollectSuccess();

    void unCollectFail(String msg);


}
