package com.pandaq.pandaeye.activities;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.BaseActivity;
import com.pandaq.pandaqlib.SwipeBackLayout;

/**
 * Created by PandaQ on 2017/3/20.
 * 所有侧滑返回的activity的父类
 */

public class SwipeBackActivity extends BaseActivity implements SwipeBackLayout.SwipeListener {
    protected SwipeBackLayout layout;
    private ArgbEvaluator argbEvaluator;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback_base, null);
        layout.attachToActivity(this);
        argbEvaluator = new ArgbEvaluator();
        layout.addSwipeListener(this);
        if (Build.VERSION.SDK_INT >= 23) {
            currentStatusColor = getResources().getColor(R.color.colorPrimaryDark, null);
        } else {
            currentStatusColor = getResources().getColor(R.color.colorPrimaryDark);
        }
    }

    public void addViewPager(ViewPager pager) {
        layout.addViewPager(pager);
    }

    @Override
    public void swipeValue(double value) {
        int statusColor = (int) argbEvaluator.evaluate((float) value, currentStatusColor,
                ContextCompat.getColor(this, R.color.transparent_00ffffff));
        getWindow().setStatusBarColor(statusColor);
    }

}
