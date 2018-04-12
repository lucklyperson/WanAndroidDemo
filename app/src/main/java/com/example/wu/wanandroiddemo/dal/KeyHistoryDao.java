package com.example.wu.wanandroiddemo.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wu.wanandroiddemo.constant.Constant;
import com.example.wu.wanandroiddemo.helper.DBSqliteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 2018/3/31.
 */

public class KeyHistoryDao {


    /**
     * 插入关键字的历史记录
     *
     * @param context context
     * @param key     key
     */
    public void insertKeyHistory(Context context, String key) {
        SQLiteDatabase db = new DBSqliteHelper(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", key);
        db.insert(Constant.DB_TABLE_KEY_HISTORY, null, cv);
        db.close();
    }

    /**
     * 查询关键字的历史记录
     *
     * @param context context
     * @return list
     */
    public List<String> queryKeyHistoryList(Context context) {
        SQLiteDatabase db = new DBSqliteHelper(context).getWritableDatabase();
        List<String> list = new ArrayList<>();
        Cursor cursor = db.query(Constant.DB_TABLE_KEY_HISTORY, new String[]{"name"}, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            list.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }


}
