package com.example.wu.wanandroiddemo.ui.set;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.TextView;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.base.BaseActivity;
import com.example.wu.wanandroiddemo.util.ToastUtil;

import butterknife.BindView;

public class SetActivity extends BaseActivity {
    @BindView(R.id.tv_notify)
    TextView tvNotify;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_cache_length)
    TextView tvCacheLength;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_update)
    TextView tvUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        tvNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        });
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toast(SetActivity.this, "您当前已是最新版本");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String setToolbar() {
        return "设置";
    }

    @Override
    protected boolean backIconIsVisible() {
        return true;
    }

    /**
     * 消息通知是否开启
     *
     * @return boolean
     */
    private boolean isNotifyOpened() {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        return managerCompat.areNotificationsEnabled();
    }

    /**
     * 设置switch按钮的状态
     *
     * @param isOpened 消息通知是否开启
     */
    private void setSwitchStatus(boolean isOpened) {
        Drawable drawableRight;
        if (isOpened) {
            drawableRight = getResources().getDrawable(R.mipmap.switch_on);
        } else {
            drawableRight = getResources().getDrawable(R.mipmap.switch_off);
        }
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        tvNotify.setCompoundDrawables(null, null, drawableRight, null);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setSwitchStatus(isNotifyOpened());
    }
}
