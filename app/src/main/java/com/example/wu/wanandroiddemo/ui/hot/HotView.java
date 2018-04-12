package com.example.wu.wanandroiddemo.ui.hot;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.Friend;
import com.example.wu.wanandroiddemo.bean.HotKey;

import java.security.Key;
import java.util.List;

/**
 * Created by wu on 2018/3/24.
 */

public interface HotView extends BaseContract.BaseView {

    //获取热门标签
    void loadHotTag(List<HotKey> list);

    //获取常用网站
    void loadHotWebSite(List<Friend> list);


    void loadFail(String msg);
}
