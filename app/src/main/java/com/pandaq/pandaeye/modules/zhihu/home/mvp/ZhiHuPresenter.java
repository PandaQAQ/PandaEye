package com.pandaq.pandaeye.modules.zhihu.home.mvp;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.disklrucache.DiskCacheManager;
import com.pandaq.pandaeye.BasePresenter;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;

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
import io.reactivex.schedulers.Schedulers;


/**
 * Created by PandaQ on 2016/9/13.
 * email : 767807368@qq.com
 */
public class ZhiHuPresenter extends BasePresenter implements ZhiHuHomeContract.Presenter {

    private ZhiHuHomeContract.View mZhiHuDailyFrag;
    private String date;

    public ZhiHuPresenter(ZhiHuHomeContract.View zhiHuDailyFrag) {
        this.mZhiHuDailyFrag = zhiHuDailyFrag;
    }

    public void refreshZhihuDaily() {
        mZhiHuDailyFrag.showRefreshBar();
        ApiManager.getInstence()
                .getZhihuService()
                .getLatestZhihuDaily()
                .map(new Function<ZhiHuDaily, ZhiHuDaily>() { //io 线程存储缓存
                    @Override
                    public ZhiHuDaily apply(ZhiHuDaily zhiHuDaily) {
                        DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_ZHIHU_FILE);
                        manager.put(Constants.CACHE_ZHIHU_DAILY, zhiHuDaily);
                        return zhiHuDaily;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhiHuDaily>() {
                    @Override
                    public void onComplete() {
                        mZhiHuDailyFrag.hideRefreshBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mZhiHuDailyFrag.hideRefreshBar();
                        mZhiHuDailyFrag.refreshFail(e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ZhiHuDaily zhiHuDaily) {
                        date = zhiHuDaily.getDate();
                        mZhiHuDailyFrag.hideRefreshBar();
                        mZhiHuDailyFrag.refreshSuccessed(zhiHuDaily);
                    }
                });
    }

    public void loadMoreData() {
        ApiManager.getInstence()
                .getZhihuService()
                .getZhihuDaily(date)
                .map(new Function<ZhiHuDaily, ArrayList<ZhiHuStory>>() {
                    @Override
                    public ArrayList<ZhiHuStory> apply(ZhiHuDaily zhiHuDaily) {
                        date = zhiHuDaily.getDate();
                        return zhiHuDaily.getStories();
                    }
                })
                .flatMap(new Function<ArrayList<ZhiHuStory>, Observable<ZhiHuStory>>() {
                    @Override
                    public Observable<ZhiHuStory> apply(ArrayList<ZhiHuStory> zhiHuStories) {
                        return Observable.fromIterable(zhiHuStories);
                    }
                })
                .map(new Function<ZhiHuStory, BaseItem>() {
                    @Override
                    public BaseItem apply(ZhiHuStory zhiHuStory) {
                        //将日期值设置到 story 中
                        zhiHuStory.setDate(date);
                        BaseItem<ZhiHuStory> baseItem = new BaseItem<>();
                        baseItem.setData(zhiHuStory);
                        return baseItem;
                    }
                })
                .toList()
                // 在所有的数据 list 前面加上当天的 tag
                .map(new Function<List<BaseItem>, List<BaseItem>>() {
                    @Override
                    public List<BaseItem> apply(List<BaseItem> baseItems) {
                        BaseItem<String> baseItem = new BaseItem<>();
                        baseItem.setItemType(BaseRecyclerAdapter.RecyclerItemType.TYPE_TAGS);
                        baseItem.setData(date);
                        baseItems.add(0, baseItem);
                        return baseItems;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BaseItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(List<BaseItem> value) {
                        mZhiHuDailyFrag.loadSuccessed((ArrayList<BaseItem>) value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mZhiHuDailyFrag.loadFail(e.getMessage());
                    }

                });
    }

    /**
     * 加载缓存
     */
    public void loadCache() {
        final DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), Constants.CACHE_ZHIHU_FILE);
        Observable.create(new ObservableOnSubscribe<ZhiHuDaily>() {
            @Override
            public void subscribe(ObservableEmitter<ZhiHuDaily> e) throws Exception {
                ZhiHuDaily zhiHuDaily = manager.getSerializable(Constants.CACHE_ZHIHU_DAILY);
                e.onNext(zhiHuDaily);
            }
        }).subscribe(new Observer<ZhiHuDaily>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(ZhiHuDaily zhiHuDaily) {
                if (zhiHuDaily != null) {
                    date = zhiHuDaily.getDate();
                    mZhiHuDailyFrag.refreshSuccessed(zhiHuDaily);
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
