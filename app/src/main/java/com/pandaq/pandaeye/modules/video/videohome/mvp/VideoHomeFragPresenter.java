package com.pandaq.pandaeye.modules.video.videohome.mvp;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.disklrucache.DiskCacheManager;
import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.modules.video.MovieResponse;
import com.pandaq.pandaeye.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by PandaQ on 2017/3/1.
 * 视频 fragment Presenter
 */

public class VideoHomeFragPresenter extends BasePresenter implements VideoHomeContract.Presenter {

    private VideoHomeContract.View mFrag;

    public VideoHomeFragPresenter(VideoHomeContract.View frag) {
        mFrag = frag;
    }

    /**
     * 加载视频主页的json数据
     */
    public void loadData() {
        mFrag.showProgressBar();
        ApiManager.getInstence()
                .getMovieService()
                .getHomePage()
                .flatMap(new Function<MovieResponse<RetDataBean>, Observable<RetDataBean.ListBean>>() {
                    @Override
                    public Observable<RetDataBean.ListBean> apply(MovieResponse<RetDataBean> response) {
                        return Observable.fromIterable(response.getData().getList());
                    }
                })
                //去广告
                .filter(new Predicate<RetDataBean.ListBean>() {
                    @Override
                    public boolean test(RetDataBean.ListBean listBean) throws Exception {
                        String showType = listBean.getShowType();
                        return Constants.SHOW_TYPE_IN.equals(showType) || Constants.SHOW_TYPE_BANNER.equals(showType);
                    }
                })
                .toList()
                //将 List 转为ArrayList 缓存存储 ArrayList Serializable对象
                .map(new Function<List<RetDataBean.ListBean>, ArrayList<RetDataBean.ListBean>>() {
                    @Override
                    public ArrayList<RetDataBean.ListBean> apply(List<RetDataBean.ListBean> listBeen) {
                        ArrayList<RetDataBean.ListBean> arr = new ArrayList<RetDataBean.ListBean>();
                        arr.addAll(listBeen);
                        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_VIDEO_FILE);
                        manager.put(Constants.CACHE_VIDEO, arr);
                        return arr;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<RetDataBean.ListBean>>() {


                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(ArrayList<RetDataBean.ListBean> value) {
                        mFrag.refreshSuccess(value);
                        mFrag.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFrag.hideProgressBar();
                        mFrag.refreshFail(Constants.ERRO, e.getMessage());
                    }

                });

    }

    /**
     * 加载缓存
     */
    public void loadCache() {
        final DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_VIDEO_FILE);
        Observable.create(new ObservableOnSubscribe<ArrayList<RetDataBean.ListBean>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<RetDataBean.ListBean>> e) throws Exception {
                ArrayList<RetDataBean.ListBean> arrbean = manager.getSerializable(Constants.CACHE_VIDEO);
                e.onNext(arrbean);
            }
        }).subscribe(new Observer<ArrayList<RetDataBean.ListBean>>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(ArrayList<RetDataBean.ListBean> value) {
                if (value != null) {
                    mFrag.refreshSuccess(value);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }
}
