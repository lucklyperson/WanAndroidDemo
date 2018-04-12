package com.example.wu.wanandroiddemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wu.wanandroiddemo.bean.DataResponse;
import com.example.wu.wanandroiddemo.bean.User;
import com.example.wu.wanandroiddemo.constant.Constant;
import com.example.wu.wanandroiddemo.event.LoginEvent;
import com.example.wu.wanandroiddemo.ui.aboutus.AboutUsActivity;
import com.example.wu.wanandroiddemo.ui.collect.MyCollectActivity;
import com.example.wu.wanandroiddemo.ui.home.HomeFragment;
import com.example.wu.wanandroiddemo.ui.hot.HotTagActivity;
import com.example.wu.wanandroiddemo.ui.login.LoginActivity;
import com.example.wu.wanandroiddemo.ui.register.RegisterActivity;
import com.example.wu.wanandroiddemo.ui.search.SearchActivity;
import com.example.wu.wanandroiddemo.ui.knowledgesystem.SystemFragment;
import com.example.wu.wanandroiddemo.ui.set.SetActivity;
import com.example.wu.wanandroiddemo.util.LogUtil;
import com.example.wu.wanandroiddemo.util.RxBus;
import com.example.wu.wanandroiddemo.util.SharedPreferencesUtil;
import com.example.wu.wanandroiddemo.util.ToastUtil;


import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private HomeFragment homeFragment;
    private SystemFragment systemFragment;
    private Unbinder unbinder;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        if (SharedPreferencesUtil.getInstance().getUser(Constant.SP_USER).getId() != -1) {
            User user = SharedPreferencesUtil.getInstance().getUser(Constant.SP_USER);
            String username = user.getUsername();
            String password = user.getPassword();

            //自动登录（从偏设置中获取用户信息）
            LogUtil.login(username, password).subscribe(new Consumer<DataResponse<User>>() {
                @Override
                public void accept(DataResponse<User> response) throws Exception {
                    if (response.getErrorCode() == 0) {
                        ToastUtil.toast(MainActivity.this, "自动登录成功");
                    }
                }
            });

        }

        initView();
        initListener();
        initFragment();


    }

    private void initView() {
        toolbar.setTitle("玩Android");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //将侧边栏顶部延伸至status bar
            drawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
            drawerLayout.setClipToPadding(false);
        }
    }

    private void initListener() {
        //底部导航栏的点击事件
        navigation.setOnNavigationItemSelectedListener(this);
        //左侧导航栏的点击事件
        navView.setNavigationItemSelectedListener(this);
        View navHeadView = navView.getHeaderView(0);
        final TextView tvName = navHeadView.findViewById(R.id.nav_header_tv_name);
        CircleImageView ivHead = navHeadView.findViewById(R.id.nav_head_iv_avatar);
        final User user = SharedPreferencesUtil.getInstance().getUser(Constant.SP_USER);
        if (user.getId() == -1) {
            tvName.setText("未登录");
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            tvName.setText(user.getUsername());
        }

        RxBus.getInstance().toFlowable(LoginEvent.class)
                .subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(LoginEvent loginEvent) throws Exception {
                        ToastUtil.toast(MainActivity.this, "登录成功");
                        tvName.setText(user.getUsername());
                    }
                });
    }

    private void initFragment() {
        if (homeFragment == null) homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, homeFragment);
        transaction.commit();
    }

    private void showFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position == 0) {
            if (homeFragment != null) {
                transaction.show(homeFragment);
            } else {
                homeFragment = new HomeFragment();
                transaction.add(R.id.container, homeFragment);
            }
            if (systemFragment != null) transaction.hide(systemFragment);
        } else if (position == 1) {
            if (homeFragment != null) transaction.hide(homeFragment);
            if (systemFragment == null) {
                systemFragment = new SystemFragment();
                transaction.add(R.id.container, systemFragment);
            } else {
                transaction.show(systemFragment);
            }
        }
        transaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                toolbar.setTitle("玩Android");
                showFragment(0);
                break;
            case R.id.navigation_knowledgesystem:
                toolbar.setTitle("知识体系");
                showFragment(1);
                break;
            case R.id.nav_collect:
                if (SharedPreferencesUtil.getInstance().getUser(Constant.SP_USER).getId() == -1) {
                    ToastUtil.toast(MainActivity.this, "请先登录");
                } else {
                    Intent intent = new Intent(MainActivity.this, MyCollectActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.nav_message:
                ToastUtil.toast(MainActivity.this, "暂无消息");
                break;
            case R.id.nav_about:
                Intent intentAbout = new Intent(this, AboutUsActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.nav_set:
                Intent intentSet = new Intent(this, SetActivity.class);
                startActivity(intentSet);
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuHot:
                Intent intent = new Intent(MainActivity.this, HotTagActivity.class);
                startActivity(intent);
                break;
            case R.id.menuSearch:
                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentSearch);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navView)) {
            drawerLayout.closeDrawer(navView);
        } else {
            super.onBackPressed();
        }
    }


}
