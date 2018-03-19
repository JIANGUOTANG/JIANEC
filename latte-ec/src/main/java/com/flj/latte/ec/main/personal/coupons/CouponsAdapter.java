package com.flj.latte.ec.main.personal.coupons;

import com.diabin.latte.ec.R;

import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

import static com.flj.latte.ec.main.personal.coupons.CouponsItemType.COUPONS_ITEM;

/**
 * Created by 11833 on 2018/3/18.
 */

public class CouponsAdapter extends MultipleRecyclerAdapter {

    protected CouponsAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(COUPONS_ITEM, R.layout.item_coupons);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);

    }
}
