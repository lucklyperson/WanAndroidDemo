package com.example.wu.wanandroiddemo.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.bean.SystemKnowledge;

import java.util.List;

/**
 * Created by wu on 2018/3/26.
 */

public class KnowledgeSystemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private static final String TAG = KnowledgeSystemAdapter.class.getSimpleName();
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    private OnItemClickListener listener;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public KnowledgeSystemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_knowledge_system_lev1);
        addItemType(TYPE_LEVEL_1, R.layout.item_knowledge_system_lev2);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final SystemKnowledge systemKnowledge = (SystemKnowledge) item;
                helper.setText(R.id.title, systemKnowledge.getName());
                helper.setImageResource(R.id.iv, systemKnowledge.isExpanded() ? R.drawable.ic_arrow_downward_black_24dp : R.drawable.ic_arrow_forward_black_24dp);
                helper.getView(R.id.iv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = helper.getAdapterPosition();
                        if (systemKnowledge.isExpanded()) {
                            collapse(position);
                        } else {
                            expand(position);
                        }
                    }
                });
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(systemKnowledge);
                    }
                });
                break;
            case TYPE_LEVEL_1:
                SystemKnowledge.ChildrenBean systemKnowledge2 = (SystemKnowledge.ChildrenBean) item;
                helper.setText(R.id.title, systemKnowledge2.getName());
                break;
            default:
                break;
        }
    }


    public interface OnItemClickListener {
        void onItemClick(SystemKnowledge systemKnowledge);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
