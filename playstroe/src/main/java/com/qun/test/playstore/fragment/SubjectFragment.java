package com.qun.test.playstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class SubjectFragment extends BaseFragment {
    @Override
    public View obtainRootView(Context context) {
        TextView tv = new TextView(context);
        tv.setText("SubjectFragment");
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

    public SubjectFragment() {

    }

    public static SubjectFragment newInstance() {
        SubjectFragment homeFragment = new SubjectFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
