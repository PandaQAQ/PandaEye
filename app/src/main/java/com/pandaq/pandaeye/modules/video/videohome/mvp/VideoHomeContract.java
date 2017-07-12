package com.pandaq.pandaeye.modules.video.videohome.mvp;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

interface VideoHomeContract {
    interface View extends ImpBaseView {
        void refreshData();

        void refreshSuccess(ArrayList<RetDataBean.ListBean> listBeen);

        void refreshFail(String errCode, String errMsg);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter extends ImpBasePresenter {
        void loadData();

        void loadCache();
    }
}
