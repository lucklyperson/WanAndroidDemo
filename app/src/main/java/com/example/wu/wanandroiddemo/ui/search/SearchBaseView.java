package com.example.wu.wanandroiddemo.ui.search;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.Article;

import java.util.List;

/**
 * Created by wu on 2018/3/24.
 */

public interface SearchBaseView extends BaseContract.BaseView {


    void loadDataSuccess(List<Article.DataBean> list);

    void loadFail(String msg);


}
