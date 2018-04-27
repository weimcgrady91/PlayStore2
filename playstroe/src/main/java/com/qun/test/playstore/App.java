package com.qun.test.playstore;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public class App extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
