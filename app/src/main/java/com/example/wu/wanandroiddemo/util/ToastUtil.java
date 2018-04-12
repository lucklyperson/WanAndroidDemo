package com.example.wu.wanandroiddemo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wu on 2018/3/22.
 */

public class ToastUtil {

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
