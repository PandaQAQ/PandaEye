package com.pandaq.pandaqlib.loopbander;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 */
public interface IndicatorParentImbl {

    void setIndicatorListener(IndicatorListener indicatorListener);

    int getIndicatorCount();

    void startAutoScroll();

    void stopAutoScroll();

    void onReDraw(int index);
}
