package com.example.wu.wanandroiddemo.ui.articlegroup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.wu.wanandroiddemo.bean.SystemKnowledge;
import com.example.wu.wanandroiddemo.ui.article.ArticleListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 2018/3/26.
 */

public class ArticleGroupFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<SystemKnowledge.ChildrenBean> mChildrenData;
    private List<ArticleListFragment> mFragments;

    public ArticleGroupFragmentPagerAdapter(FragmentManager fm, List<SystemKnowledge.ChildrenBean> childrenData) {
        super(fm);
        mChildrenData = childrenData;
        mFragments = new ArrayList<>();
        if (mChildrenData == null) return;
        for (SystemKnowledge.ChildrenBean childrenBean : mChildrenData) {
            ArticleListFragment fragment = new ArticleListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", childrenBean.getId());
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mChildrenData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChildrenData.get(position).getName();
    }
}
