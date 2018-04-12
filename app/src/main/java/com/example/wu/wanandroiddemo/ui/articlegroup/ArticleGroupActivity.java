package com.example.wu.wanandroiddemo.ui.articlegroup;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.base.BaseActivity;
import com.example.wu.wanandroiddemo.bean.SystemKnowledge;
import com.example.wu.wanandroiddemo.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ArticleGroupActivity extends BaseActivity {

    private static final String TAG = "ArticleGroupActivity";
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private String title;
    private List<SystemKnowledge.ChildrenBean> list = new ArrayList<>();

    private ArticleGroupFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initData();
        super.onCreate(savedInstanceState);
        initView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_group;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        adapter = new ArticleGroupFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    protected void initData() {
        if (getIntent() == null) return;
        SystemKnowledge systemKnowledge = (SystemKnowledge) getIntent().getSerializableExtra("bundle");
        list.addAll(systemKnowledge.getChildren());
        title = systemKnowledge.getName();
        LogUtil.e(TAG, "title1 = " + title);
    }

    @Override
    protected String setToolbar() {
        LogUtil.e(TAG, "title2 = " + title);
        return title;
    }


    @Override
    protected boolean backIconIsVisible() {
        return true;

    }
}
