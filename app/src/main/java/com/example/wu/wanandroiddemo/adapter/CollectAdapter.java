package com.example.wu.wanandroiddemo.adapter;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.bean.Article;
import com.example.wu.wanandroiddemo.util.LogUtil;

import java.util.Random;


/**
 * Created by wu on 2018/3/29.
 */

public class CollectAdapter extends BaseQuickAdapter<Article.DataBean, BaseViewHolder> {
    private int[] images = {R.drawable.splash0, R.drawable.splash1, R.drawable.splash2, R.drawable.splash3, R.drawable.splash4,
            R.drawable.splash6, R.drawable.splash7, R.drawable.splash8,
            R.drawable.splash9, R.drawable.splash10, R.drawable.splash11,
            R.drawable.splash12, R.drawable.splash13, R.drawable.splash14, R.drawable.splash15, R.drawable.splash16};

    public CollectAdapter() {
        super(R.layout.item_collect, null);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Article.DataBean item) {
        LogUtil.e(TAG, "convert = " + item.getTag());
        Random random = new Random();
        int i = random.nextInt(16);
        Glide.with(mContext).load(images[i]).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into((ImageView) helper.getView(R.id.civHeadPortrait));
        helper.setText(R.id.tvAuthor, item.getAuthor());
        helper.setText(R.id.tvTitle, Html.fromHtml(item.getTitle()));
        //tag == 0或者null:隐藏  tag = 1:未选中  tag=-1：选中
        if (item.getTag() == null || (int) item.getTag() == 0) {
            helper.setVisible(R.id.iv_edit, false);
        } else {
            helper.setVisible(R.id.iv_edit, true);
            if ((int) item.getTag() == -1) {
                helper.setImageResource(R.id.iv_edit, R.mipmap.right_circle);
            } else {
                helper.setImageResource(R.id.iv_edit, R.mipmap.right_circle_disabled);
            }
        }
        helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int) item.getTag() == 1) {
                    for (Article.DataBean bean : getData()) {
                        bean.setTag(1);
                    }
                    helper.setImageResource(R.id.iv_edit, R.mipmap.right_circle);
                    item.setTag(-1);
                    notifyDataSetChanged();
                } else {
                    helper.setImageResource(R.id.iv_edit, R.mipmap.right_circle_disabled);
                    item.setTag(1);
                    notifyDataSetChanged();
                }
            }
        });
    }
}
