package com.example.wu.wanandroiddemo.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wu.wanandroiddemo.constant.Constant;

/**
 * Created by wu on 2018/3/31.
 */

public class DBSqliteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "wan.db";
    private static final int VERSION_CODE = 1;

    public DBSqliteHelper(Context context) {
        super(context, DB_NAME, null, VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         *创建搜索记录表（自增长id  var_char name）
         */
        String sqlKeySearch = "create table " + Constant.DB_TABLE_KEY_HISTORY + "(Id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(8000) UNIQUE)";
        db.execSQL(sqlKeySearch);


        /**
         * 创建自动聊天记录表（自增长id ）
         */

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
