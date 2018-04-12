package com.example.wu.wanandroiddemo.ui.aboutus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.base.BaseActivity;
import com.example.wu.wanandroiddemo.util.ToastUtil;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.tv_github)
    TextView tvGithub;
    @BindView(R.id.tv_score)
    TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        tvIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.wanandroid.com/blog/show/2");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        tvGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/lucklyperson");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        tvScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("market://details?id=" + "com.shaodianbao");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(AboutUsActivity.this, "未检测到应用市场");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String setToolbar() {
        return "关于我们";
    }

    @Override
    protected boolean backIconIsVisible() {
        return true;

    }
}
