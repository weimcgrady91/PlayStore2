package com.qun.test.playstore.util;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.qun.test.playstore.App;

/**
 * Created by Administrator on 2018/4/30.
 */

public class UIUtil {
    public static View inflate(int layoutId) {
        return LayoutInflater.from(App.sContext).inflate(layoutId, null, false);
    }

    public static int dip2px(int dip) {
        float density = App.sContext.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static Context getContext() {
        return App.sContext;
    }

    public static int getMainThreadId() {
        return App.getMainThreadId();
    }

    public static boolean isRunOnUIThread() {
        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }

        return false;
    }

    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程, 直接运行
            r.run();
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            getHandler().post(r);
        }
    }

    public static Handler getHandler() {
        return App.getHandler();
    }
}
