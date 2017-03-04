package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaeye.entity.zhihu.ZhiHuDaily;
import com.pandaq.pandaeye.entity.zhihu.ZhiHuStory;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/13.
 * email : 767807368@qq.com
 */
public interface IZhiHuDailyFrag {

    void showRefreshBar();

    void hideRefreshBar();

    void refreshData();

    void refreshSuccessed(ZhiHuDaily stories);

    void refreshFail(String errMsg);

    void loadMoreData();

    void loadSuccessed(ArrayList<BaseItem> stories);

    void loadFail(String errMsg);
}
