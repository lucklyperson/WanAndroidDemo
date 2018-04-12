package com.example.wu.wanandroiddemo.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.adapter.HotTagAdapter;
import com.example.wu.wanandroiddemo.adapter.SearchAdapter;
import com.example.wu.wanandroiddemo.base.BaseActivity;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.bll.KeyHistoryBiz;
import com.example.wu.wanandroiddemo.ui.article.ArticleContentActivity;
import com.example.wu.wanandroiddemo.util.LogUtil;
import com.example.wu.wanandroiddemo.util.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, SearchBaseView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SearchActivity";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private SearchView mSearchView;

    private int page;
    private SearchAdapter adapter;
    private SearchPresenter presenter;
    private List<Article.DataBean> articles;
    private String keyWord;

    private List<String> tags = new ArrayList<>();
    private HotTagAdapter<String> tagAdapter;
    private KeyHistoryBiz biz;
    private TagFlowLayout flowLayout;            //流式布局


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new SearchAdapter();
        adapter.openLoadAnimation(new AlphaInAnimation());
        recyclerView.setAdapter(adapter);

        //添加历史记录
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_search_head, null);
        flowLayout = headView.findViewById(R.id.frameLayout);
        biz = new KeyHistoryBiz();
        tags = biz.queryKeyHistoryList(SearchActivity.this);
        tagAdapter = new HotTagAdapter<>(tags, SearchActivity.this);
        flowLayout.setAdapter(tagAdapter);
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mSearchView.setQuery(tags.get(position), false);
                page = 0;
                presenter.search(page, tags.get(position));
                return false;
            }
        });

        adapter.addHeaderView(headView);

        //设置空布局
        if (tags.size() == 0) {
            View emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent(), false);
            LinearLayout llEmpty = emptyView.findViewById(R.id.ll_empty);
            llEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    page = 0;
                    presenter.search(page, keyWord);
                }
            });
            adapter.setEmptyView(emptyView);
        }

    }

    @Override
    protected void initData() {
        articles = new ArrayList<>();
        presenter = new SearchPresenter(this);
        if (getIntent() == null) return;
        if (getIntent().hasExtra("keyWord")) {
            swipeRefreshLayout.setRefreshing(true);
            keyWord = getIntent().getStringExtra("keyWord");
            presenter.search(page, keyWord);
            biz.insertKeyHistory(this, keyWord);
            tags.clear();
            tags.addAll(biz.queryKeyHistoryList(this));
            tagAdapter.notifyDataChanged();
        }

    }

    private void setListener() {
        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    protected String setToolbar() {
        return "搜索";
    }

    @Override
    protected boolean backIconIsVisible() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        mSearchView.setQuery(keyWord, false);
        mSearchView.setMaxWidth(1920);
        mSearchView.setIconified(false);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                page = 0;
                articles.clear();
                presenter.search(page, query);
                biz.insertKeyHistory(SearchActivity.this, query);
                tags.clear();
                tags.addAll(biz.queryKeyHistoryList(SearchActivity.this));
                tagAdapter.notifyDataChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                keyWord = newText;
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onLoadMoreRequested() {
        page++;
        keyWord = mSearchView.getQuery().toString();
        presenter.search(page, keyWord);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, ArticleContentActivity.class);
        intent.putExtra("article", articles.get(position));
        startActivity(intent);
    }

    @Override
    public void loadDataSuccess(List<Article.DataBean> list) {
        if (page == 0) {
            adapter.setNewData(list);
        } else {
            adapter.addData(list);
        }

        if (list.size() == 0) {
            adapter.loadMoreEnd();
            ToastUtil.toast(this, "亲,换个关键词试试");
        } else {
            adapter.loadMoreComplete();
        }

        articles.addAll(list);
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.toast(this, msg);
    }

    @Override
    public void onRefresh() {
        articles.clear();
        page = 0;
        keyWord = mSearchView.getQuery().toString();
        presenter.search(page, keyWord);
    }

    @Override
    public void refreshCompleted() {
        super.refreshCompleted();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
