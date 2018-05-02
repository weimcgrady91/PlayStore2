package com.qun.test.playstore.actvity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qun.test.playstore.R;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.domain.AppDetailBean;
import com.qun.test.playstore.engine.DataEngine;
import com.qun.test.playstore.util.UIUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_PACKAGE_NAME = "com.qun.test.playstore.EXTRA_PACKAGE_NAME";
    private View mLlMain;
    private View mLlError;
    private ImageView mIvIcon;
    private TextView mTvName;
    private RatingBar mRbStar;
    private TextView mTvDownLoadNum;
    private TextView mTvVersion;
    private TextView mTvDate;
    private TextView mSize;
    private String mPackageName;
    private ImageView[] mSafeIcons;
    private ImageView[] mDesIcons;
    private TextView[] mSafeDes;
    private LinearLayout[] mSafeDesBar;
    private RelativeLayout mRlDesRoot;
    private LinearLayout mLlDesRoot;
    private ImageView mIvArrow;
    private int mDesHeight;
    private LinearLayout.LayoutParams mParams;

    public static void enter(Context context, String packageName) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_PACKAGE_NAME, packageName);
        context.startActivity(intent);
        Log.e("weiqun12345", "DetailActivity pckName=" + packageName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPackageName = getIntent().getStringExtra(EXTRA_PACKAGE_NAME);
        initViews();
        fetchData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mLlMain = findViewById(R.id.ll_main);
        mLlError = findViewById(R.id.ll_error);
        mIvIcon = findViewById(R.id.iv_icon);
        mTvName = findViewById(R.id.tv_name);
        mRbStar = findViewById(R.id.rb_star);
        mTvDownLoadNum = findViewById(R.id.tv_download_num);
        mTvVersion = findViewById(R.id.tv_version);
        mTvDate = findViewById(R.id.tv_date);
        mSize = findViewById(R.id.tv_size);


        mSafeIcons = new ImageView[4];
        mSafeIcons[0] = (ImageView) findViewById(R.id.iv_safe1);
        mSafeIcons[1] = (ImageView) findViewById(R.id.iv_safe2);
        mSafeIcons[2] = (ImageView) findViewById(R.id.iv_safe3);
        mSafeIcons[3] = (ImageView) findViewById(R.id.iv_safe4);

        mDesIcons = new ImageView[4];
        mDesIcons[0] = (ImageView) findViewById(R.id.iv_des1);
        mDesIcons[1] = (ImageView) findViewById(R.id.iv_des2);
        mDesIcons[2] = (ImageView) findViewById(R.id.iv_des3);
        mDesIcons[3] = (ImageView) findViewById(R.id.iv_des4);

        mSafeDes = new TextView[4];
        mSafeDes[0] = (TextView) findViewById(R.id.tv_des1);
        mSafeDes[1] = (TextView) findViewById(R.id.tv_des2);
        mSafeDes[2] = (TextView) findViewById(R.id.tv_des3);
        mSafeDes[3] = (TextView) findViewById(R.id.tv_des4);

        mSafeDesBar = new LinearLayout[4];
        mSafeDesBar[0] = (LinearLayout) findViewById(R.id.ll_des1);
        mSafeDesBar[1] = (LinearLayout) findViewById(R.id.ll_des2);
        mSafeDesBar[2] = (LinearLayout) findViewById(R.id.ll_des3);
        mSafeDesBar[3] = (LinearLayout) findViewById(R.id.ll_des4);

        mRlDesRoot = (RelativeLayout) findViewById(R.id.rl_des_root);
        mRlDesRoot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        mLlDesRoot = (LinearLayout) findViewById(R.id.ll_des_root);
        mIvArrow = (ImageView) findViewById(R.id.iv_arrow);
    }

    boolean isOpen;

    public void toggle() {
        ValueAnimator animator = null;
        if (isOpen) {
            // 关闭
            isOpen = false;
            // 属性动画
            animator = ValueAnimator.ofInt(mDesHeight, 0);// 从某个值变化到某个值
        } else {
            // 开启
            isOpen = true;
            // 属性动画
            animator = ValueAnimator.ofInt(0, mDesHeight);
        }

        // 动画更新的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            // 启动动画之后, 会不断回调此方法来获取最新的值
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // 获取最新的高度值
                Integer height = (Integer) animator.getAnimatedValue();

                System.out.println("最新高度:" + height);

                // 重新修改布局高度
                mParams.height = height;
                mLlDesRoot.setLayoutParams(mParams);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // 动画结束的事件
                // 更新小箭头的方向
                if (isOpen) {
                    mIvArrow.setImageResource(R.drawable.arrow_up);
                } else {
                    mIvArrow.setImageResource(R.drawable.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });

        animator.setDuration(200);// 动画时间
        animator.start();// 启动动画
    }

    public void parseData(String result) {
        Gson gson = new Gson();
        AppDetailBean appDetailBean = gson.fromJson(result, AppDetailBean.class);
        Log.e("weiqun12345", appDetailBean.toString());
        refreshView(appDetailBean);
    }

    private void refreshView(AppDetailBean appDetailBean) {
        Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + appDetailBean.iconUrl).error(R.drawable.ic_default).
                placeholder(R.drawable.ic_default).into(mIvIcon);
        mTvName.setText(appDetailBean.name);
        mRbStar.setRating(appDetailBean.stars);
        mTvDownLoadNum.setText(appDetailBean.downloadNum);
        mTvVersion.setText(appDetailBean.version);
        mTvDate.setText(appDetailBean.date);
        mSize.setText(Formatter.formatFileSize(UIUtil.getContext(), appDetailBean.size));


        ArrayList<AppDetailBean.Safe> safe = appDetailBean.safe;

        for (int i = 0; i < 4; i++) {
            if (i < safe.size()) {
                AppDetailBean.Safe safeInfo = safe.get(i);
                Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + safe.get(i).safeUrl).error(R.drawable.ic_default).
                        placeholder(R.drawable.ic_default).into(mSafeIcons[i]);
                mSafeDes[i].setText(safeInfo.safeDes);
                Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + safe.get(i).safeDesUrl).error(R.drawable.ic_default).
                        placeholder(R.drawable.ic_default).into(mDesIcons[i]);
            } else {
                mSafeIcons[i].setVisibility(View.GONE);
                mSafeDesBar[i].setVisibility(View.GONE);
            }
        }

        // 获取安全描述的完整高度
        mLlDesRoot.measure(0, 0);
        mDesHeight = mLlDesRoot.getMeasuredHeight();

        System.out.println("安全描述高度:" + mDesHeight);

        // 修改安全描述布局高度为0,达到隐藏效果
        mParams = (LinearLayout.LayoutParams) mLlDesRoot.getLayoutParams();
        mParams.height = 0;
        mLlDesRoot.setLayoutParams(mParams);
    }

    public String getRequestUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConfig.HOST);
        sb.append(ServerConfig.PATH_DETAIL);
        sb.append("?packageName=" + mPackageName);
        Log.e("weiqun12345", "url=" + sb.toString());
        return sb.toString();
    }

    public void fetchData() {
        DataEngine dataEngine = new DataEngine(new DataEngine.DataEngineListener() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
                mLlMain.setVisibility(View.VISIBLE);
                mLlError.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                mLlError.setVisibility(View.VISIBLE);
                mLlMain.setVisibility(View.GONE);
            }
        });
        dataEngine.fetchData(getRequestUrl());
    }
}
