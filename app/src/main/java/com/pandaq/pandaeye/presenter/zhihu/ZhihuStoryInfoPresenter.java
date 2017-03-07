package com.pandaq.pandaeye.presenter.zhihu;

import com.pandaq.pandaeye.model.api.ApiManager;
import com.pandaq.pandaeye.model.zhihu.ZhihuStoryContent;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.IZhihuStoryInfoActivity;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PandaQ on 2016/10/10.
 * email : 767807368@qq.com
 */

public class ZhihuStoryInfoPresenter extends BasePresenter {

    private IZhihuStoryInfoActivity mActivity;

    public ZhihuStoryInfoPresenter(IZhihuStoryInfoActivity iZhihuStoryInfoActivity) {
        mActivity = iZhihuStoryInfoActivity;
    }

    public void loadStory(String id) {
        mActivity.showProgressBar();
        Subscription subscription = ApiManager.getInstence().getZhihuService()
                .getStoryContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuStoryContent>() {
                    @Override
                    public void onCompleted() {
                        mActivity.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadFail(e.getMessage());
                        mActivity.hideProgressBar();
                    }

                    @Override
                    public void onNext(ZhihuStoryContent zhihuStoryContent) {
                        mActivity.loadSuccess(zhihuStoryContent);
                        mActivity.hideProgressBar();
                    }
                });

        addSubscription(subscription);
    }
}
