package com.flj.latte.ec.main.personal.order;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.flj.latte.ec.main.personal.non_payment.NonPayDelegate;
import com.flj.latte.ec.main.personal.order.detail.OrderDetailDelegate;

/**
 * Created by 傅令杰
 */

public class OrderListClickListener extends SimpleClickListener {

    private final OrderListDelegate DELEGATE;

    public OrderListClickListener(OrderListDelegate delegate) {
        this.DELEGATE = delegate;
    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DELEGATE.getSupportDelegate().start(new OrderDetailDelegate());
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
