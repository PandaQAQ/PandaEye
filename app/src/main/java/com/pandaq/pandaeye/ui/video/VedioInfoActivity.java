package com.pandaq.pandaeye.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.adapters.ViewPagerAdapter;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.entity.video.CommentBean;
import com.pandaq.pandaeye.entity.video.MovieInfo;
import com.pandaq.pandaeye.presenter.video.VideoInfoPresenter;
import com.pandaq.pandaeye.ui.ImplView.IVedioInfoActivity;
import com.pandaq.pandaeye.ui.base.BaseActivity;
import com.pandaq.pandaeye.widget.viewpager.ViewPagerIndicator;
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

public class VedioInfoActivity extends BaseActivity implements IVedioInfoActivity {
    @BindView(R.id.jc_video_player)
    JCVideoPlayerStandard mJcVideoPlayer;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vp_video_info)
    ViewPager mVpVideoInfo;
    @BindView(R.id.vpi_video_info)
    ViewPagerIndicator mVpiVideoInfo;
    private String dataId = "";
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
                .into(mJcVideoPlayer.thumbImageView);
        mJcVideoPlayer.onClick(mJcVideoPlayer.thumbImageView);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new VideoInfoFragment());
        fragments.add(new VideoCommentFrag());
        mVpVideoInfo.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        mVpVideoInfo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mVpiVideoInfo.move(position, positionOffset); //position当前fragment，offset滑动值0-1
            }

            @Override
            public void onPageSelected(int position) {
                mVpiVideoInfo.selectText(position);//textview选中效果
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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

    }

    @Override
    public void loadVideoInfoFail(String errCode, String errMsg) {

    }

    @Override
    public void refreshCommentSuccess(ArrayList<CommentBean.ListBean> commentBean) {

    }

    @Override
    public void refreshCommentFail(String errCode, String errMsg) {

    }

    @Override
    public void loadCommentSuccess(ArrayList<CommentBean.ListBean> commentBean) {

    }

    @Override
    public void loadCommentFail(String errCode, String errMsg) {

    }

    @Override
    public void noMoreComment() {

    }
}
