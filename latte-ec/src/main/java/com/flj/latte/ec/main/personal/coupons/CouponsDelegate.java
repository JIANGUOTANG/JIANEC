package com.flj.latte.ec.main.personal.coupons;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.diabin.latte.ec.R;

import com.flj.latte.delegates.bottom.BottomItemDelegate;

import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by 11833 on 2018/3/18.
 */

public class CouponsDelegate extends BottomItemDelegate {
    private RecyclerView mRecyclerView = null;
    private CouponsAdapter couponsAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_coupons;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRecyclerView = $(R.id.rv_coupons);
        final ArrayList<MultipleItemEntity> data =
                new ArrayList<>(10);
        final MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, CouponsItemType.COUPONS_ITEM)
                .setField(MultipleFields.ID, 2)
                .build();
        couponsAdapter=new CouponsAdapter(data);
        data.add(entity);
        data.add(entity);
        data.add(entity);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(couponsAdapter);
    }

}
