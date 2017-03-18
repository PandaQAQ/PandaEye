package com.pandaq.pandaeye.presenter.news;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.disklrucache.DiskCacheManager;
import com.pandaq.pandaeye.model.api.ApiManager;
import com.pandaq.pandaeye.model.neteasynews.TopNews;
import com.pandaq.pandaeye.model.neteasynews.TopNewsList;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.INewsListFrag;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


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
        ApiManager.getInstence().getTopNewsServie()
                .getTopNews("T1348647909107", currentIndex + "")
                .map(new Function<TopNewsList, ArrayList<TopNews>>() {
                    @Override
                    public ArrayList<TopNews> apply(TopNewsList topNewsList) {
                        return topNewsList.getTopNewsArrayList();
                    }
                })
                .flatMap(new Function<ArrayList<TopNews>, Observable<TopNews>>() {
                    @Override
                    public Observable<TopNews> apply(ArrayList<TopNews> topNewses) throws Exception {
                        return Observable.fromIterable(topNewses);
                    }
                })
                .filter(new Predicate<TopNews>() {
                    @Override
                    public boolean test(TopNews topNews) throws Exception {
                        return topNews.getUrl() != null;
                    }
                })
                .map(new Function<TopNews, BaseItem>() {
                    @Override
                    public BaseItem apply(TopNews topNews) {
                        BaseItem<TopNews> baseItem = new BaseItem<>();
                        baseItem.setData(topNews);
                        return baseItem;
                    }
                })
                .toList()
                //将 List 转为ArrayList 缓存存储 ArrayList Serializable对象
                .map(new Function<List<BaseItem>, ArrayList<BaseItem>>() {
                    @Override
                    public ArrayList<BaseItem> apply(List<BaseItem> baseItems) {
                        ArrayList<BaseItem> items = new ArrayList<>();
                        items.addAll(baseItems);
                        return items;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<BaseItem>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(ArrayList<BaseItem> value) {
                        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_TOPNEWS_FILE);
                        manager.put(Constants.CACHE_TOPNEWS, value);
                        currentIndex += 20;
                        mNewsListFrag.hideRefreshBar();
                        mNewsListFrag.refreshNewsSuccessed(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsListFrag.hideRefreshBar();
                        mNewsListFrag.refreshNewsFail(e.getMessage());
                    }

                });
    }

    //两个方法没区别,只是刷新会重新赋值
    public void loadMore() {
        ApiManager.getInstence().getTopNewsServie()
                .getTopNews("T1348647909107", currentIndex + "")
                .map(new Function<TopNewsList, ArrayList<TopNews>>() {
                    @Override
                    public ArrayList<TopNews> apply(TopNewsList topNewsList) {
                        return topNewsList.getTopNewsArrayList();
                    }
                })
                .flatMap(new Function<ArrayList<TopNews>, Observable<TopNews>>() {
                    @Override
                    public Observable<TopNews> apply(ArrayList<TopNews> topNewses) {
                        return Observable.fromIterable(topNewses);
                    }
                })
                .filter(new Predicate<TopNews>() {
                    @Override
                    public boolean test(TopNews topNews) {
                        return topNews.getUrl() != null;
                    }
                })
                .map(new Function<TopNews, BaseItem>() {
                    @Override
                    public BaseItem apply(TopNews topNews) {
                        BaseItem<TopNews> baseItem = new BaseItem<>();
                        baseItem.setData(topNews);
                        return baseItem;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BaseItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(List<BaseItem> value) {
                        //每刷新成功一次多加载20条
                        currentIndex += 20;
                        mNewsListFrag.loadMoreSuccessed((ArrayList<BaseItem>) value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsListFrag.loadMoreFail(e.getMessage());
                    }

                });

    }

    /**
     * 读取缓存
     */
    public void loadCache() {
        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_TOPNEWS_FILE);
        ArrayList<BaseItem> topNews = manager.getSerializable(Constants.CACHE_TOPNEWS);
        if (topNews != null) {
            mNewsListFrag.refreshNewsSuccessed(topNews);
        }
    }
}
