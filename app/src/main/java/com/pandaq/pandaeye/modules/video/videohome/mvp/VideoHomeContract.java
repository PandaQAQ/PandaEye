package com.pandaq.pandaeye.modules.video.videohome.mvp;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface VideoHomeContract {
    interface View {
        void refreshData();

        void refreshSuccess(ArrayList<RetDataBean.ListBean> listBeen);

        void refreshFail(String errCode, String errMsg);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {
        void loadData();

        void loadCache();
    }
}
