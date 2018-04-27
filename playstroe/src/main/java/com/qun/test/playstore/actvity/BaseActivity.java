package com.qun.test.playstore.actvity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int getLayoutId();

    public abstract void initSystemUI();

    public abstract void onViewCreated();

    public abstract void childWindowFocusChanged(boolean hasFocus);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemUI();
        setContentView(getLayoutId());
        onViewCreated();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        childWindowFocusChanged(hasFocus);
    }
}


