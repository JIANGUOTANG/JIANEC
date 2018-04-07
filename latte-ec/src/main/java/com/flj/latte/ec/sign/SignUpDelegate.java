package com.flj.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.diabin.latte.ec.R;
import com.flj.latte.app.ConfigKeys;
import com.flj.latte.app.Latte;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.net.RestClient;
import com.flj.latte.util.TimeCount;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.util.toast.GlobalToast;

import java.util.concurrent.ExecutorService;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class SignUpDelegate extends LatteDelegate {

    private EditText mCode = null;//验证码
    private EditText mPhone = null;//手机号码
    private EditText mPassword = null;//密码
    private EditText mRePassword = null;//
    private Button mBtgetCode;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mCode = $(R.id.register_et_code);

        mPhone = $(R.id.register_et_phoneNumber);
        mPassword = $(R.id.register_et_password);
        mRePassword = $(R.id.register_et_repassword);
        mBtgetCode = $(R.id.register_btn_getCode);
        $(R.id.register_btn_sign_up).setOnClickListener(view -> onClickSignUp());
        $(R.id.register_btn_getCode).setOnClickListener(view -> getCode());
        $(R.id.tv_link_sign_in).setOnClickListener(view -> onClickLink());

    }

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    private void onClickSignUp() {
        if (checkForm()) {
            RestClient.builder()
                    .url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")
                    .params("name", mCode.getText().toString())
                    .params("phone", mPhone.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(response -> {
                        LatteLogger.json("USER_PROFILE", response);
                        SignHandler.onSignUp(response, mISignListener);
                    })
                    .build()
                    .post();
        }
    }

    private void onClickLink() {
        getSupportDelegate().start(new SignInDelegate());
    }

    private boolean checkForm() {
        final String code = mCode.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;
//
        if (code.isEmpty()) {
            mCode.setError("请输入验证码");
            isPass = false;
        } else {
            mCode.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    /**
     * 获取验证码
     */
    TimeCount timeCount;
    public void getCode(){
         timeCount = new TimeCount(60000,1000,mBtgetCode);
        timeCount.start();
        RestClient.builder()
                .url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")
                .params("name", mPhone.getText().toString())
                .success(response -> {
                    LatteLogger.json("USER_PROFILE", response);
                    GlobalToast.toast("获取成功");
                })
                .build()
                .post();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timeCount.onFinish();
    }
}
