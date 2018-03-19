package com.flj.latte.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diabin.latte.ec.R;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.ISuccess;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

/**
 * Created by 11833 on 2018/3/15.
 */

public class AddAddressDelegate extends LatteDelegate implements View.OnClickListener,ISuccess{

    private LinearLayout ll_location = null;
    private TextView tvLocation = null;
    private Button btnSave = null;

    private EditText edtConsignee = null;
    private EditText edtPhone = null;
    private EditText edtDetailAddress = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_add_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ll_location   = $(R.id.ll_location);
        tvLocation   = $(R.id.tv_location);
        btnSave   = $(R.id.address_btn_save);
        edtConsignee   = $(R.id.edtConsignee);
        edtPhone   = $(R.id.edtPhone);
        edtDetailAddress   = $(R.id.edtDetailAddress);
        btnSave.setOnClickListener(this);
        initPicker();
    }
    /**
     * 初始化地址选择器
     */
    private void initPicker(){
        //申明对象
        CityPickerView mPicker=new CityPickerView();
        mPicker.init(getContext());
        //添加默认的配置，不需要自己定义
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);
        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuffer stringBuffer = new StringBuffer();
                //省份
                if (province != null) {
                    stringBuffer.append(province);
                }

                //城市
                if (city != null) {
                    stringBuffer.append(city);
                }

                //地区
                if (district != null) {
                    stringBuffer.append(district);
                }
                tvLocation.setText(stringBuffer.toString());
            }

            @Override
            public void onCancel() {
//                "已取消"
            }
        });

        ll_location.setOnClickListener(view -> {
            //显示
            mPicker.showCityPicker( );
        });
    }

    @Override
    public void onClick(View view) {
        if(checkForm()){
            //新增地址
            createAddress();
        }
    }
    //创建新地址
    private void createAddress(){
        RestClient.builder()
                .url("")
                .params("address",tvLocation.getText()+edtDetailAddress.getText().toString())
                .params("consignee",edtConsignee.getText().toString())
                .params("phone",edtPhone.getText().toString())
                .success(this)
                .build()
                .post();

    }

    @Override
    public void onSuccess(String response) {

    }
    //表单检查
    private boolean checkForm() {
        //收货人
        final String consignee = edtConsignee.getText().toString();
        final String phone = edtPhone.getText().toString();
        final String detailAddress = edtDetailAddress.getText().toString();

        boolean isPass = true;
        if (consignee.isEmpty()) {
            edtConsignee.setError("请输入收货人");
            isPass = false;
        } else {
            edtConsignee.setError(null);
        }

        if (phone.isEmpty()||!Patterns.PHONE.matcher(phone).matches()) {
            edtPhone.setError("请输入正确的手机号");
            isPass = false;
        } else {
            edtPhone.setError(null);
        }
        if (detailAddress.isEmpty()||tvLocation.getText().toString().isEmpty()) {
            edtDetailAddress.setError("请输入地址");
            isPass = false;
        } else {
            edtDetailAddress.setError(null);
        }

        return isPass;
    }

}
