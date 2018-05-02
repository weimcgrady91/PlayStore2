package com.qun.test.playstore.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qun.test.playstore.App;
import com.qun.test.playstore.R;
import com.qun.test.playstore.actvity.DetailActivity;
import com.qun.test.playstore.adapter.BasicAdapter;
import com.qun.test.playstore.adapter.BasicViewHolder;
import com.qun.test.playstore.adapter.HomeViewHolder;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.domain.AppInfo;
import com.qun.test.playstore.domain.HomeBean;
import com.qun.test.playstore.util.UIUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class HomeFragment extends BaseFragment {

    private ListView mLv;
    private HomeAdapter mAdapter;
    private ViewPager mViewPager;

    @Override
    public View obtainRootView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.fragment_home, null, false);
        mLv = contentView.findViewById(R.id.lv);
        mLv.setSelector(new ColorDrawable());
        mLv.setDivider(null);
        mLv.setCacheColorHint(Color.TRANSPARENT);
        mAdapter = new HomeAdapter();
        mViewPager = UIUtil.inflate(R.layout.item_viewpager).findViewById(R.id.view_pager);
        mViewPager.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.dip2px(200)));
        mLv.addHeaderView(mViewPager);
        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = (AppInfo) parent.getAdapter().getItem(position);
                if (appInfo != null) {
                    DetailActivity.enter(getActivity(),appInfo.packageName);
                } else {
                    Log.e("weiqun12345","appInfo==null");
                }
            }
        });
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
            HomeViewPagerAdapter homeViewPagerAdapter = new HomeViewPagerAdapter(bean.picture);
            mViewPager.setAdapter(homeViewPagerAdapter);
            updateViewState(VIEW_STATE_SUCCESS);
        }
    }

    public class HomeViewPagerAdapter extends PagerAdapter {
        List<String> data;

        public HomeViewPagerAdapter(List<String> picture) {
            this.data = picture;
        }

        @Override
        public int getCount() {
            return this.data.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(UIUtil.getContext());
            Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + this.data.get(position)).error(R.drawable.ic_default).
                    placeholder(R.drawable.subject_default).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.dip2px(200)));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
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
