package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/22.
 * email : 767807368@qq.com
 */

public interface INewsListFrag {

    void showRefreshBar();

    void hideRefreshBar();

    void refreshNews();

    void refreshNewsFail(String errorMsg);

    void refreshNewsSuccessed(ArrayList<BaseItem> topNews);

    void loadMoreNews();

    void loadMoreFail(String errorMsg);

    void loadMoreSuccessed(ArrayList<BaseItem> topNewses);

    void loadAll();
}
