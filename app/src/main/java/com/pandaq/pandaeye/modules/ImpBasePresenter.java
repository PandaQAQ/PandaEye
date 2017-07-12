package com.pandaq.pandaeye.modules;

/**
 * Created by PandaQ on 2017/7/12.
 * Presenter 基础接口，用于初始化和释放资源避免引起内存泄漏
 */

public interface ImpBasePresenter {
    void bindView(ImpBaseView view);

    void unbindView();

    void onDestory();
}
