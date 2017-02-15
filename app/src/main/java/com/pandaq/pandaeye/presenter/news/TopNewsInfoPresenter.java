package com.pandaq.pandaeye.presenter.news;

import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.entity.NetEasyNews.TopNewsContent;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.ITopNewsInfoActivity;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by PandaQ on 2016/10/10.
 * email : 767807368@qq.com
 */

public class TopNewsInfoPresenter extends BasePresenter {
    private ITopNewsInfoActivity mActivity;

    public TopNewsInfoPresenter(ITopNewsInfoActivity activity) {
        mActivity = activity;
    }

    public void loadNewsContent(String id) {
        Subscription subscription = ApiManager.getInstence()
                .getTopNewsServie()
                .getNewsContent(id)
                .subscribe(new Subscriber<TopNewsContent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TopNewsContent content) {

                    }
                });
        addSubscription(subscription);
    }
}
