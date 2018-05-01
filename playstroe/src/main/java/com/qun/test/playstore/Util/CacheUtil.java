package com.qun.test.playstore.Util;

import com.qun.test.playstore.App;

import java.io.File;

/**
 * Created by Administrator on 2018/4/30.
 */

public class CacheUtil {
    public static final File getCacheFile(String fileName) {
        boolean available = SDCardUtil.isSDCardAvailable();
        if (available) {
            File cacheDir = App.sContext.getExternalCacheDir();
            File cacheFile = new File(cacheDir, MD5Util.MD5(fileName));
            return cacheFile;
        }
        return null;
    }

}
