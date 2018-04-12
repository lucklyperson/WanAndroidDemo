package com.example.wu.wanandroiddemo.ui.article;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.base.BaseActivity;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.bean.BannerEntity;
import com.example.wu.wanandroiddemo.bean.Friend;
import com.example.wu.wanandroiddemo.bean.HotKey;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.ChromeClientCallbackManager;

import butterknife.BindView;

public class ArticleContentActivity extends BaseActivity<ArticleContentPresenter> implements ArticleContentView {

    private static final String TAG = "ArticleContentActivity";

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    private AgentWeb agentWeb;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_content;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if (getIntent() == null) return;
        if (getIntent().hasExtra("banner")) {
            BannerEntity bannerEntity = (BannerEntity) getIntent().getSerializableExtra("banner");
            url = bannerEntity.getUrl();
        } else if (getIntent().hasExtra("article")) {
            Article.DataBean article = (Article.DataBean) getIntent().getSerializableExtra("article");
            url = article.getLink();
        } else if (getIntent().hasExtra("hotTag")) {
            HotKey hotKey = (HotKey) getIntent().getSerializableExtra("hotTag");
            url = hotKey.getLink();
        } else if (getIntent().hasExtra("hotWebSite")) {
            Friend friend = (Friend) getIntent().getSerializableExtra("hotWebSite");
            url = friend.getLink();
        }

        loadUrl(url);
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected String setToolbar() {
        return "";
    }

    @Override
    protected boolean backIconIsVisible() {
        return true;
    }

    /**
     * 使用AgentWeb加载url
     *
     * @param url url
     */
    private void loadUrl(String url) {
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(frameLayout, new ViewGroup.LayoutParams(-1, -1))  //父布局
                .useDefaultIndicator()  //使用默认进度条
                .defaultProgressBarColor()  //使用默认进度条
                .setReceivedTitleCallback(new TitleCallback())
                .createAgentWeb()
                .ready()
                .go(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_share:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.putExtra(Intent.EXTRA_TEXT, url);
                intentShare.setType("text/plain");
                startActivity(Intent.createChooser(intentShare, "分享"));
                break;
            case R.id.menu_browser:
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class TitleCallback implements ChromeClientCallbackManager.ReceivedTitleCallback {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        agentWeb.getWebLifeCycle().onDestroy();
    }

}

