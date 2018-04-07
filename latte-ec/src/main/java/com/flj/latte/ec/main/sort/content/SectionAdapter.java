package com.flj.latte.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.diabin.latte.ec.R;
import com.flj.latte.ec.detail.GoodsDetailDelegate;
import com.flj.latte.ec.main.sort.SortMessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by 傅令杰
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();
    ContentDelegate contentDelegate;
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data,ContentDelegate contentDelegate) {
        super(layoutResId, sectionHeadResId, data);
        this.contentDelegate = contentDelegate;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        //item.t返回SectionBean类型
        final String thumb = item.t.getGoodsThumb();
        final String name = item.t.getGoodsName();
        final int goodsId = item.t.getGoodsId();
        final SectionContentItemEntity entity = item.t;
        helper.setText(R.id.tv, name);
        final AppCompatImageView goodsImageView = helper.getView(R.id.iv);
        Glide.with(mContext)
                .load(thumb)
                .into(goodsImageView);
        //点击
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new SortMessageEvent(goodsId));
            }
        });
    }
}
