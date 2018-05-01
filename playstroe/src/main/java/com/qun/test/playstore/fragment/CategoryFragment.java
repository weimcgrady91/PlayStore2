package com.qun.test.playstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qun.test.playstore.domain.BaseRequestBean;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class CategoryFragment extends BaseFragment {
    @Override
    public View obtainRootView(Context context) {
        TextView tv = new TextView(context);
        tv.setText("CategoryFragment");
        tv.setTextSize(22);
        return tv;
    }


    @Override
    public void onLoadSuccess(String t) {

    }

    @Override
    public void onLoadFailure() {

    }

    @Override
    public String getRequestUrl() {
        return "http://www.baidu.com";
    }

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance() {
        CategoryFragment homeFragment = new CategoryFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
