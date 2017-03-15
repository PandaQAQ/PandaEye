package com.pandaq.pandaeye.presenter.video;

import com.pandaq.pandaeye.config.Config;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.model.api.ApiManager;
import com.pandaq.pandaeye.model.video.MovieResponse;
import com.pandaq.pandaeye.model.video.TypedVideos;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.ITypedVideosActivity;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by PandaQ on 2017/3/15.
 * 视频分类 Presenter
 */

public class TypedVideosPresenter extends BasePresenter {
    private ITypedVideosActivity mActivity;
    private int currentPage = 0;

    public TypedVideosPresenter(ITypedVideosActivity activity) {
        mActivity = activity;
    }

    private void loadVideos(String catalogId) {
        Subscription subscription = ApiManager
                .getInstence()
                .getMovieService()
                .getTypedVideos(catalogId, String.valueOf(currentPage + 1))
                .map(new Func1<MovieResponse<TypedVideos>, List<TypedVideos.ListBean>>() {
                    @Override
                    public List<TypedVideos.ListBean> call(MovieResponse<TypedVideos> response) {
                        currentPage = response.getData().getPnum();
                        int totalPum = response.getData().getTotalPnum();
                        if (currentPage == totalPum) { //加载完所有的视频后
                            mActivity.noMoreVideo();
                        }
                        return response.getData().getList();
                    }
                })
                .flatMap(new Func1<List<TypedVideos.ListBean>, Observable<TypedVideos.ListBean>>() {
                    @Override
                    public Observable<TypedVideos.ListBean> call(List<TypedVideos.ListBean> listBeen) {
                        return Observable.from(listBeen);
                    }
                })
                .map(new Func1<TypedVideos.ListBean, BaseItem>() {
                    @Override
                    public BaseItem call(TypedVideos.ListBean listBean) {
                        BaseItem<TypedVideos.ListBean> base = new BaseItem<>();
                        base.setData(listBean);
                        return base;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BaseItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadFail(Constants.ERRO, e.getMessage());
                    }

                    @Override
                    public void onNext(List<BaseItem> baseItems) {
                        mActivity.loadMoreSuccess(baseItems);
                    }
                });
        addSubscription(subscription);
    }

    private void loadLives(String catalogId) {
        Subscription subscription = ApiManager
                .getInstence()
                .getMovieService()
                .getLiveVideo(catalogId, String.valueOf(currentPage + 1))
                .map(new Func1<MovieResponse<TypedVideos>, List<TypedVideos.ListBean>>() {
                    @Override
                    public List<TypedVideos.ListBean> call(MovieResponse<TypedVideos> response) {
                        currentPage = response.getData().getPnum();
                        int totalPum = response.getData().getTotalPnum();
                        if (currentPage == totalPum) { //加载完所有的视频后
                            mActivity.noMoreVideo();
                        }
                        return response.getData().getList();
                    }
                })
                .flatMap(new Func1<List<TypedVideos.ListBean>, Observable<TypedVideos.ListBean>>() {
                    @Override
                    public Observable<TypedVideos.ListBean> call(List<TypedVideos.ListBean> listBeen) {
                        return Observable.from(listBeen);
                    }
                })
                .map(new Func1<TypedVideos.ListBean, BaseItem>() {
                    @Override
                    public BaseItem call(TypedVideos.ListBean listBean) {
                        BaseItem<TypedVideos.ListBean> base = new BaseItem<>();
                        base.setData(listBean);
                        return base;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BaseItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadFail(Constants.ERRO, e.getMessage());
                    }

                    @Override
                    public void onNext(List<BaseItem> baseItems) {
                        mActivity.loadMoreSuccess(baseItems);
                    }
                });
        addSubscription(subscription);
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
