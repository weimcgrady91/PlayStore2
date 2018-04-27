package com.qun.test.playstore;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public class TaskUtil {
    public static volatile TaskUtil sTaskUtil;
    private final ExecutorService mExecutorService;

    private TaskUtil() {
        mExecutorService = Executors.newCachedThreadPool();
    }

    public interface OnTaskListener {
        public void onTaskFinish(String result);
    }

    public static TaskUtil newInstance() {
        if (sTaskUtil == null) {
            synchronized (TaskUtil.class) {
                if (sTaskUtil == null) {
                    sTaskUtil = new TaskUtil();
                }
            }
        }
        return sTaskUtil;
    }

    public void addTask(final String dataUrl, final OnTaskListener listener) {

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                String result = requestServer();
                listener.onTaskFinish(result);
            }

            public String requestServer() {
                try {
                    URL url = new URL(dataUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();
                    int code = connection.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String str = null;
                        StringBuilder sb = new StringBuilder();
                        while ((str = br.readLine()) != null) {
                            sb.append(str);
                        }
                        br.close();
                        connection.disconnect();
                        Log.e("weiqun12345", "succ=" + sb.toString());
                        return sb.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return null;
            }
        });
    }
}
