package com.qun.test.playstore.actvity;

import android.os.Handler;
import android.os.Message;

import com.qun.test.playstore.R;

public class SplashActivity extends BaseFullScreenActivity {
    private final long DELAY_MILLIS = 2000;
    private final int WHAT_ENTER_HOME = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_ENTER_HOME) {
                MainActivity.enter(SplashActivity.this);
                finish();
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onViewCreated() {
        enterHome();
    }

    private void enterHome() {
        mHandler.sendEmptyMessageDelayed(WHAT_ENTER_HOME, DELAY_MILLIS);
    }

}
