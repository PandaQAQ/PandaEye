package com.pandaq.pandaeye.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by PandaQ on 2017/2/28.
 * 视频详情界面
 */

public class VedioInfoActivity extends BaseActivity {
    @BindView(R.id.jc_video_player)
    JCVideoPlayerStandard mJcVideoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedioinfo);
        ButterKnife.bind(this);
//        mJcVideoPlayer.setUp();
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
}
