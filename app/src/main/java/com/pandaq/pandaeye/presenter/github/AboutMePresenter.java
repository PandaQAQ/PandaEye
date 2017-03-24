package com.pandaq.pandaeye.presenter.github;

import com.pandaq.pandaeye.model.api.ApiManager;
import com.pandaq.pandaeye.model.github.UserInfo;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.IAboutMeActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PandaQ on 2017/3/24.
 * 关于我界面的 Presenter
 */

public class AboutMePresenter extends BasePresenter {
    private IAboutMeActivity mActivity;

    public AboutMePresenter(IAboutMeActivity activity) {
        mActivity = activity;
    }

    public void loadInfo(String user) {
        ApiManager.getInstence()
                .getGithubService()
                .getMyInfo(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(UserInfo value) {
                        mActivity.showMyInfo(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadMyInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
