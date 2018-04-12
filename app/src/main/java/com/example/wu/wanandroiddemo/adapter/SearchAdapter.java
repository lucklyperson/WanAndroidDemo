package com.example.wu.wanandroiddemo.adapter;

import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.bean.Article;

import java.util.Random;

import javax.inject.Inject;

/**
 * Created by wu on 2018/3/21.
 */

public class SearchAdapter extends BaseQuickAdapter<Article.DataBean, BaseViewHolder> {
    private int[] images = {R.drawable.splash0, R.drawable.splash1, R.drawable.splash2, R.drawable.splash3, R.drawable.splash4,
            R.drawable.splash6, R.drawable.splash7, R.drawable.splash8,
            R.drawable.splash9, R.drawable.splash10, R.drawable.splash11,
            R.drawable.splash12, R.drawable.splash13, R.drawable.splash14, R.drawable.splash15, R.drawable.splash16};

    @Inject
    public SearchAdapter() {
        super(R.layout.item_search, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article.DataBean item) {
        Random random = new Random();
        int i = random.nextInt(16);
        Glide.with(mContext).load(images[i]).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into((ImageView) helper.getView(R.id.civHeadPortrait));
        helper.setText(R.id.tvAuthor, item.getAuthor());
        helper.setText(R.id.tvNiceDate, item.getNiceDate());
        helper.setText(R.id.tvTitle, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tvChapterName, item.getChapterName());
    }


}
