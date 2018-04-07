package com.flj.latte.ec.pay;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diabin.latte.ec.R;
import com.flj.latte.app.ConfigKeys;
import com.flj.latte.app.Latte;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.ISuccess;
import com.flj.latte.ui.loader.LatteLoader;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.util.toast.GlobalToast;
import com.flj.latte.wechat.LatteWeChat;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by 11833 on 2018/4/6.
 */

public class PayDelegate extends LatteDelegate implements View.OnClickListener,IAlPayResultListener{
    private Activity mActivity = null;
    private Button mBtPay;
    private RadioButton mRdoAliPay;
    private RadioButton mRdoWeChatPay;
    private int mOrderId;
    private PayType mPayType = PayType.ALI_PAY;
    public static final String ORDER_ID= "ORDER_ID";
    @Override
    public Object setLayout() {
        return R.layout.delegate_pay;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mBtPay = $(R.id.btn_pay);
        mRdoAliPay = $(R.id.rdo_ali_pay);
        mRdoWeChatPay = $(R.id.rdo_we_chat);
        $(R.id.ll_pay_we_chat).setOnClickListener(this);//选择微信支付
        $(R.id.ll_pay_ali).setOnClickListener(this);//选择支付宝支付
        initPayInfo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mOrderId = args.getInt(ORDER_ID);
        }
    }

    /**
     * 初始化支付信息
     */
    private void initPayInfo(){
        RestClient.builder()
                .url("goods_detail_data_1.json")
                .loader(getContext())
                .success(response -> {
                    final JSONObject data =
                            JSON.parseObject(response).getJSONObject("data");
                    initPager(data);

                })
                .build()
                .get();
    }

    private void initPager(JSONObject data) {
        mBtPay.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if(viewId==R.id.btn_pay){
            //点击支付按钮
        }else if(viewId==R.id.ll_pay_we_chat){
            mRdoAliPay.setChecked(false);
            mRdoWeChatPay.setChecked(true);
            mPayType = PayType.WE_CHAT_PAY;//微信支付

        }
        else if(viewId==R.id.ll_pay_ali){
            mRdoWeChatPay.setChecked(false);
            mRdoAliPay.setChecked(true);
            mPayType = PayType.ALI_PAY;//支付宝支付
        }
    }

    private void alPay(int orderId) {
        final String singUrl = "你的服务端支付地址" + orderId;
        //获取签名字符串
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySign = JSON.parseObject(response).getString("result");
                        LatteLogger.d("PAY_SIGN", paySign);
                        //必须是异步的调用客户端支付接口
                        final PayAsyncTask payAsyncTask = new PayAsyncTask(getProxyActivity(), PayDelegate.this);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);
                    }
                })
                .build()
                .post();
    }

    private void weChatPay(int orderId) {
        LatteLoader.stopLoading();
        final String weChatPrePayUrl = "你的服务端微信预支付地址" + orderId;
        LatteLogger.d("WX_PAY", weChatPrePayUrl);

        final IWXAPI iwxapi = LatteWeChat.getInstance().getWXAPI();
        final String appId = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
        iwxapi.registerApp(appId);
        RestClient.builder()
                .url(weChatPrePayUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject result =
                                JSON.parseObject(response).getJSONObject("result");
                        final String prepayId = result.getString("prepayid");
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timestamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                        final PayReq payReq = new PayReq();
                        payReq.appId = appId;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        iwxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }

    @Override
    public void onPaySuccess() {
        GlobalToast.toast("支付成功");
    }

    @Override
    public void onPaying() {
        GlobalToast.toast("正在支付");
    }

    @Override
    public void onPayFail() {
        GlobalToast.toast("正在失败");
    }

    @Override
    public void onPayCancel() {
        GlobalToast.toast("用户取消");
    }

    @Override
    public void onPayConnectError() {
        GlobalToast.toast("连接出错");
    }
}
