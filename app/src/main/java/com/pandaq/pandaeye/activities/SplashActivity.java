package com.pandaq.pandaeye.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.BaseActivity;
import com.pandaq.pandaeye.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2017/2/21.
 * 闪屏页，优化启动体验
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_splash_image)
    ImageView mIvSplashImage;
    @BindView(R.id.iv_background)
    ImageView mIvBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        String[] images = getResources().getStringArray(R.array.splash_background);
        int position = new Random().nextInt(images.length - 1) % (images.length);
        Picasso.with(this)
                .load(images[position])
                .into(mIvBackground);
        Picasso.with(this)
                .load("file://" + ViewUtils.getAppFile(this, "images/user.png"))
                .error(getResources().getDrawable(R.drawable.userimage))
                .into(mIvSplashImage);
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
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

}
