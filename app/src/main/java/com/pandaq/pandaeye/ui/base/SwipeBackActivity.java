package com.pandaq.pandaeye.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.pandaq.pandaeye.utils.SwipeBackController;

/**
 * Created by PandaQ on 2017/3/20.
 * 所有侧滑返回的activity的父类
 */

public abstract class SwipeBackActivity extends BaseActivity {
    private SwipeBackController swipeBackController;
    private boolean swipeBack = true;
    private float down_x;
    private float down_y;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //横向滑动时交给 swipeBackController 处理
        if (swipeBackController != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    down_x = ev.getX();
                    down_y = ev.getY();
                    swipeBack = ev.getX() < swipeBackController.getTouchWidth();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = Math.abs(ev.getX() - down_x);
                    float y = Math.abs(ev.getY() - down_y);
                    swipeBack = y < x;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            if (swipeBack) {
                return swipeBackController.processEvent(ev);
            } else {
                return super.dispatchTouchEvent(ev);
            }
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    protected void setViewActivity(SwipeBackActivity swipeBackActivity) {
        swipeBackController = new SwipeBackController(swipeBackActivity);
    }

}
