package com.pandaq.pandaeye.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 所有的Presenter都继承自此Presenter
 */
public class BasePresenter {

    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null||mCompositeSubscription.isUnsubscribed()) { //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    //在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
    public void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
