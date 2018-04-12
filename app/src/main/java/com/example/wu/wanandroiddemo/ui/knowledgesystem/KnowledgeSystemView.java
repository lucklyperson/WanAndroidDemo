package com.example.wu.wanandroiddemo.ui.knowledgesystem;

import com.example.wu.wanandroiddemo.base.BaseContract;
import com.example.wu.wanandroiddemo.bean.SystemKnowledge;

import java.util.List;

/**
 * Created by wu on 2018/3/26.
 */

public interface KnowledgeSystemView extends BaseContract.BaseView {

    void loadKnowledgeSystem(List<SystemKnowledge> list);

    void loadFail(String msg);



}
