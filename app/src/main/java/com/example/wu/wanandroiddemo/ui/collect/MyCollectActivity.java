package com.example.wu.wanandroiddemo.ui.collect;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.adapter.CollectAdapter;
import com.example.wu.wanandroiddemo.base.BaseActivity;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.event.CollectEvent;
import com.example.wu.wanandroiddemo.ui.article.ArticleContentActivity;
import com.example.wu.wanandroiddemo.util.RxBus;
import com.example.wu.wanandroiddemo.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class MyCollectActivity extends BaseActivity implements MyCollectView {

    private static final String TAG = "MyCollectActivity";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MenuItem tvEdit;

    private MyCollectPresenter mPresenter;
    private int page;
    private CollectAdapter adapter;
    private int deletePosition;        //删除的position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CollectAdapter();
        adapter.openLoadAnimation(new SlideInLeftAnimation());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyCollectActivity.this, ArticleContentActivity.class);
                intent.putExtra("article", (Article.DataBean) adapter.getData().get(position));
                startActivity(intent);
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                mPresenter.loadData(page);
            }
        }, recyclerView);

    }

    @Override
    protected void initData() {
        mPresenter = new MyCollectPresenter(this);
        mPresenter.loadData(page);
    }

    @Override
    protected String setToolbar() {
        return "我的收藏";
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

    }

    @Override
    public void loadData(List<Article.DataBean> response) {
        if (response.size() == 0) {
            adapter.loadMoreEnd();
            return;
        } else {
            adapter.loadMoreComplete();
        }
        if (page == 0) {
            adapter.setNewData(response);
        } else {
            adapter.addData(response);
        }
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.toast(this, msg);
    }

    @Override
    public void unCollectSuccess() {
        ToastUtil.toast(this, "取消成功");
        adapter.getData().remove(deletePosition);
        adapter.notifyItemRemoved(deletePosition);
        for (Article.DataBean dataBean : adapter.getData()) {
            dataBean.setTag(0);
        }
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        tvEdit.setTitle("编辑");
        RxBus.getInstance().post(new CollectEvent());
    }

    @Override
    public void unCollectFail(String msg) {

    }

    @Override
    protected boolean backIconIsVisible() {
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collect_menu, menu);
        tvEdit = menu.findItem(R.id.menu_edit);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                if (TextUtils.equals("编辑", item.getTitle())) {
                    tvEdit.setTitle("删除");
                    for (Article.DataBean dataBean : adapter.getData()) {
                        dataBean.setTag(1);
                    }
                    adapter.notifyDataSetChanged();
                } else if (TextUtils.equals("删除", item.getTitle())) {
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        if ((int) adapter.getData().get(i).getTag() == -1) {
                            deletePosition = i;
                            if (!TextUtils.isEmpty(adapter.getData().get(i).getOrigin())) {
                                mPresenter.unCollect(adapter.getData().get(i).getId(), adapter.getData().get(i).getOrigin());
                            } else {
                                mPresenter.unCollect(adapter.getData().get(i).getId(), "-1");
                            }
                        }
                    }
                }

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
