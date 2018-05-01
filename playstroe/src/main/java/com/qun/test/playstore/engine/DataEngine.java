package com.qun.test.playstore.engine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.qun.test.playstore.Util.TaskUtil;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public class DataEngine {
    public DataEngineListener mListener;

    public DataEngine(DataEngineListener dataEngineListener) {
        mListener = dataEngineListener;
    }

    public interface DataEngineListener {
        public void onSuccess(String result);

        public void onFailure();
    }

    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mListener.onFailure();
            } else if (msg.what == 2) {
                mListener.onSuccess((String) msg.obj);
            }
        }
    };


    public void fetchData(String urlStr) {
        if (TextUtils.isEmpty(urlStr)) {
            mHandler.sendEmptyMessage(1);
        }
        TaskUtil.newInstance().addTask(urlStr, new TaskUtil.OnTaskListener() {
            @Override
            public void onTaskFinish(String result) {
                if (TextUtils.isEmpty(result)) {
                    mHandler.sendEmptyMessage(1);
                } else {
                    Message message = mHandler.obtainMessage();
                    message.obj = result;
                    message.what = 2;
                    mHandler.sendMessage(message);
                }
            }
        });
    }
}
