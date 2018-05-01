package com.qun.test.playstore.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qun.test.playstore.App;
import com.qun.test.playstore.R;
import com.qun.test.playstore.adapter.BasicAdapter;
import com.qun.test.playstore.adapter.BasicViewHolder;
import com.qun.test.playstore.adapter.HomeViewHolder;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.domain.AppBean;
import com.qun.test.playstore.domain.AppInfo;
import com.qun.test.playstore.domain.BaseRequestBean;
import com.qun.test.playstore.domain.HomeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class AppFragment extends BaseFragment {
    private ListView mLv;
    private AppAdapter mAdapter;

    @Override
    public View obtainRootView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.fragment_home, null, false);
        mLv = contentView.findViewById(R.id.lv);
        mLv.setSelector(new ColorDrawable());
        mLv.setDivider(null);
        mLv.setCacheColorHint(Color.TRANSPARENT);
        mAdapter = new AppAdapter();
        mLv.setAdapter(mAdapter);
        return contentView;
    }

    public class AppAdapter extends BasicAdapter<AppInfo> {

        @Override
        public BasicViewHolder getViewHolder() {
            return new HomeViewHolder();
        }

        @Override
        public void loadMore() {
            loadData();
        }
    }

    @Override
    public void onLoadSuccess(String result) {
        Gson gson = new Gson();
        Log.e("weiqun12345", "result=" + result);
        List<AppInfo> infos = gson.fromJson(result, new TypeToken<List<AppInfo>>() {
        }.getType());
        if (infos.size() == 0 && mAdapter.getDataSize() == 0) {
            updateViewState(VIEW_STATE_EMPTY);
        } else {
            mAdapter.addData(infos);
            updateViewState(VIEW_STATE_SUCCESS);
        }
    }

    @Override
    public void onLoadFailure() {
        Toast.makeText(App.sContext, "loadFailure", Toast.LENGTH_SHORT).show();
        if (mAdapter.getDataSize() == 0) {
            updateViewState(VIEW_STATE_ERROR);
        } else {
            mAdapter.addData(null);
            updateViewState(VIEW_STATE_SUCCESS);
        }
    }

    @Override
    public String getRequestUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConfig.HOST);
        sb.append(ServerConfig.PATH_APP);
        sb.append("?index=" + mAdapter.getDataIndex());
        Log.e("weiqun12345", "url=" + sb.toString());
        return sb.toString();
    }

    public AppFragment() {

    }

    public static AppFragment newInstance() {
        Log.e("weiqun12345", "AppFragment newInstance");
        AppFragment homeFragment = new AppFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
