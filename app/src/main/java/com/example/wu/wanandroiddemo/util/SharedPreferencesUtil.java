package com.example.wu.wanandroiddemo.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.wu.wanandroiddemo.base.App;
import com.example.wu.wanandroiddemo.bean.User;
import com.example.wu.wanandroiddemo.constant.Constant;

/**
 * Created by wu on 2018/3/23.
 */

public class SharedPreferencesUtil {

    private volatile static SharedPreferencesUtil instance;

    private SharedPreferencesUtil() {

    }

    public static SharedPreferencesUtil getInstance() {
        if (instance == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 保存用户的信息
     *
     * @param fileName fileName
     * @param user     user
     */
    public void saveUser(String fileName, User user) {
        SharedPreferences sharedPreferences = App.instance.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(Constant.ID, user.getId());
        edit.putString(Constant.USER_NAME, user.getUsername());
        edit.putString(Constant.PASSWORD, user.getPassword());
        edit.apply();
    }

    /**
     * 获取用户信息
     *
     * @param fileName fileName
     * @return user
     */
    public User getUser(String fileName) {
        SharedPreferences sharedPreferences = App.instance.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt(Constant.ID, -1);
        String username = sharedPreferences.getString(Constant.USER_NAME, "");
        String password = sharedPreferences.getString(Constant.PASSWORD, "");
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    /**
     * App当前的是否开启夜间模式
     *
     * @return boolean 默认不开启
     */
    public boolean getNightMode() {
        SharedPreferences sharedPreferences = App.instance.getSharedPreferences(Constant.SP_NIGHT_MODE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constant.NIGHT_MODE, false);
    }


    public void setNightMode(Boolean isNightMode) {
        SharedPreferences sharedPreferences = App.instance.getSharedPreferences(Constant.SP_NIGHT_MODE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constant.NIGHT_MODE, isNightMode);
        editor.apply();
    }
}
