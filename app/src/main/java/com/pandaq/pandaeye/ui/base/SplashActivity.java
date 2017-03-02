package com.pandaq.pandaeye.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.pandaq.pandaeye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2017/2/21.
 * 闪屏页，优化启动体验
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_splash_image)
    ImageView mIvSplashImage;
    private boolean wheelRunning = false;
    private int wheelProgress = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
//        RotateAnimation animation = new RotateAnimation(this)
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(1000);
        mIvSplashImage.startAnimation(animation);
        animation.setAnimationListener(new AnimationImpl());
    }

    private class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }
}
