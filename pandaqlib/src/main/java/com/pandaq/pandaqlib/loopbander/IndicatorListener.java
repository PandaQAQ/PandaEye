package com.pandaq.pandaqlib.loopbander;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 */
public interface IndicatorListener {

    void moving(int position, float dis);

    void notifyDateChanged(int count);


}
