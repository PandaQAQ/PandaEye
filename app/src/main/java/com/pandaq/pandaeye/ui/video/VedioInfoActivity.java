package com.pandaq.pandaeye.ui.video;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.model.video.MovieInfo;
import com.pandaq.pandaeye.presenter.video.VideoInfoPresenter;
import com.pandaq.pandaeye.ui.ImplView.IVedioInfoActivity;
import com.pandaq.pandaeye.ui.base.BaseActivity;
import com.pandaq.pandaeye.utils.PicassoTarget;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by PandaQ on 2017/2/28.
 * 视频详情界面
 */

public class VedioInfoActivity extends BaseActivity implements IVedioInfoActivity, ViewPager.OnPageChangeListener {
    @BindView(R.id.jc_video_player)
    JCVideoPlayerStandard mJcVideoPlayer;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vp_video_info)
    ViewPager mVpVideoInfo;
    @BindView(R.id.tv_tab_description)
    TextView mTvTabDescription;
    @BindView(R.id.tv_tab_comment)
    TextView mTvTabComment;
    private String dataId = "";
    private ArgbEvaluator argbEvaluator;
    private FloatEvaluator floatEvaluator;
    private VideoInfoPresenter mPresenter = new VideoInfoPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedioinfo);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        initView();
        initData();
    }

    private void initData() {
        mPresenter.loadVideoInfo();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(Constants.BUNDLE_KEY_TITLE);
        dataId = bundle.getString(Constants.BUNDLE_KEY_ID);
        String pic = bundle.getString(Constants.BUNDLE_KEY_IMG_URL);
        mToolbarTitle.setText(title);
        mJcVideoPlayer.backButton.setVisibility(View.GONE);
        mJcVideoPlayer.titleTextView.setVisibility(View.GONE);
        mJcVideoPlayer.tinyBackImageView.setVisibility(View.GONE);
        mJcVideoPlayer.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(this)
                .load(pic)
                .into(new PicassoTarget(this, mJcVideoPlayer.thumbImageView, mToolbar));
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new VideoInfoFragment());
        fragments.add(new VideoCommentFrag());
        mVpVideoInfo.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        mVpVideoInfo.addOnPageChangeListener(this);
        mVpVideoInfo.setCurrentItem(0);
        argbEvaluator = new ArgbEvaluator();
        floatEvaluator = new FloatEvaluator();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public String getDataId() {
        return dataId;
    }

    @Override
    public void loadVideoInfoSuccess(MovieInfo info) {
        mJcVideoPlayer.setUp(info.getVideoUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, info.getTitle());
    }

    @Override
    public void loadVideoInfoFail(String errCode, String errMsg) {
        showSnackBar(mVpVideoInfo, errMsg, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        changePage(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void changePage(int position, float positionOffset) {
        if (position == 0) {
            // 字体颜色
            mTvTabDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            int stepsColor = (int) argbEvaluator.evaluate(positionOffset, ContextCompat.getColor(this, R.color.colorPrimary),
                    ContextCompat.getColor(this, R.color.white_FFFFFF));
            mTvTabDescription.setTextColor(stepsColor);
            mTvTabComment.setTextColor(ContextCompat.getColor(this, R.color.white_FFFFFF));
            int sleepColor = (int) argbEvaluator.evaluate(positionOffset, ContextCompat.getColor(this, R.color.white_FFFFFF),
                    ContextCompat.getColor(this, R.color.colorPrimary));
            mTvTabComment.setTextColor(sleepColor);
            // 字体大小
            mTvTabDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            float stepsSize = floatEvaluator.evaluate(positionOffset, 18, 14);
            mTvTabDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, stepsSize);
            mTvTabComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            float sleepSize = floatEvaluator.evaluate(positionOffset, 14, 18);
            mTvTabComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, sleepSize);
        } else {
            // 字体颜色
            mTvTabDescription.setTextColor(ContextCompat.getColor(this, R.color.white_FFFFFF));
            int stepsColor = (int) argbEvaluator.evaluate(positionOffset, ContextCompat.getColor(this, R.color.white_FFFFFF),
                    ContextCompat.getColor(this, R.color.colorPrimary));
            mTvTabDescription.setTextColor(stepsColor);
            mTvTabComment.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            int sleepColor = (int) argbEvaluator.evaluate(positionOffset, ContextCompat.getColor(this, R.color.colorPrimary),
                    ContextCompat.getColor(this, R.color.white_FFFFFF));
            mTvTabComment.setTextColor(sleepColor);
            // 字体大小
            mTvTabDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            float stepsSize = floatEvaluator.evaluate(positionOffset, 14, 18);
            mTvTabDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, stepsSize);
            mTvTabComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            float sleepSize = floatEvaluator.evaluate(positionOffset, 18, 14);
            mTvTabComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, sleepSize);
        }
    }
}
