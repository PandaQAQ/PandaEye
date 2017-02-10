package com.pandaq.pandaeye.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by huxinyu on 2016/8/30.
 * 自定义behavior
 */
public class FABScrollBehavior extends FloatingActionButton.Behavior {
    public FABScrollBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        // 确保是竖直判断的滚动
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target,
                               final int dxConsumed, final int dyConsumed, final int dxUnconsumed, final int dyUnconsumed) {
//        if ((dyUnconsumed > 0 || dxUnconsumed > 0) && child.isShown()) {
//            child.hide();
//        } else if ((dyUnconsumed < 0 || dxUnconsumed < 0) && !child.isShown()) {
//            child.show();
//        }

    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dx, int dy, int[] consumed) {
//        Log.i("dy", dxConsumed + "");
        Log.i("dy1", dy + "");
        Log.i("dy2", child.getVisibility() + "");

        if (dy >= 0 && child.getVisibility() == View.VISIBLE) {// 手指上滑，隐藏FAB
            child.hide();
        } else if (dy <= 0 && child.getVisibility() != View.VISIBLE) {
            child.show();// 手指下滑，显示FAB
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}
