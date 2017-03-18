package com.pandaq.pandaeye.presenter.news;

import com.pandaq.pandaeye.model.api.ApiManager;
import com.pandaq.pandaeye.model.neteasynews.TopNewsContent;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.ITopNewsInfoActivity;
import com.pandaq.pandaeye.utils.JsonUtils;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

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
        ApiManager.getInstence()
                .getTopNewsServie()
                .getNewsContent(id)
                .map(new Function<ResponseBody, TopNewsContent>() {
                    @Override
                    public TopNewsContent apply(ResponseBody responseBody) {
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
                .subscribe(new Observer<TopNewsContent>() {
                    @Override
                    public void onComplete() {
                        mActivity.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.hideProgressBar();
                        mActivity.loadFail(e.getMessage());
                    }


                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(TopNewsContent response) {
                        mActivity.hideProgressBar();
                        mActivity.loadSuccess(response);
                    }
                });

    }

}
