package com.pandaq.pandaeye.presenter.douban;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.disklrucache.DiskCacheManager;
import com.pandaq.pandaeye.entity.douban.MovieSubject;
import com.pandaq.pandaeye.entity.douban.MovieTop250;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.IDoubanFrag;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 豆瓣电影FragmentPresenter
 */
public class DouBanMoviePresenter extends BasePresenter {

    private IDoubanFrag mMovieListFrag;
    private int start;
    private boolean loadAllCompleted = false;

    public DouBanMoviePresenter(IDoubanFrag mMovieListFrag) {
        this.mMovieListFrag = mMovieListFrag;
    }

    /**
     * 刷新操作
     */
    public void refreshData() {
        mMovieListFrag.showProgressBar();
        Subscription subscription = ApiManager.getInstence().getDoubanService()
                .getTop250(0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<MovieTop250, Observable<MovieSubject>>() {
                    @Override
                    public Observable<MovieSubject> call(MovieTop250 movieTop250) {
                        //刷新后下一次加载的起点为
                        start = movieTop250.getCount();
                        return Observable.from(movieTop250.getMovieSubjects());
                    }
                })
                .map(new Func1<MovieSubject, BaseItem>() {
                    @Override
                    public BaseItem call(MovieSubject movieSubject) {
                        BaseItem<MovieSubject> baseItem = new BaseItem<>();
                        baseItem.setData(movieSubject);
                        return baseItem;
                    }
                })
                .toList()
                //将 List 转为ArrayList 缓存存储 ArrayList Serializable对象
                .map(new Func1<List<BaseItem>, ArrayList<BaseItem>>() {
                    @Override
                    public ArrayList<BaseItem> call(List<BaseItem> baseItems) {
                        ArrayList<BaseItem> items = new ArrayList<>();
                        items.addAll(baseItems);
                        return items;
                    }
                })
                .subscribe(new Subscriber<ArrayList<BaseItem>>() {
                    @Override
                    public void onCompleted() {
                        mMovieListFrag.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMovieListFrag.hideProgressBar();
                        mMovieListFrag.refreshFail(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<BaseItem> baseItems) {
                        mMovieListFrag.hideProgressBar();
                        mMovieListFrag.refreshSucceed(baseItems);
                        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_DOUBAN_FILE);
                        manager.put(Constants.CACHE_DOUBAN_MOVIE, baseItems);
                    }
                });
        addSubscription(subscription);
    }

    /**
     * 对外提供的加载更多方法，当还未全部加载完的时候回去加载更多
     */
    public void loadMoreData() {
        if (!loadAllCompleted) {
            loadMore();
        }
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        mMovieListFrag.showProgressBar();
        Subscription subscription = ApiManager.getInstence().getDoubanService()
                .getTop250(start, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<MovieTop250, Observable<MovieSubject>>() {
                    @Override
                    public Observable<MovieSubject> call(MovieTop250 movieTop250) {
                        //刷新后下一次加载的起点为
                        start += movieTop250.getCount();
                        return Observable.from(movieTop250.getMovieSubjects());
                    }
                })
                .map(new Func1<MovieSubject, BaseItem>() {
                    @Override
                    public BaseItem call(MovieSubject movieSubject) {
                        BaseItem<MovieSubject> baseItem = new BaseItem<>();
                        baseItem.setData(movieSubject);
                        return baseItem;
                    }
                })
                .toList()
                .map(new Func1<List<BaseItem>, ArrayList<BaseItem>>() {
                    @Override
                    public ArrayList<BaseItem> call(List<BaseItem> baseItems) {
                        ArrayList<BaseItem> items = new ArrayList<>();
                        items.addAll(baseItems);
                        return items;
                    }
                })
                .subscribe(new Subscriber<ArrayList<BaseItem>>() {
                    @Override
                    public void onCompleted() {
                        mMovieListFrag.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMovieListFrag.hideProgressBar();
                        mMovieListFrag.loadFail(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<BaseItem> movieSubjects) {
                        mMovieListFrag.hideProgressBar();
                        mMovieListFrag.loadSuccessed(movieSubjects);
                    }
                });
        addSubscription(subscription);
    }

    /**
     * 加载缓存
     */
    public void loadCache() {
        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_DOUBAN_FILE);
        ArrayList<BaseItem> movieSubjects = manager.getSerializable(Constants.CACHE_DOUBAN_MOVIE);
        if (movieSubjects != null) {
            mMovieListFrag.refreshSucceed(movieSubjects);
        }
    }
}
