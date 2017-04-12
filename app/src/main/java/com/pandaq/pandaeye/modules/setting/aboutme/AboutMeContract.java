package com.pandaq.pandaeye.modules.setting.aboutme;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface AboutMeContract {
    interface View {
        void loadMyInfo();

        void showMyInfo(UserInfo myInfo);

        void loadMyInfoFail();
    }

    interface Presenter {
        void loadInfo(String user);
    }
}
