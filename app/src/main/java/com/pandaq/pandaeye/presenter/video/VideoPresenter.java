package com.pandaq.pandaeye.presenter.video;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.disklrucache.DiskCacheManager;
import com.pandaq.pandaeye.entity.movie.MovieResponse;
import com.pandaq.pandaeye.entity.movie.RetDataBean;
import com.pandaq.pandaeye.entity.neteasynews.TopNews;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.IVideoListFrag;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by PandaQ on 2017/3/1.
 * 视频 fragment Presenter
 */

public class VideoPresenter extends BasePresenter {

    private IVideoListFrag mFrag;

    public VideoPresenter(IVideoListFrag frag) {
        mFrag = frag;
    }

    /**
     * 加载视频主页的json数据
     */
    public void loadData() {
        mFrag.showProgressBar();
        Subscription subscription = ApiManager.getInstence()
                .getMovieService()
                .getHomePage()
                .flatMap(new Func1<MovieResponse<RetDataBean>, Observable<RetDataBean.ListBean>>() {
                    @Override
                    public Observable<RetDataBean.ListBean> call(MovieResponse<RetDataBean> response) {
                        return Observable.from(response.getData().getList());
                    }
                })
                //去广告
                .filter(new Func1<RetDataBean.ListBean, Boolean>() {
                    @Override
                    public Boolean call(RetDataBean.ListBean listBean) {
                        String showType = listBean.getShowType();
                        return Constants.SHOW_TYPE_IN.equals(showType) || Constants.SHOW_TYPE_BANNER.equals(showType);
                    }
                })
                .toList()
                //将 List 转为ArrayList 缓存存储 ArrayList Serializable对象
                .map(new Func1<List<RetDataBean.ListBean>, ArrayList<RetDataBean.ListBean>>() {
                    @Override
                    public ArrayList<RetDataBean.ListBean> call(List<RetDataBean.ListBean> listBeen) {
                        ArrayList<RetDataBean.ListBean> arr = new ArrayList<RetDataBean.ListBean>();
                        arr.addAll(listBeen);
                        return arr;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<RetDataBean.ListBean>>() {
                    @Override
                    public void onCompleted() {
                        mFrag.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFrag.hideProgressBar();
                        mFrag.refreshFail(Constants.ERRO, e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<RetDataBean.ListBean> listBeen) {
                        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_VIDEO_FILE);
                        manager.put(Constants.CACHE_VIDEO, listBeen);
                        mFrag.refreshSuccess(listBeen);
                        mFrag.hideProgressBar();
                    }
                });
        addSubscription(subscription);
    }

    /**
     * 读取缓存
     */
    public void loadCache() {
        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_VIDEO_FILE);

        ArrayList<RetDataBean.ListBean> arrbean = manager.getSerializable(Constants.CACHE_VIDEO);
        if (arrbean != null) {
            mFrag.refreshSuccess(arrbean);
        }
    }
}
