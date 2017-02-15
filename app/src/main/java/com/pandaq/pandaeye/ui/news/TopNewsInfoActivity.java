package com.pandaq.pandaeye.ui.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.entity.NetEasyNews.TopNewsContent;
import com.pandaq.pandaeye.presenter.news.TopNewsInfoPresenter;
import com.pandaq.pandaeye.ui.ImplView.ITopNewsInfoActivity;
import com.pandaq.pandaeye.ui.base.BaseActivity;
import com.pandaq.pandaeye.utils.DensityUtil;
import com.pandaq.pandaeye.widget.FiveThreeImageView;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopNewsInfoActivity extends BaseActivity implements ITopNewsInfoActivity {

    @BindView(R.id.news_img)
    FiveThreeImageView mNewsImg;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.wv_topnews_content)
    WebView mWvTopnewsContent;
    @BindView(R.id.cl_topnews_content_parent)
    CoordinatorLayout mClTopnewsContentParent;
    private int width;
    private int heigh;
    private TopNewsInfoPresenter mPresenter = new TopNewsInfoPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news_info);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        int[] deviceInfo = DensityUtil.getDeviceInfo(this);
        width = deviceInfo[0];
        heigh = width * 3 / 5;
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        String news_id = bundle.getString(Constants.BUNDLE_KEY_ID);
        String newsImg = bundle.getString(Constants.BUNDLE_KEY_IMG_URL);
        loadTopNewsInfo(news_id);
        Glide.with(this)
                .load(R.drawable.userimage)
//                .listener(new ZhihuStoryInfoActivity.GlideRequestListener())
                .override(width, heigh)
                .crossFade()
                .into(mNewsImg);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void loadTopNewsInfo(String news_id) {
        mPresenter.loadNewsContent(news_id);
    }

    @Override
    public void loadFail(String errmsg) {
        showSnackBar(mClTopnewsContentParent, errmsg, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void loadSuccess(TopNewsContent topNewsContent) {
        topNewsContent.getBody();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubcription();
    }
}
