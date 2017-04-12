package com.pandaq.pandaeye.utils;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.BaseActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by PandaQ on 2017/3/3.
 * picasso 图片加载监听，并获取主题色
 */

public class PicassoTarget implements Target {
    private static final float SCRIM_ADJUSTMENT = 0.075f;
    private Activity mActivity;
    private ImageView mImageView;
    private CollapsingToolbarLayout mToolbarLayout;
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;

    public PicassoTarget(Activity activity, ImageView imageView, Toolbar toolbar) {
        this(activity, imageView, null, toolbar, null);
    }

    public PicassoTarget(Activity activity, ImageView imageView, @Nullable CollapsingToolbarLayout toolbarLayout, Toolbar toolbar, @Nullable FloatingActionButton fab) {
        mActivity = activity;
        mImageView = imageView;
        mToolbarLayout = toolbarLayout;
        mToolbar = toolbar;
        mFloatingActionButton = fab;
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
        mImageView.setImageBitmap(bitmap);
        final int twentyFourDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                24, mActivity.getResources().getDisplayMetrics());
        assert bitmap != null;
        Palette.from(bitmap)
                .maximumColorCount(16)
                .clearFilters()
                .setRegion(0, 0, bitmap.getWidth() - 1, twentyFourDip)
                .generate(new Palette.PaletteAsyncListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onGenerated(Palette palette) {
                        boolean isDark;
                        int lightness = ColorUtils.isDark(palette);
                        if (lightness == ColorUtils.LIGHTNESS_UNKNOWN) {
                            isDark = ColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
                        } else {
                            isDark = lightness == ColorUtils.IS_DARK;
                        }
                        // color the status bar. Set a complementary dark color on L,
                        // light or dark color on M (with matching status bar icons)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            int statusBarColor = mActivity.getWindow().getStatusBarColor();
                            final Palette.Swatch topColor = ColorUtils.getMostPopulousSwatch(palette);
                            if (topColor != null && (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                                statusBarColor = ColorUtils.scrimify(topColor.getRgb(), isDark, SCRIM_ADJUSTMENT);
                                // set a light status bar on M+
                                if (!isDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    ViewUtils.setLightStatusBar(mImageView);
                                }
                            }
                            if (statusBarColor != mActivity.getWindow().getStatusBarColor()) {
                                ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(
                                        mActivity.getWindow().getStatusBarColor(), statusBarColor);
                                statusBarColorAnim.addUpdateListener(new ValueAnimator
                                        .AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        mActivity.getWindow().setStatusBarColor((int) animation.getAnimatedValue());
                                        BaseActivity.currentStatusColor = (int) animation.getAnimatedValue();
                                    }
                                });
                                statusBarColorAnim.setDuration(500L);
                                statusBarColorAnim.setInterpolator(
                                        new AccelerateInterpolator());
                                statusBarColorAnim.start();
                                if (ColorUtils.compareColors(Color.WHITE, statusBarColor) > 0.8) {
                                    statusBarColor = ColorUtils.blendColors(statusBarColor, mActivity.getResources().getColor(R.color.black_000000), 0.2f);
                                }
                                if (mToolbarLayout != null) {
                                    mToolbarLayout.setContentScrimColor(statusBarColor);
                                    mToolbar.setBackgroundColor(ColorUtils.modifyAlpha(statusBarColor, 0.5f));
                                } else { //无toolbarlayout时保持toolbar颜色与状态栏一致
                                    mToolbar.setBackgroundColor(ColorUtils.modifyAlpha(statusBarColor, 0.8f));
                                }
                                if (mFloatingActionButton != null) {
                                    mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(statusBarColor));
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }


}

