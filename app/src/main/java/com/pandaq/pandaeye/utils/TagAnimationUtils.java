package com.pandaq.pandaeye.utils;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by PandaQ on 2017/3/24.
 * 控制知乎list滑动标签显示隐藏的动画工具类
 */

public class TagAnimationUtils {
    private static final String TAG = TagAnimationUtils.class.getSimpleName();

    /**
     * 从控件顶部移动到控件开始的位置
     */
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

    /**
     * 从控件开始的位置移动到空间顶部
     */
    public static TranslateAnimation moveToViewTop() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

}
