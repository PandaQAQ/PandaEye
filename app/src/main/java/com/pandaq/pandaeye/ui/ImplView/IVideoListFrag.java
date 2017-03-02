package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaeye.entity.movie.RetDataBean;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/3/1.
 * email : 767807368@qq.com
 * 视频列表界面的绑定接口
 */

public interface IVideoListFrag {

    void refreshData();

    void refreshSuccess(ArrayList<RetDataBean.ListBean> listBeen);

    void refreshFail(String errCode, String errMsg);

    void showProgressBar();

    void hideProgressBar();
}
