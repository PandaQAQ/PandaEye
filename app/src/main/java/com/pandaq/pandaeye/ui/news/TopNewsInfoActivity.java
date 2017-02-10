package com.pandaq.pandaeye.ui.news;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.entity.NetEasyNews.TopNewsContent;
import com.pandaq.pandaeye.utils.DensityUtil;
import com.pandaq.pandaeye.ui.ImplView.ITopNewsInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopNewsInfoActivity extends AppCompatActivity implements ITopNewsInfoActivity {

    @BindView(R.id.news_img)
    ImageView mNewsImg;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private String news_id;
    private String news_img;
    int[] mDeviceInfo;
    int width;
    int heigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news_info);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initData();
    }

    private void initView() {
        mDeviceInfo = DensityUtil.getDeviceInfo(this);
        width = mDeviceInfo[0];
        heigh = width*3/ 4;
        System.out.println(width + "----" + heigh);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mNewsImg.getHeight() - mToolbar.getHeight() - getstatuBarHeight());
            }
        });
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        news_id = bundle.getInt("id") + "";
        news_img = bundle.getString("img_url");
        Glide.with(this)
                .load(news_img)
                .centerCrop()
                .override(width, heigh)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mNewsImg);
        loadTopNewsInfo();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void loadTopNewsInfo() {

    }

    @Override
    public void loadFail(String errmsg) {

    }

    @Override
    public void loadSuccess(TopNewsContent topNewsContent) {

    }

    private int getstatuBarHeight() {
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }
}
