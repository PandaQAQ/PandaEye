package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaeye.entity.NetEasyNews.TopNewsContent;

/**
 * Created by PandaQ on 2016/10/10.
 * email : 767807368@qq.com
 */

public interface ITopNewsInfoActivity {
    void showProgressBar();

    void hideProgressBar();

    void loadTopNewsInfo();

    void loadFail(String errmsg);

    void loadSuccess(TopNewsContent topNewsContent);
}
