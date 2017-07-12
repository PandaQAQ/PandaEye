package com.pandaq.pandaeye.modules.setting.aboutme;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

interface AboutMeContract {
    interface View extends ImpBaseView{
        void loadMyInfo();

        void showMyInfo(UserInfo myInfo);

        void loadMyInfoFail();
    }

    interface Presenter extends ImpBasePresenter{
        void loadInfo(String user);
    }
}
