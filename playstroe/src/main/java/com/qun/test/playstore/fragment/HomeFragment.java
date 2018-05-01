package com.qun.test.playstore.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qun.test.playstore.App;
import com.qun.test.playstore.R;
import com.qun.test.playstore.adapter.BasicAdapter;
import com.qun.test.playstore.adapter.BasicViewHolder;
import com.qun.test.playstore.adapter.HomeViewHolder;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.domain.AppInfo;
import com.qun.test.playstore.domain.HomeBean;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class HomeFragment extends BaseFragment {

    private ListView mLv;
    private HomeAdapter mAdapter;

    @Override
    public View obtainRootView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.fragment_home, null, false);
        mLv = contentView.findViewById(R.id.lv);
        mLv.setSelector(new ColorDrawable());
        mLv.setDivider(null);
        mLv.setCacheColorHint(Color.TRANSPARENT);
        mAdapter = new HomeAdapter();
        mLv.setAdapter(mAdapter);
        return contentView;
    }

    public class HomeAdapter extends BasicAdapter<AppInfo> {

        @Override
        public BasicViewHolder getViewHolder() {
            return new HomeViewHolder();
        }

        @Override
        public void loadMore() {
            Log.e("weiqun12345", "home load more");
            loadData();
        }
    }


    @Override
    public void onLoadSuccess(String result) {
        Log.e("weiqun12345", "baseRequestBean result=" + result.toString());
        Gson gson = new Gson();
        HomeBean bean = gson.fromJson(result, HomeBean.class);
        if (bean.list.size() == 0 && mAdapter.getDataSize() == 0) {
            updateViewState(VIEW_STATE_EMPTY);
        } else {
            mAdapter.addData(bean.list);
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
        sb.append(ServerConfig.PATH_HOME);
        sb.append("?index=" + mAdapter.getDataIndex());
        Log.e("weiqun12345", "url=" + sb.toString());
        return sb.toString();
    }

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        Log.e("weiqun12345", "HomeFragment newInstance");
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
