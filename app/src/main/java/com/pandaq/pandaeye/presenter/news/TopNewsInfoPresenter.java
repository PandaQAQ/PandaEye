package com.pandaq.pandaeye.presenter.news;

import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.entity.NetEasyNews.TopNewsContent;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.ITopNewsInfoActivity;
import com.pandaq.pandaeye.utils.JsonUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by PandaQ on 2016/10/10.
 * email : 767807368@qq.com
 */

public class TopNewsInfoPresenter extends BasePresenter {
    private ITopNewsInfoActivity mActivity;

    public TopNewsInfoPresenter(ITopNewsInfoActivity activity) {
        mActivity = activity;
    }

    /**
     * 加载新闻详情
     *
     * @param id
     */
    public void loadNewsContent(final String id) {
        mActivity.showProgressBar();
        Subscription subscription = ApiManager.getInstence()
                .getTopNewsServie()
                .getNewsContent(id)
                .map(new Func1<ResponseBody, TopNewsContent>() {
                    @Override
                    public TopNewsContent call(ResponseBody responseBody) {
                        TopNewsContent topNews = null;
                        try {
                            topNews = JsonUtils.readJsonNewsContent(responseBody.string(), id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return topNews;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopNewsContent>() {
                    @Override
                    public void onCompleted() {
                        mActivity.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.hideProgressBar();
                        mActivity.loadFail(e.getMessage());
                    }

                    @Override
                    public void onNext(TopNewsContent response) {
                        mActivity.hideProgressBar();
                        mActivity.loadSuccess(response);
                    }
                });
        addSubscription(subscription);
    }

}
