package com.qun.test.playstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class RecommendFragment extends BaseFragment {
    @Override
    public View obtainRootView(Context context) {
        TextView tv = new TextView(context);
        tv.setText("RecommendFragment");
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

    public RecommendFragment() {

    }

    public static RecommendFragment newInstance() {
        RecommendFragment homeFragment = new RecommendFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
