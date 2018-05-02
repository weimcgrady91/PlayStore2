package com.qun.test.playstore.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qun.test.playstore.App;
import com.qun.test.playstore.util.DrawableUtils;
import com.qun.test.playstore.util.UIUtil;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.ui.FlowLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class HotFragment extends BaseFragment {
    public ArrayList<String> mData;
    private ScrollView mScrollView;

    @Override
    public View obtainRootView(Context context) {
        // 支持上下滑动
        mScrollView = new ScrollView(UIUtil.getContext());
        return mScrollView;
    }


    @Override
    public void onLoadSuccess(String result) {
        Gson gson = new Gson();
        Log.e("weiqun12345", "result=" + result);
        ArrayList<String> apps = gson.fromJson(result, new TypeToken<ArrayList<String>>() {
        }.getType());
        mData = apps;
        FlowLayout flow = new FlowLayout(UIUtil.getContext());

        int padding = UIUtil.dip2px(10);
        flow.setPadding(padding, padding, padding, padding);// 设置内边距

        flow.setHorizontalSpacing(UIUtil.dip2px(6));// 水平间距
        flow.setVerticalSpacing(UIUtil.dip2px(8));// 竖直间距

        for (int i = 0; i < mData.size(); i++) {
            final String keyword = mData.get(i);
            TextView view = new TextView(UIUtil.getContext());
            view.setText(keyword);

            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);// 18sp
            view.setPadding(padding, padding, padding, padding);
            view.setGravity(Gravity.CENTER);

            // 生成随机颜色
            Random random = new Random();
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);

            int color = 0xffcecece;// 按下后偏白的背景色

            // GradientDrawable bgNormal = DrawableUtils.getGradientDrawable(
            // Color.rgb(r, g, b), UIUtils.dip2px(6));
            // GradientDrawable bgPress = DrawableUtils.getGradientDrawable(
            // color, UIUtils.dip2px(6));
            // StateListDrawable selector = DrawableUtils.getSelector(bgNormal,
            // bgPress);

            StateListDrawable selector = DrawableUtils.getSelector(
                    Color.rgb(r, g, b), color, UIUtil.dip2px(6));
            view.setBackgroundDrawable(selector);

            flow.addView(view);

            // 只有设置点击事件, 状态选择器才起作用
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtil.getContext(), keyword,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        mScrollView.addView(flow);
        updateViewState(VIEW_STATE_SUCCESS);
    }

    @Override
    public void onLoadFailure() {
        Toast.makeText(App.sContext, "loadFailure", Toast.LENGTH_SHORT).show();
        updateViewState(VIEW_STATE_ERROR);
    }

    @Override
    public String getRequestUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConfig.HOST);
        sb.append(ServerConfig.PATH_HOT);
        sb.append("?index=" + 0);
        Log.e("weiqun12345", "url=" + sb.toString());
        return sb.toString();
    }

    public static HotFragment newInstance() {
        HotFragment homeFragment = new HotFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
