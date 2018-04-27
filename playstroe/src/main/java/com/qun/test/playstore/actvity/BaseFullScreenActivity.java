package com.qun.test.playstore.actvity;

import android.view.View;
import android.view.WindowManager;

import com.qun.test.playstore.actvity.BaseActivity;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public abstract class BaseFullScreenActivity extends BaseActivity {

    @Override
    public void initSystemUI() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    public void childWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            int uiVisibility = View.INVISIBLE;
            getWindow().getDecorView().setSystemUiVisibility(uiVisibility);
        }
    }
}
