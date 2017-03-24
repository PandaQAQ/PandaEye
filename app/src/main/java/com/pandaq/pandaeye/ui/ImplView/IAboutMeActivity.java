package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaeye.model.github.UserInfo;

/**
 * Created by PandaQ on 2017/3/24.
 * 关于我界面接口
 */

public interface IAboutMeActivity {
    void loadMyInfo();

    void showMyInfo(UserInfo myInfo);

    void loadMyInfoFail();
}
