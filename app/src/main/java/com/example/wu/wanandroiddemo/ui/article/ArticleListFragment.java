package com.example.wu.wanandroiddemo.ui.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.animation.SlideInBottomAnimation;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.adapter.ArticleListAdapter;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.util.ToastUtil;
import com.example.wu.wanandroiddemo.widget.MyRecyclerView;
import com.example.wu.wanandroiddemo.widget.MySwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wu on 2018/3/26.
 */

public class ArticleListFragment extends Fragment implements ArticleListView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerView)
    MyRecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    MySwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;


    private int id;
    private int page;
    private ArticleListAdapter adapter;

    private ArticleListPresenter articleListPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setRefreshing(true);
        initRecyclerView();
        return view;
    }


    private void initRecyclerView() {
        articleListPresenter = new ArticleListPresenter(this);
        articleListPresenter.loadDataSuccess(page, id);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ArticleListAdapter();
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                articleListPresenter.loadDataSuccess(page, id);
            }
        });
        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Article.DataBean dataBean = (Article.DataBean) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), ArticleContentActivity.class);
                intent.putExtra("article", dataBean);
                startActivity(intent);

            }
        });

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
    public void refreshCompleted() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadDataSuccess(Article article) {
        if (page == 0) {
            adapter.setNewData(article.getDatas());
        } else {
            adapter.addData(article.getDatas());
        }

        if (article.getDatas().size() == 0) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreEnd();
        }

    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.toast(getActivity(), msg);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        articleListPresenter.loadDataSuccess(page, id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
