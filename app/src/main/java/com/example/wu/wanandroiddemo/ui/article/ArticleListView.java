package com.example.wu.wanandroiddemo.ui.article;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.Article;


/**
 * Created by wu on 2018/3/27.
 */

public interface ArticleListView extends BaseContract.BaseView {


    void loadDataSuccess(Article article);

    void loadFail(String msg);


}
