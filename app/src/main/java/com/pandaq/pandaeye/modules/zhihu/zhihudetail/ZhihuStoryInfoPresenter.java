package com.pandaq.pandaeye.modules.zhihu.zhihudetail;

import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.modules.BasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by PandaQ on 2016/10/10.
 * email : 767807368@qq.com
 */

class ZhihuStoryInfoPresenter extends BasePresenter implements ZhiHuDetailContract.Presenter {

    private ZhiHuDetailContract.View mActivity;

    @Override
    public void loadStory(String id) {
        ApiManager.getInstence().getZhihuService()
                .getStoryContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuStoryContent>() {

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ZhihuStoryContent zhihuStoryContent) {
                        mActivity.loadSuccess(zhihuStoryContent);
                    }
                });

    }

    @Override
    public void bindView(ImpBaseView view) {
        mActivity = (ZhiHuDetailContract.View) view;
    }

    @Override
    public void unbindView() {
        dispose();
    }

    @Override
    public void onDestory() {
        mActivity = null;
    }
}
