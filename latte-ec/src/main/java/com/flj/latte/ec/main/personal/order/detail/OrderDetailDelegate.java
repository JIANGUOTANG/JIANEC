package com.flj.latte.ec.main.personal.order.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.diabin.latte.ec.R;
import com.flj.latte.delegates.LatteDelegate;


/**
 * Created by 11833 on 2018/3/27.
 */

public class OrderDetailDelegate  extends LatteDelegate{
    private AppCompatTextView tvAddressName;
    private AppCompatTextView tvAddressPhone;
    private AppCompatTextView tvAddressddress;
    private AppCompatTextView tvAddress_name;
    private AppCompatTextView tvOorderId;
//    private AppCompatTextView tvOorderId;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_details;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        RestClient.builder()
//                .loader(getContext())
//                .url("order_list.json")
//                .params("type", mType)
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
//                        mRecyclerView.setLayoutManager(manager);
//                        final List<MultipleItemEntity> data =
//                                new OrderListDataConverter().setJsonData(response).convert();
//                        final OrderListAdapter adapter = new OrderListAdapter(data);
//                        mRecyclerView.setAdapter(adapter);
//                        mRecyclerView.addOnItemTouchListener(new OrderListClickListener(OrderListDelegate.this));
//                    }
//                })
//                .build()
//                .get();
    }
}
