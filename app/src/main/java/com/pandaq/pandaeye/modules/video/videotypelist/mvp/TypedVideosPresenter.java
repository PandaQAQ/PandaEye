package com.pandaq.pandaeye.modules.video.videotypelist.mvp;

import com.pandaq.pandaeye.config.Config;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.modules.video.MovieResponse;
import com.pandaq.pandaeye.BasePresenter;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by PandaQ on 2017/3/15.
 * 视频分类 Presenter
 */

public class TypedVideosPresenter extends BasePresenter implements VideoTypedContract.Presenter {
    private VideoTypedContract.View mActivity;
    private int currentPage = 0;

    public TypedVideosPresenter(VideoTypedContract.View activity) {
        mActivity = activity;
    }

    public void loadVideos(String catalogId) {
        ApiManager
                .getInstence()
                .getMovieService()
                .getTypedVideos(catalogId, String.valueOf(currentPage + 1))
                .map(new Function<MovieResponse<TypedVideos>, List<TypedVideos.ListBean>>() {
                    @Override
                    public List<TypedVideos.ListBean> apply(MovieResponse<TypedVideos> response) {
                        currentPage = response.getData().getPnum();
                        int totalPum = response.getData().getTotalPnum();
                        if (currentPage == totalPum) { //加载完所有的视频后
                            mActivity.noMoreVideo();
                        }
                        return response.getData().getList();
                    }
                })
                .flatMap(new Function<List<TypedVideos.ListBean>, Observable<TypedVideos.ListBean>>() {
                    @Override
                    public Observable<TypedVideos.ListBean> apply(List<TypedVideos.ListBean> listBeen) {
                        return Observable.fromIterable(listBeen);
                    }
                })
                .map(new Function<TypedVideos.ListBean, BaseItem>() {
                    @Override
                    public BaseItem apply(TypedVideos.ListBean listBean) {
                        BaseItem<TypedVideos.ListBean> base = new BaseItem<>();
                        base.setData(listBean);
                        return base;
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
                        mActivity.loadMoreSuccess((ArrayList<BaseItem>) value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadFail(Constants.ERRO, e.getMessage());
                    }

                });
    }

    public void loadLives(String catalogId) {
        ApiManager
                .getInstence()
                .getMovieService()
                .getLiveVideo(catalogId, String.valueOf(currentPage + 1))
                .map(new Function<MovieResponse<TypedVideos>, List<TypedVideos.ListBean>>() {
                    @Override
                    public List<TypedVideos.ListBean> apply(MovieResponse<TypedVideos> response) {
                        currentPage = response.getData().getPnum();
                        int totalPum = response.getData().getTotalPnum();
                        if (currentPage == totalPum) { //加载完所有的视频后
                            mActivity.noMoreVideo();
                        }
                        return response.getData().getList();
                    }
                })
                .flatMap(new Function<List<TypedVideos.ListBean>, Observable<TypedVideos.ListBean>>() {
                    @Override
                    public Observable<TypedVideos.ListBean> apply(List<TypedVideos.ListBean> listBeen) {
                        return Observable.fromIterable(listBeen);
                    }
                })
                .map(new Function<TypedVideos.ListBean, BaseItem>() {
                    @Override
                    public BaseItem apply(TypedVideos.ListBean listBean) {
                        BaseItem<TypedVideos.ListBean> base = new BaseItem<>();
                        base.setData(listBean);
                        return base;
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
                        mActivity.loadMoreSuccess((ArrayList<BaseItem>) value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadFail(Constants.ERRO, e.getMessage());
                    }

                });

    }

    public void loadData(String title) {
        switch (title) {
            case Constants.MOVIE_TYPE_BIGBRO:
                loadVideos(Config.MOVIE_TYPE_BIGBRO);
                break;
            case Constants.MOVIE_TYPE_HOLLYWOOD:
                loadVideos(Config.MOVIE_TYPE_HOLLYWOOD);
                break;
            case Constants.MOVIE_TYPE_HOT:
                loadVideos(Config.MOVIE_TYPE_HOT);
                break;
            case Constants.MOVIE_TYPE_JSKS:
                loadVideos(Config.MOVIE_TYPE_JSKS);
                break;
            case Constants.MOVIE_TYPE_LITTLEMOVIE:
                loadVideos(Config.MOVIE_TYPE_LITTLEMOVIE);
                break;
            case Constants.MOVIE_TYPE_LIVE:
                loadLives(Config.MOVIE_TYPE_LIVE);
                break;
            case Constants.MOVIE_TYPE_MIDNIGHT:
                loadVideos(Config.MOVIE_TYPE_MIDNIGHT);
                break;
            case Constants.MOVIE_TYPE_RECOMMEND:
                loadVideos(Config.MOVIE_TYPE_RECOMMEND);
                break;
            case Constants.MOVIE_TYPE_TOPIC:
//                loadVideos(Config.MOVIE_TYPE_TOPIC);
                break;
        }
    }
}
