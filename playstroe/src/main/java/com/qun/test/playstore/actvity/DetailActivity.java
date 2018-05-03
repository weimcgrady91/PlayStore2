package com.qun.test.playstore.actvity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qun.test.playstore.R;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.domain.AppDetailBean;
import com.qun.test.playstore.engine.DataEngine;
import com.qun.test.playstore.util.UIUtil;
import com.qun.test.playstore.view.ProgressHorizontal;
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
    private ImageView[] mIvPics;
    private TextView mTvDes;
    private TextView mTvAuthor;
    private ImageView mIvArrow1;
    private RelativeLayout mRlToggle;
    private ProgressHorizontal mPbProgress;

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


        mIvPics = new ImageView[5];
        mIvPics[0] = (ImageView) findViewById(R.id.iv_pic1);
        mIvPics[1] = (ImageView) findViewById(R.id.iv_pic2);
        mIvPics[2] = (ImageView) findViewById(R.id.iv_pic3);
        mIvPics[3] = (ImageView) findViewById(R.id.iv_pic4);
        mIvPics[4] = (ImageView) findViewById(R.id.iv_pic5);


        mTvDes = (TextView) findViewById(R.id.tv_detail_des);
        mTvAuthor = (TextView) findViewById(R.id.tv_detail_author);
        mIvArrow1 = (ImageView) findViewById(R.id.iv_arrow);
        mRlToggle = (RelativeLayout) findViewById(R.id.rl_detail_toggle);

        mRlToggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggle2();
            }
        });
        FrameLayout flProgress = findViewById(R.id.fl);
        mPbProgress = new ProgressHorizontal(UIUtil.getContext());
        mPbProgress.setProgressBackgroundResource(R.drawable.progress_bg);// 进度条背景图片
        mPbProgress.setProgressResource(R.drawable.progress_normal);// 进度条图片
        mPbProgress.setProgressTextColor(Color.WHITE);// 进度文字颜色
        mPbProgress.setProgressTextSize(UIUtil.dip2px(18));// 进度文字大小

        // 宽高填充父窗体
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        // 给帧布局添加自定义进度条
        flProgress.addView(mPbProgress, params);

        mPbProgress.setProgress(50);
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
        this.mAppDetailBean = appDetailBean;
        refreshView(appDetailBean);
    }

    public AppDetailBean mAppDetailBean;

    private void refreshView(final AppDetailBean appDetailBean) {
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


        final ArrayList<String> screen = appDetailBean.screen;

        for (int i = 0; i < 5; i++) {
            if (i < screen.size()) {
                Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + screen.get(i)).error(R.drawable.ic_default).
                        placeholder(R.drawable.ic_default).into(mIvPics[i]);
            } else {
                mIvPics[i].setVisibility(View.GONE);
            }
        }


        mTvDes.setText(appDetailBean.des);
        mTvAuthor.setText(appDetailBean.author);

        // 放在消息队列中运行, 解决当只有三行描述时也是7行高度的bug
        mTvDes.post(new Runnable() {

            @Override
            public void run() {
                // 默认展示7行的高度
                int shortHeight = getShortHeight();
                mParams = (LinearLayout.LayoutParams) mTvDes.getLayoutParams();
                mParams.height = shortHeight;

                mTvDes.setLayoutParams(mParams);
            }
        });
    }

    protected void toggle2() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();

        ValueAnimator animator = null;
        if (isOpen) {
            // 关闭
            isOpen = false;
            if (longHeight > shortHeight) {// 只有描述信息大于7行,才启动动画
                animator = ValueAnimator.ofInt(longHeight, shortHeight);
            }
        } else {
            // 打开
            isOpen = true;
            if (longHeight > shortHeight) {// 只有描述信息大于7行,才启动动画
                animator = ValueAnimator.ofInt(shortHeight, longHeight);
            }
        }

        if (animator != null) {// 只有描述信息大于7行,才启动动画
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator arg0) {
                    Integer height = (Integer) arg0.getAnimatedValue();
                    mParams.height = height;
                    mTvDes.setLayoutParams(mParams);
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
                    // ScrollView要滑动到最底部
                    final ScrollView scrollView = getScrollView();

                    // 为了运行更加安全和稳定, 可以讲滑动到底部方法放在消息队列中执行
                    scrollView.post(new Runnable() {

                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);// 滚动到底部
                        }
                    });

                    if (isOpen) {
                        mIvArrow1.setImageResource(R.drawable.arrow_up);
                    } else {
                        mIvArrow1.setImageResource(R.drawable.arrow_down);
                    }

                }

                @Override
                public void onAnimationCancel(Animator arg0) {

                }
            });

            animator.setDuration(200);
            animator.start();
        }
    }

    /**
     * 获取7行textview的高度
     */
    private int getShortHeight() {
        // 模拟一个textview,设置最大行数为7行, 计算该虚拟textview的高度, 从而知道tvDes在展示7行时应该多高
        int width = mTvDes.getMeasuredWidth();// 宽度

        TextView view = new TextView(UIUtil.getContext());
        view.setText(mAppDetailBean.des);// 设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);// 文字大小一致
        view.setMaxLines(7);// 最大行数为7行

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);// 宽不变, 确定值, match_parent
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                View.MeasureSpec.AT_MOST);// 高度包裹内容, wrap_content;当包裹内容时,
        // 参1表示尺寸最大值,暂写2000, 也可以是屏幕高度

        // 开始测量
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();// 返回测量后的高度
    }

    /**
     * 获取完整textview的高度
     */
    private int getLongHeight() {
        // 模拟一个textview,设置最大行数为7行, 计算该虚拟textview的高度, 从而知道tvDes在展示7行时应该多高
        int width = mTvDes.getMeasuredWidth();// 宽度

        TextView view = new TextView(UIUtil.getContext());
        view.setText(mAppDetailBean.des);// 设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);// 文字大小一致
        // view.setMaxLines(7);// 最大行数为7行

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);// 宽不变, 确定值, match_parent
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                View.MeasureSpec.AT_MOST);// 高度包裹内容, wrap_content;当包裹内容时,
        // 参1表示尺寸最大值,暂写2000, 也可以是屏幕高度

        // 开始测量
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();// 返回测量后的高度
    }

    // 获取ScrollView, 一层一层往上找,
    // 知道找到ScrollView后才返回;注意:一定要保证父控件或祖宗控件有ScrollView,否则死循环
    private ScrollView getScrollView() {
        ViewParent parent = mTvDes.getParent();

        while (!(parent instanceof ScrollView)) {
            parent = parent.getParent();
        }

        return (ScrollView) parent;
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
