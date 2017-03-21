package com.pandaq.pandaeye.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by PandaQ on 2017/3/20.
 * 优化返回控制器
 */
public class SwipeBackController {
    private static final String TAG = SwipeBackController.class.getSimpleName();
    private static final int ANIMATION_DURATION = 300;//默认动画时间
    private static final int DEFAULT_TOUCH_THRESHOLD = 80;//默认开始滑动的位置距离左边缘的距离
    private int mScreenWidth;
    private int mTouchSlop;

    private boolean isMoving = false;
    private float mInitX;
    private float mInitY;

    private ViewGroup contentView;//content布局
    private ViewGroup userView;//用户添加的布局

    private ArgbEvaluator evaluator;
    private ValueAnimator mAnimator;
    private VelocityTracker mVelTracker;

    public SwipeBackController(final Activity activity) {
        mScreenWidth = activity.getResources().getDisplayMetrics().widthPixels;
        mTouchSlop = ViewConfiguration.get(activity).getScaledTouchSlop();
        evaluator = new ArgbEvaluator();

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.setBackground(new ColorDrawable(Color.parseColor("#00ffffff")));
        contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        userView = (ViewGroup) contentView.getChildAt(0);

        mAnimator = new ValueAnimator();
        mAnimator.setDuration(ANIMATION_DURATION);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int x = (Integer) valueAnimator.getAnimatedValue();
                if (x >= mScreenWidth) {
                    activity.finish();
                }
                handleView(x);
                handleBackgroundColor(x);
            }
        });
    }

    private void handleView(int x) {
        userView.setTranslationX(x);
    }

    /**
     * 控制背景颜色和透明度
     */
    private void handleBackgroundColor(float x) {
        int colorValue = (int) evaluator.evaluate(x / mScreenWidth,
                Color.parseColor("#dd000000"), Color.parseColor("#00000000"));
        contentView.setBackgroundColor(colorValue);
        Log.d(TAG, "x is " + x);
    }

    public boolean processEvent(MotionEvent event) {
        getVelocityTracker(event);
        if (mAnimator.isRunning()) {
            return true;
        }
        int pointId = -1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitX = event.getRawX();
                mInitY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isMoving) {
                    float dx = Math.abs(event.getRawX() - mInitX);
                    float dy = Math.abs(event.getRawY() - mInitY);
                    if (dx > mTouchSlop && dx > dy && mInitX < DEFAULT_TOUCH_THRESHOLD) {
                        isMoving = true;
                    }
                }
                if (isMoving) {
                    handleView((int) event.getRawX());
                    handleBackgroundColor(event.getRawX());
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                int distance = (int) (event.getRawX() - mInitX);

                mVelTracker.computeCurrentVelocity(1000);
                //获取x方向上的速度
                float velocityX = mVelTracker.getXVelocity(pointId);

                Log.d(TAG, "mVelocityX is " + velocityX);
                if (isMoving && Math.abs(userView.getTranslationX()) >= 0) {
                    if (velocityX > 1000f || distance >= mScreenWidth / 4) {
                        mAnimator.setIntValues((int) event.getRawX(), mScreenWidth);
                    } else {
                        mAnimator.setIntValues((int) event.getRawX(), 0);
                    }
                    mAnimator.start();
                    isMoving = false;
                }

                mInitX = 0;
                mInitY = 0;

                recycleVelocityTracker();
                break;
        }
        return true;
    }

    /**
     * 获取速度追踪器
     */
    private VelocityTracker getVelocityTracker(MotionEvent event) {
        if (mVelTracker == null) {
            mVelTracker = VelocityTracker.obtain();
        }
        mVelTracker.addMovement(event);
        return mVelTracker;
    }

    /**
     * 回收速度追踪器
     */
    private void recycleVelocityTracker() {
        if (mVelTracker != null) {
            mVelTracker.clear();
            mVelTracker.recycle();
            mVelTracker = null;
        }
    }

    public int getTouchWidth() {
        return DEFAULT_TOUCH_THRESHOLD;
    }
}
