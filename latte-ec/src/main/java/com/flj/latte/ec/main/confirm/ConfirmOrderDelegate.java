package com.flj.latte.ec.main.confirm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.diabin.latte.ec.R;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ec.main.personal.address.AddressDelegate;
import com.flj.latte.ec.pay.PayDelegate;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.ISuccess;
import com.flj.latte.util.log.LatteLogger;

import java.util.WeakHashMap;

/**
 * Created by 11833 on 2018/3/29.
 * 订单确认页面
 */

public class ConfirmOrderDelegate extends LatteDelegate {
    /**
     * 地址选择
     */

    private ConstraintLayout clChoiceAddress = null;
    private Button btn_pay = null;//支付按钮

    private AppCompatTextView tv_pay_price = null;//价格
    private AppCompatTextView tv_address_name = null;//姓名
    private AppCompatTextView tv_address_phone = null;//电话
    private AppCompatTextView tv_address_address = null;//地址

    private Bundle mArgs = null;
    private static final int REQ_CODE = 100;
    @Override
    public Object setLayout() {
        return R.layout.delegate_confirm_order;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();//用于传递参数给另一个界面
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        clChoiceAddress = $(R.id.choice_address);

        tv_address_name = $(R.id.tv_address_name);
        tv_address_phone = $(R.id.tv_address_phone);
        tv_address_address = $(R.id.tv_address_address);
        tv_pay_price = $(R.id.tv_pay_price);
        btn_pay = $(R.id.btn_pay);
        AddressDelegate addressDelegate  = new AddressDelegate();
        clChoiceAddress.setOnClickListener(view -> getSupportDelegate().startForResult(new AddressDelegate(),REQ_CODE));
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE &&data!=null ) {
            // 在此通过Bundle data 获取返回的数据
            data.getInt("id");
            data.getString("phone");
            data.getString("address");
            data.getBoolean("isDefault");
            tv_address_name.setText(data.getString("name"));
            tv_address_phone.setText(data.getString("phone"));
            tv_address_address.setText(data.getString("address"));

        }
    }

    //创建订单，注意，和支付是没有关系的
    private void createOrder() {
        final String orderUrl = "你的生成订单的API";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        //加入你的参数
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
                        LatteLogger.d("ORDER", response);
                        final int orderId = JSON.parseObject(response).getInteger("result");
//                        FastPay.create(ShopCartDelegate.this)
//                                .setPayResultListener(ShopCartDelegate.this)
//                                .setOrderId(orderId)
//                                .beginPayDialog();
                        //跳转到支付选择界面
                        final PayDelegate delegate = new PayDelegate();
                        delegate.setArguments(mArgs);
                        getParentDelegate().getSupportDelegate().start(delegate);
                    }
                })
                .build()
                .post();

    }
}
