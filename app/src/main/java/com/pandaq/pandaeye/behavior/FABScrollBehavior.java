package com.pandaq.pandaeye.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;

import com.pandaq.pandaeye.utils.AnimUtils;

/**
 * Created by huxinyu on 2016/8/30.
 * 自定义behavior
 */
public class FABScrollBehavior extends FloatingActionButton.Behavior {

    public FABScrollBehavior() {
    }

    // 隐藏动画是否正在执行
    private boolean isAnimatingOut = false;
    private OnStateChangedListener mOnStateChangedListener;

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        // 确保是竖直判断的滚动
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimatingOut
                && child.getVisibility() == View.VISIBLE) {// 手指上滑，隐藏FAB
            AnimUtils.scaleHide(child, listener);
            if (mOnStateChangedListener != null) {
                mOnStateChangedListener.onChanged(false);
            }
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && child.getVisibility() != View.VISIBLE) {
            AnimUtils.scaleShow(child, null);// 手指下滑，显示FAB
            if (mOnStateChangedListener != null) {
                mOnStateChangedListener.onChanged(true);
            }
        }
    }

    private ViewPropertyAnimatorListener listener = new ViewPropertyAnimatorListener() {
        @Override
        public void onAnimationStart(View view) {
            isAnimatingOut = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimatingOut = false;
            view.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationCancel(View arg0) {
            isAnimatingOut = false;
        }
    };

    // 外部监听显示和隐藏。
    public interface OnStateChangedListener {
        void onChanged(boolean isShow);
    }

    //设置监听
    public void setOnStateChangedListener(OnStateChangedListener mOnStateChangedListener) {
        this.mOnStateChangedListener = mOnStateChangedListener;
    }
}
