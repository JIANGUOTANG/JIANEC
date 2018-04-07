package com.flj.latte.util;

import android.os.CountDownTimer;
import android.widget.Button;

import com.diabin.latte.R;

/**
 * Created by 11833 on 2018/4/5.
 */

public class TimeCount extends CountDownTimer {

    private Button btn_count;

    public TimeCount(long millisInFuture, long countDownInterval,Button btn_count) {
        super(millisInFuture, countDownInterval);
        this.btn_count = btn_count;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btn_count.setEnabled(false);
        btn_count.setText(millisUntilFinished / 1000 + "秒");
    }

    @Override
    public void onFinish() {
        btn_count.setEnabled(true);
        btn_count.setText("获取验证码");

    }

}
