package com.example.wu.wanandroiddemo.ui.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wu.wanandroiddemo.MainActivity;
import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.adapter.HomeAdapter;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.bean.BannerEntity;
import com.example.wu.wanandroiddemo.constant.Constant;
import com.example.wu.wanandroiddemo.dialog.CommonDialog;
import com.example.wu.wanandroiddemo.event.CollectEvent;
import com.example.wu.wanandroiddemo.event.LoginEvent;
import com.example.wu.wanandroiddemo.ui.article.ArticleContentActivity;
import com.example.wu.wanandroiddemo.ui.login.LoginActivity;
import com.example.wu.wanandroiddemo.util.LogUtil;
import com.example.wu.wanandroiddemo.util.RxBus;
import com.example.wu.wanandroiddemo.util.SharedPreferencesUtil;
import com.example.wu.wanandroiddemo.util.ToastUtil;
import com.example.wu.wanandroiddemo.widget.MyRecyclerView;
import com.example.wu.wanandroiddemo.widget.MySwipeRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public class HomeFragment extends Fragment implements BaseQuickAdapter.OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, HomeView, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.rvHomeArticles)
    MyRecyclerView mRvHomeArticles;
    @BindView(R.id.swipeRefreshLayout)
    MySwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;


    private int page;
    private Unbinder unbinder;
    private HomeAdapter mHomeAdapter;
    private HomePresenter mHomePresenter;
    private Banner banner;
    private List<Article.DataBean> articles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        return view;

    }


    @SuppressLint("ClickableViewAccessibility")
    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void initData() {
        swipeRefreshLayout.setRefreshing(true);
        if (getUserVisibleHint()) {
            mHomePresenter = new HomePresenter(this);
            initRecyclerView();
            mHomePresenter.loadBanners();
        }

        //取消收藏后进行数据更新
        RxBus.getInstance().toFlowable(CollectEvent.class).subscribe(new Consumer<CollectEvent>() {
            @Override
            public void accept(CollectEvent collectEvent) throws Exception {
                page = 0;
                mHomePresenter.loadHomeData(page);
            }
        });
    }

    private void initRecyclerView() {
        //设置RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvHomeArticles.setLayoutManager(linearLayoutManager);
        mHomeAdapter = new HomeAdapter();
        mRvHomeArticles.setAdapter(mHomeAdapter);

        //设置BannerView
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_head, null);
        banner = headView.findViewById(R.id.banner);
        mHomeAdapter.addHeaderView(headView);

        //设置事件监听
        mHomeAdapter.setOnItemClickListener(this);
        mHomeAdapter.setOnItemChildClickListener(this);
        mHomeAdapter.setOnLoadMoreListener(this, mRvHomeArticles);
        swipeRefreshLayout.setOnRefreshListener(this);
        floatingActionButton.setOnClickListener(this);

        //请求数据
        mHomePresenter.loadHomeData(0);

    }

    @Override
    public void onRefresh() {
        page = 0;
        articles.clear();
        mHomePresenter.loadHomeData(page);
    }

    @Override
    public void loadDataSuccess(List<Article.DataBean> list) {
        LogUtil.e(TAG, "list = " + list.toString());
        if (page == 0) {
            articles.clear();
            articles.addAll(list);
            mHomeAdapter.setNewData(articles);
        } else {
            articles.addAll(list);
            mHomeAdapter.addData(list);
        }

        if (list.size() == 0) {
            mHomeAdapter.loadMoreEnd();
        } else {
            mHomeAdapter.loadMoreComplete();
        }

    }

    @Override
    public void loadBannerDataSuccess(final List<BannerEntity> list) {
        List<String> images = new ArrayList<>();
        for (BannerEntity bannerEntity : list) {
            images.add(bannerEntity.getImagePath());
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setDelayTime(3600);
        banner.setBannerAnimation(Transformer.Default);
        banner.isAutoPlay(true);
        banner.setImageLoader(new ImageLoaderInterface() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                Glide.with(context).load(path).into((ImageView) imageView);
            }

            @Override
            public View createImageView(Context context) {
                return null;
            }
        });
        banner.setImages(images);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), ArticleContentActivity.class);
                intent.putExtra("banner", list.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void loadDataFail(String msg) {
        mHomeAdapter.loadMoreFail();
        refreshCompleted();
    }

    @Override
    public void collectSuccess(int position) {
        ToastUtil.toast(getActivity(), "收藏成功");
        mHomeAdapter.getData().get(position).setCollect(true);
        mHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void collectFail(String msg) {
        ToastUtil.toast(getActivity(), msg);
    }

    @Override
    public void removeCollectSuccess(int position) {
        ToastUtil.toast(getActivity(), "取消收藏成功");
        mHomeAdapter.getData().get(position).setCollect(false);
        mHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeCollectFail(String msg) {
        ToastUtil.toast(getActivity(), msg);
    }

    @Override
    public void onLoadMoreRequested() {
        page += 1;
        mHomePresenter.loadHomeData(page);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        if (SharedPreferencesUtil.getInstance().getUser(Constant.SP_USER).getId() == -1) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }
        CommonDialog commonDialog = new CommonDialog();
        Bundle bundle = new Bundle();
        LogUtil.e(TAG, "article = " + articles.get(position).toString());
        if (!articles.get(position).isCollect()) {
            bundle.putString("title", "确认收藏?");
        } else {
            bundle.putString("title", "取消收藏?");
        }
        commonDialog.setArguments(bundle);
        commonDialog.show(getFragmentManager(), "1");
        commonDialog.setOnPositiveClickListener(new CommonDialog.OnPositiveClickListener() {
            @Override
            public void positive() {
                if (articles.get(position).isCollect()) {
                    mHomePresenter.unCollect(articles.get(position).getId(), position);
                } else {
                    mHomePresenter.collect(articles.get(position).getId(), position);
                }
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getActivity(), ArticleContentActivity.class);
        intent.putExtra("article", articles.get(position));
        startActivity(intent);
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
        page = 0;
        mHomePresenter.loadHomeData(page);
    }

    @Override
    public void refreshCompleted() {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mHomePresenter = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                if (!swipeRefreshLayout.isRefreshing()) {
                    mRvHomeArticles.smoothScrollToPosition(0);
                }
                break;
            default:
                break;
        }
    }
}
