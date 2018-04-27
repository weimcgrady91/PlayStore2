package com.qun.test.playstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.qun.test.playstore.R;
import com.qun.test.playstore.adapter.BasicAdapter;
import com.qun.test.playstore.adapter.BasicViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class HomeFragment extends BaseFragment {

    private ListView mLv;
    private HomeFrgAdapter mAdapter;

    @Override
    public View obtainRootView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.fragment_home, null, false);
        mLv = contentView.findViewById(R.id.lv);
        mAdapter = new HomeFrgAdapter();
        mLv.setAdapter(mAdapter);
        return contentView;
    }

    public class HomeFrgViewHolder extends BasicViewHolder {
        public TextView mTvTitle;

        public HomeFrgViewHolder(View convertView) {
            super(convertView);
        }

        @Override
        public void findView() {
            mTvTitle = mContentView.findViewById(R.id.tv_title);
        }
    }

    public class HomeFrgAdapter extends BasicAdapter<String, HomeFrgViewHolder> {
        @Override
        public View getItemView(ViewGroup parent) {
            return LayoutInflater.from(getActivity()).inflate(R.layout.item_home_frg, parent, false);
        }

        @Override
        public HomeFrgViewHolder getViewHolder(View rootView) {
            return new HomeFrgViewHolder(rootView);
        }

        @Override
        public void bindView(HomeFrgViewHolder viewHolder, int position) {
            String data = mData.get(position);
            TextView title = viewHolder.mTvTitle;
            title.setText(data);
        }
    }

    @Override
    public void onLoadSuccess(String result) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("这是第" + i + "个测试条目");
        }
        mAdapter.setData(data);
    }

    @Override
    public void onLoadFailure() {

    }

    @Override
    public String getRequestUrl() {
        return "http://www.baidu.com";
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
