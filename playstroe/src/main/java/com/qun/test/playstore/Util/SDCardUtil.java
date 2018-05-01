package com.qun.test.playstore.Util;

import android.os.Environment;

/**
 * Created by Administrator on 2018/4/30.
 */

public class SDCardUtil {
    public static boolean isSDCardAvailable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(state)) {
            return true;
        }
        return false;
    }
}
