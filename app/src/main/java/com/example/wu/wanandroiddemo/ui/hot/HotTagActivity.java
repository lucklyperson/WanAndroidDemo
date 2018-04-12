package com.example.wu.wanandroiddemo.ui.hot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.adapter.HotTagAdapter;
import com.example.wu.wanandroiddemo.base.BaseActivity;
import com.example.wu.wanandroiddemo.bean.Friend;
import com.example.wu.wanandroiddemo.bean.HotKey;
import com.example.wu.wanandroiddemo.ui.article.ArticleContentActivity;
import com.example.wu.wanandroiddemo.ui.search.SearchActivity;
import com.example.wu.wanandroiddemo.util.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;

public class HotTagActivity extends BaseActivity implements HotView {

    private static final String TAG = "HotTagActivity";

    @BindView(R.id.hot_website)
    TagFlowLayout hotWebsite;
    @BindView(R.id.hot_tag)
    TagFlowLayout hotTag;

    private HotPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_tag;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        presenter = new HotPresenter(this);
        presenter.getHotTag();
        presenter.getHotWebSite();
    }


    @Override
    protected void initInjector() {

    }

    @Override
    protected String setToolbar() {
        return "热门";
    }

    @Override
    protected boolean backIconIsVisible() {
        return true;

    }

    @Override
    public void loadHotTag(final List<HotKey> list) {
        hotTag.setAdapter(new HotTagAdapter(list, this));
        hotTag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(HotTagActivity.this, SearchActivity.class);
                intent.putExtra("keyWord", list.get(position).getName());
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void loadHotWebSite(final List<Friend> list) {
        hotWebsite.setAdapter(new HotTagAdapter(list, this));
        hotWebsite.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(HotTagActivity.this, ArticleContentActivity.class);
                intent.putExtra("hotWebSite", list.get(position));
                startActivity(intent);
                return false;
            }
        });
    }


    @Override
    public void loadFail(String msg) {
        ToastUtil.toast(this, msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
}
