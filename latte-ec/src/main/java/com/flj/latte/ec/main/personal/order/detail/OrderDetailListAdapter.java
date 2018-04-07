package com.flj.latte.ec.main.personal.order.detail;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.diabin.latte.ec.R;
import com.flj.latte.ec.main.personal.order.OrderItemFields;
import com.flj.latte.ec.main.personal.order.OrderListItemType;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by 11833 on 2018/3/27.
 */

public class OrderDetailListAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected OrderDetailListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_DETAIL_LIST, R.layout.item_order_detail);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case OrderListItemType.ITEM_ORDER_DETAIL_LIST:
                final AppCompatImageView imageView = holder.getView(R.id.img_goods);
                final AppCompatTextView title = holder.getView(R.id.tv_order_detail_name);
                final AppCompatTextView price = holder.getView(R.id.tv_order_detail_price);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_order_detail_count);

                final String name = entity.getField(MultipleFields.TITLE);
                final String count = entity.getField(OrderItemDetailFields.COUNT);
                final double priceVal = entity.getField(OrderItemDetailFields.PRICE);
                final String imageUrl = entity.getField(MultipleFields.IMAGE_URL);

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);
                title.setText(name);
                price.setText(String.valueOf(priceVal));
                tvCount.setText(count);
                break;
            default:
                break;
        }
    }
}
