package com.pandaq.pandaeye.presenter.zhihu;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.disklrucache.DiskCacheManager;
import com.pandaq.pandaeye.entity.zhihu.ZhiHuDaily;
import com.pandaq.pandaeye.entity.zhihu.ZhiHuStory;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.IZhiHuDailyFrag;
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
 * Created by PandaQ on 2016/9/13.
 * email : 767807368@qq.com
 */
public class ZhiHuPresenter extends BasePresenter {

    private IZhiHuDailyFrag mZhiHuDailyFrag;
    private String date;

    public ZhiHuPresenter(IZhiHuDailyFrag zhiHuDailyFrag) {
        this.mZhiHuDailyFrag = zhiHuDailyFrag;
    }

    public void refreshZhihuDaily() {
        mZhiHuDailyFrag.showRefreshBar();
        Subscription subscription = ApiManager.getInstence()
                .getZhihuService()
                .getLatestZhihuDaily()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuDaily>() {
                    @Override
                    public void onCompleted() {
                        mZhiHuDailyFrag.hideRefreshBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mZhiHuDailyFrag.hideRefreshBar();
                        mZhiHuDailyFrag.refreshFail(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhiHuDaily zhiHuDaily) {
                        date = zhiHuDaily.getDate();
                        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_ZHIHU_FILE);
                        manager.put(Constants.CACHE_ZHIHU_DAILY, zhiHuDaily);
                        mZhiHuDailyFrag.hideRefreshBar();
                        mZhiHuDailyFrag.refreshSuccessed(zhiHuDaily);
                    }
                });
        addSubscription(subscription);
    }

    public void loadMoreData() {
        Subscription subscription = ApiManager.getInstence()
                .getZhihuService()
                .getZhihuDaily(date)
                .map(new Func1<ZhiHuDaily, ArrayList<ZhiHuStory>>() {
                    @Override
                    public ArrayList<ZhiHuStory> call(ZhiHuDaily zhiHuDaily) {
                        date = zhiHuDaily.getDate();
                        return zhiHuDaily.getStories();
                    }
                })
                .flatMap(new Func1<ArrayList<ZhiHuStory>, Observable<ZhiHuStory>>() {
                    @Override
                    public Observable<ZhiHuStory> call(ArrayList<ZhiHuStory> zhiHuStories) {
                        return Observable.from(zhiHuStories);
                    }
                })
                .map(new Func1<ZhiHuStory, BaseItem>() {
                    @Override
                    public BaseItem call(ZhiHuStory zhiHuStory) {
                        //将日期值设置到 story 中
                        zhiHuStory.setDate(date);
                        BaseItem<ZhiHuStory> baseItem = new BaseItem<>();
                        baseItem.setData(zhiHuStory);
                        return baseItem;
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
                        mZhiHuDailyFrag.loadFail(e.getMessage());
                    }

                    @Override
                    public void onNext(List<BaseItem> baseitems) {
                        mZhiHuDailyFrag.loadSuccessed((ArrayList<BaseItem>) baseitems);
                    }
                });
        addSubscription(subscription);
    }

    /**
     * 加载缓存
     */
    public void loadCache() {
        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_ZHIHU_FILE);
        ZhiHuDaily zhiHuDaily = manager.getSerializable(Constants.CACHE_ZHIHU_DAILY);
        if (zhiHuDaily != null) {
            mZhiHuDailyFrag.refreshSuccessed(zhiHuDaily);
        }
    }
}
