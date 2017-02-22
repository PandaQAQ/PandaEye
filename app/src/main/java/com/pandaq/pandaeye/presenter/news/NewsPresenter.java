package com.pandaq.pandaeye.presenter.news;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.disklrucache.DiskCacheManager;
import com.pandaq.pandaeye.entity.NetEasyNews.TopNews;
import com.pandaq.pandaeye.entity.NetEasyNews.TopNewsList;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.INewsListFrag;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by PandaQ on 2016/9/22.
 * email : 767807368@qq.com
 */

public class NewsPresenter extends BasePresenter {

    private INewsListFrag mNewsListFrag;
    private int currentIndex;

    public NewsPresenter(INewsListFrag newsListFrag) {
        this.mNewsListFrag = newsListFrag;
    }

    public void refreshNews() {
        mNewsListFrag.showRefreshBar();
        currentIndex = 0;
        Subscription subscription = ApiManager.getInstence().getTopNewsServie()
                .getTopNews("T1348647909107", currentIndex + "")
                .map(new Func1<TopNewsList, ArrayList<TopNews>>() {
                    @Override
                    public ArrayList<TopNews> call(TopNewsList topNewsList) {
                        return topNewsList.getTopNewsArrayList();
                    }
                })
                .flatMap(new Func1<ArrayList<TopNews>, Observable<TopNews>>() {
                    @Override
                    public Observable<TopNews> call(ArrayList<TopNews> topNewses) {
                        return Observable.from(topNewses);
                    }
                })
                .filter(new Func1<TopNews, Boolean>() {
                    @Override
                    public Boolean call(TopNews topNews) {
                        return topNews.getUrl() != null;
                    }
                })
                .toList()
                //将 List 转为ArrayList 缓存存储 ArrayList Serializable对象
                .map(new Func1<List<TopNews>, ArrayList<TopNews>>() {
                    @Override
                    public ArrayList<TopNews> call(List<TopNews> topNewses) {
                        ArrayList<TopNews> news = new ArrayList<>();
                        news.addAll(topNewses);
                        return news;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<TopNews>>() {
                    @Override
                    public void onCompleted() {
                        mNewsListFrag.hideRefreshBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsListFrag.hideRefreshBar();
                        mNewsListFrag.refreshNewsFail(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<TopNews> topNewses) {
                        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_TOPNEWS_FILE);
                        manager.put(Constants.CACHE_TOPNEWS, topNewses);
                        currentIndex += 20;
                        mNewsListFrag.refreshNewsSuccessed(topNewses);
                    }
                });
        addSubscription(subscription);
    }

    //两个方法没区别,只是刷新会重新赋值
    public void loadMore() {
        Subscription subscription = ApiManager.getInstence().getTopNewsServie()
                .getTopNews("T1348647909107", currentIndex + "")
                .map(new Func1<TopNewsList, ArrayList<TopNews>>() {
                    @Override
                    public ArrayList<TopNews> call(TopNewsList topNewsList) {
                        return topNewsList.getTopNewsArrayList();
                    }
                })
                .flatMap(new Func1<ArrayList<TopNews>, Observable<TopNews>>() {
                    @Override
                    public Observable<TopNews> call(ArrayList<TopNews> topNewses) {
                        return Observable.from(topNewses);
                    }
                })
                .filter(new Func1<TopNews, Boolean>() {
                    @Override
                    public Boolean call(TopNews topNews) {
                        return topNews.getUrl() != null;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TopNews>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsListFrag.loadMoreFail(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopNews> topNews) {
                        //每刷新成功一次多加载20条
                        currentIndex += 20;
                        mNewsListFrag.loadMoreSuccessed((ArrayList<TopNews>) topNews);
                    }
                });
        addSubscription(subscription);
    }

    /**
     * 读取缓存
     */
    public void loadCache() {
        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_TOPNEWS_FILE);
        ArrayList<TopNews> topNews = manager.getSerializable(Constants.CACHE_TOPNEWS);
        if (topNews != null) {
            mNewsListFrag.refreshNewsSuccessed(topNews);
        }
    }
}
