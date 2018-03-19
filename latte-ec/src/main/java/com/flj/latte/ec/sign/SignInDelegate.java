package com.flj.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.ISuccess;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.wechat.LatteWeChat;
import com.flj.latte.wechat.callbacks.IWeChatSignInCallback;


/**
 * Created by 傅令杰 on 2017/4/22
 */

public class SignInDelegate extends LatteDelegate implements View.OnClickListener {

    private EditText mPhoneNumber = null;
    private EditText mPassword = null;
    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    private void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder()
                    .url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")
                    .params("phoneNumber", mPhoneNumber.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(response -> {
                        LatteLogger.json("USER_PROFILE", response);
                        SignHandler.onSignIn(response, mISignListener);
                    })
                    .build()
                    .post();
        }
    }

    private void onClickWeChat() {
        LatteWeChat
                .getInstance()
                .onSignSuccess(new IWeChatSignInCallback() {
                    @Override
                    public void onSignInSuccess(String userInfo) {
                        Toast.makeText(getContext(), userInfo, Toast.LENGTH_LONG).show();
                    }
                })
                .signIn();
    }

    private void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String email = mPhoneNumber.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mPhoneNumber.setError("错误的电话格式");
            isPass = false;
        } else {
            mPhoneNumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mPhoneNumber = $(R.id.login_et_phoneNumber);
        mPassword = $(R.id.login_et_password);
        $(R.id.tv_register).setOnClickListener(this);
        $(R.id.login_btn_login).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_register) {
            onClickSignIn();
        } else if (i == R.id.login_btn_login) {
            onClickLink();
        }
    }
}
