package com.qun.test.playstore.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qun.test.playstore.App;
import com.qun.test.playstore.Util.UIUtil;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.ui.fly.ShakeListener;
import com.qun.test.playstore.ui.fly.StellarMap;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class RecommendFragment extends BaseFragment {
    private ArrayList<String> mData = new ArrayList<>();
    private StellarMap mStellar;

    @Override
    public View obtainRootView(Context context) {
        mStellar = new StellarMap(App.sContext);


        return mStellar;
    }

    class RecommendAdapter implements StellarMap.Adapter {

        // 返回组的个数
        @Override
        public int getGroupCount() {
            return 2;
        }

        // 返回某组的item个数
        @Override
        public int getCount(int group) {
            int count = mData.size() / getGroupCount();
            if (group == getGroupCount() - 1) {
                // 最后一页, 将除不尽,余下来的数量追加在最后一页, 保证数据完整不丢失
                count += mData.size() % getGroupCount();
            }

            return count;
        }

        // 初始化布局
        @Override
        public View getView(int group, int position, View convertView) {
            // 因为position每组都会从0开始计数, 所以需要将前面几组数据的个数加起来,才能确定当前组获取数据的角标位置
            position += (group) * getCount(group - 1);

            // System.out.println("pos:" + position);

            final String keyword = mData.get(position);

            TextView view = new TextView(App.sContext);
            view.setText(keyword);

            Random random = new Random();
            // 随机大小, 16-25
            int size = 16 + random.nextInt(10);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);

            // 随机颜色
            // r g b, 0-255 -> 30-230, 颜色值不能太小或太大, 从而避免整体颜色过亮或者过暗
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);

            view.setTextColor(Color.rgb(r, g, b));

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(App.sContext, keyword,
                            Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        // 返回下一组的id
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            System.out.println("isZoomIn:" + isZoomIn);
            if (isZoomIn) {
                // 往下滑加载上一页
                if (group > 0) {
                    group--;
                } else {
                    // 跳到最后一页
                    group = getGroupCount() - 1;
                }
            } else {
                // 往上滑加载下一页
                if (group < getGroupCount() - 1) {
                    group++;
                } else {
                    // 跳到第一页
                    group = 0;
                }
            }
            return group;
        }

    }

    @Override
    public void onLoadSuccess(String result) {
        Gson gson = new Gson();
        Log.e("weiqun12345", "result=" + result);
        ArrayList<String> apps = gson.fromJson(result, new TypeToken<ArrayList<String>>() {
        }.getType());
        if (apps.size() > 0) {
            mData = apps;
            // 随机方式, 将控件划分为9行6列的的格子, 然后在格子中随机展示
            mStellar.setAdapter(new RecommendAdapter());
            mStellar.setRegularity(6, 9);
            // 设置内边距10dp
            int padding = UIUtil.dip2px(10);
            mStellar.setInnerPadding(padding, padding, padding, padding);

            // 设置默认页面, 第一组数据
            mStellar.setGroup(0, true);

            ShakeListener shake = new ShakeListener(App.sContext);
            shake.setOnShakeListener(new ShakeListener.OnShakeListener() {

                @Override
                public void onShake() {
                    mStellar.zoomIn();// 跳到下一页数据
                }
            });
            updateViewState(VIEW_STATE_SUCCESS);
        } else if (apps.size() == 0) {
            updateViewState(VIEW_STATE_EMPTY);
        }
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
        sb.append(ServerConfig.PATH_RECOMMEND);
        sb.append("?index=" + 0);
        Log.e("weiqun12345", "url=" + sb.toString());
        return sb.toString();
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
