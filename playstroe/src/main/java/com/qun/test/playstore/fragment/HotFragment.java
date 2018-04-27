package com.qun.test.playstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class HotFragment extends BaseFragment {
    @Override
    public View obtainRootView(Context context) {
        TextView tv = new TextView(context);
        tv.setText("HotFragment");
        tv.setTextSize(22);
        return tv;
    }

    @Override
    public void onLoadSuccess(String result) {

    }

    @Override
    public void onLoadFailure() {

    }

    @Override
    public String getRequestUrl() {
        return "http://www.baidu.com";
    }

    public static HotFragment newInstance() {
        HotFragment homeFragment = new HotFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
