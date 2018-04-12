package com.example.wu.wanandroiddemo.bll;

import android.content.Context;

import com.example.wu.wanandroiddemo.dal.KeyHistoryDao;

import java.util.List;

/**
 * Created by wu on 2018/3/31.
 */

public class KeyHistoryBiz {
    private KeyHistoryDao dao;

    public KeyHistoryBiz() {
        dao = new KeyHistoryDao();
    }


    /**
     * 插入关键字的历史记录
     *
     * @param context context
     * @param key     key
     */
    public void insertKeyHistory(Context context, String key) {
        dao.insertKeyHistory(context, key);
    }

    /**
     * 查询关键字的历史记录
     *
     * @param context context
     * @return list
     */
    public List<String> queryKeyHistoryList(Context context) {
        return dao.queryKeyHistoryList(context);
    }
}
