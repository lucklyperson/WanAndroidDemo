package com.example.wu.wanandroiddemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.bean.Friend;
import com.example.wu.wanandroiddemo.bean.HotKey;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by wu on 2018/3/24.
 */

public class HotTagAdapter<T> extends TagAdapter<T> {
    private Context mContext;
    private LayoutInflater mInflate;

    public HotTagAdapter(List<T> list, Context context) {
        super(list);

        mContext = context;
        mInflate = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(FlowLayout parent, int position, T t) {
        View view = mInflate.inflate(R.layout.item_tag, parent, false);
        final TextView tv = view.findViewById(R.id.tv_tag);
        if (t instanceof Friend) {
            tv.setText(((Friend) t).getName());
        } else if (t instanceof HotKey) {
            tv.setText(((HotKey) t).getName());
        } else if (t instanceof String) {
            tv.setText((String) t);
        }
        return view;
    }


}
