package com.qun.test.playstore;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public class App extends Application {
    public static Context sContext;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
    }


    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
