package com.qun.test.playstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.qun.test.playstore.R;
import com.qun.test.playstore.engine.DataEngine;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public abstract class BaseFragment extends Fragment {

    private Button mBtnReload;

    public abstract View obtainRootView(Context context);

    public abstract void onLoadSuccess(String result);

    public abstract void onLoadFailure();

    public abstract String getRequestUrl();

    public View mRootView;
    public View mContentView;
    public View mErrorView;
    public View mLoadingView;
    public View mEmptyView;
    public int viewState;
    public final int VIEW_STATE_SUCCESS = 0;
    public final int VIEW_STATE_LOADING = 1;
    public final int VIEW_STATE_ERROR = 2;
    public final int VIEW_STATE_EMPTY = 3;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_base, container, false);
        mLoadingView = mRootView.findViewById(R.id.loadview);
        mErrorView = mRootView.findViewById(R.id.errorview);
        mEmptyView = mRootView.findViewById(R.id.emptyview);
        mBtnReload = mRootView.findViewById(R.id.btn_reload);
        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        mContentView = obtainRootView(getActivity());
        FrameLayout fl = mRootView.findViewById(R.id.fl);
        fl.addView(mContentView);
        loadData();
        return mRootView;
    }

    public void loadData() {
        updateViewState(VIEW_STATE_LOADING);
        DataEngine dataEngine = new DataEngine(new DataEngine.DataEngineListener() {
            @Override
            public void onSuccess(String result) {
                onLoadSuccess(result);
            }

            @Override
            public void onFailure() {
                onLoadFailure();
            }
        });
        dataEngine.fetchData(getRequestUrl());
    }

    public void updateViewState(int viewState) {
        this.viewState = viewState;
        switch (viewState) {
            case VIEW_STATE_SUCCESS:
                mContentView.setVisibility(View.VISIBLE);
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                break;
            case VIEW_STATE_LOADING:
                mLoadingView.setVisibility(View.VISIBLE);
                mContentView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                break;
            case VIEW_STATE_ERROR:
                mErrorView.setVisibility(View.VISIBLE);
                mContentView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                break;
            case VIEW_STATE_EMPTY:
                mEmptyView.setVisibility(View.VISIBLE);
                mContentView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                break;
        }
    }
}

