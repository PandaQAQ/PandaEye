package com.pandaq.pandaeye.presenter.douban;

import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.entity.DouBan.MovieSubject;
import com.pandaq.pandaeye.entity.DouBan.MovieTop250;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.IMovieListFrag;

import java.util.ArrayList;

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

    private IMovieListFrag mMovieListFrag;
    private int start;
    private boolean loadAllCompleted = false;

    public DouBanMoviePresenter(IMovieListFrag mMovieListFrag) {
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
                .map(new Func1<MovieTop250, ArrayList<MovieSubject>>() {
                    @Override
                    public ArrayList<MovieSubject> call(MovieTop250 movieTop250) {
                        //刷新后下一次加载的起点为
                        start = movieTop250.getCount();
                        return movieTop250.getMovieSubjects();
                    }
                })
                .subscribe(new Subscriber<ArrayList<MovieSubject>>() {
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
                    public void onNext(ArrayList<MovieSubject> movieSubjects) {
                        mMovieListFrag.hideProgressBar();
                        mMovieListFrag.refreshSucceed(movieSubjects);
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
                .map(new Func1<MovieTop250, ArrayList<MovieSubject>>() {
                    @Override
                    public ArrayList<MovieSubject> call(MovieTop250 movieTop250) {
                        //赋值下一次刷新的起始位置
                        start += movieTop250.getCount();
                        //下一次刷新的起始位置如果大于了总的item则不再刷新
                        if (start > movieTop250.getTotal()) {
                            loadAllCompleted = true;
                        }
                        return movieTop250.getMovieSubjects();
                    }
                }).subscribe(new Subscriber<ArrayList<MovieSubject>>() {
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
                    public void onNext(ArrayList<MovieSubject> movieSubjects) {
                        mMovieListFrag.hideProgressBar();
                        mMovieListFrag.loadSuccessed(movieSubjects);
                    }
                });
        addSubscription(subscription);
    }


}
