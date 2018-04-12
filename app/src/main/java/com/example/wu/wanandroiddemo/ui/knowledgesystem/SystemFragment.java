package com.example.wu.wanandroiddemo.ui.knowledgesystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.adapter.KnowledgeSystemAdapter;
import com.example.wu.wanandroiddemo.bean.SystemKnowledge;
import com.example.wu.wanandroiddemo.ui.articlegroup.ArticleGroupActivity;
import com.example.wu.wanandroiddemo.util.LogUtil;
import com.example.wu.wanandroiddemo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wu on 2018/3/26.
 */

public class SystemFragment extends Fragment implements KnowledgeSystemView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SystemFragment";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;

    private KnowledgeSystemPresenter presenter;
    private KnowledgeSystemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_knowledge, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        initData();
        return view;
    }


    private void initData() {
        LogUtil.e(TAG, "isVisible = " + getUserVisibleHint());
        swipeRefreshLayout.setRefreshing(true);
        if (getUserVisibleHint()) {
            presenter = new KnowledgeSystemPresenter(this);
            presenter.getKnowledgeSystem();
        }
    }


    private void initRecyclerView(List list) {

        List<MultiItemEntity> data = new ArrayList<>();         //适配器需要的数据

        for (int i = 0; i < list.size(); i++) {
            SystemKnowledge lv0 = (SystemKnowledge) list.get(i);                 //一级数据
            for (int j = 0; j < ((SystemKnowledge) list.get(i)).getChildren().size(); j++) {
                SystemKnowledge.ChildrenBean lv1 = lv0.getChildren().get(j);     //二级数据
                lv0.addSubItem(lv1);                                             //将二级数据和一级数据关联起来
            }
            data.add(lv0);                                                       //一个新的集合
        }

        adapter = new KnowledgeSystemAdapter(list);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        swipeRefreshLayout.setRefreshing(false);
        adapter.setOnItemClickListener(new KnowledgeSystemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SystemKnowledge systemKnowledge) {
                Intent intent = new Intent(getActivity(), ArticleGroupActivity.class);
                intent.putExtra("bundle", systemKnowledge);
                startActivity(intent);
            }
        });
    }

    @Override
    public void refreshCompleted() {
    }

    @Override
    public void loadKnowledgeSystem(final List<SystemKnowledge> list) {
        initRecyclerView(list);
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.toast(getActivity(), msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void onRetry() {

    }


    @Override
    public void onRefresh() {
        presenter.getKnowledgeSystem();
    }
}
