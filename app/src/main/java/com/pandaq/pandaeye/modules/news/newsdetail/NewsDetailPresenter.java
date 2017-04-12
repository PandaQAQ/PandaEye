package com.pandaq.pandaeye.modules.news.newsdetail;

import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.BasePresenter;
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

public class NewsDetailPresenter extends BasePresenter implements NewsDetailContract.Presenter {
    private NewsDetailContract.View mActivity;

    public NewsDetailPresenter(NewsDetailContract.View activity) {
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
                .map(new Function<ResponseBody, NewsContent>() {
                    @Override
                    public NewsContent apply(ResponseBody responseBody) {
                        NewsContent topNews = null;
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
                .subscribe(new Observer<NewsContent>() {
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
                    public void onNext(NewsContent response) {
                        mActivity.hideProgressBar();
                        mActivity.loadSuccess(response);
                    }
                });

    }

}
