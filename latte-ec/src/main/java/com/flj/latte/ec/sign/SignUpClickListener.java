package com.flj.latte.ec.sign;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.diabin.latte.ec.R;
import com.flj.latte.ec.main.personal.profile.UploadConfig;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.ISuccess;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.flj.latte.util.callback.IGlobalCallback;
import com.flj.latte.util.log.LatteLogger;

/**
 * Created by 11833 on 2018/3/20.
 */

public class SignUpClickListener implements View.OnClickListener {
    private final SignUpDelegate DELEGATE;

    public SignUpClickListener(SignUpDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public void onClick(View view) {

    }

}
