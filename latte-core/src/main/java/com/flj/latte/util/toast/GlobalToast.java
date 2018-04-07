package com.flj.latte.util.toast;

import android.widget.Toast;

import com.flj.latte.app.Latte;

/**
 * Created by 11833 on 2018/4/5.
 */

public class GlobalToast {

    public static void toast(String message){
        Toast.makeText(Latte.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
