package com.pandaq.pandaeye.presenter.video;

import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.model.api.ApiManager;
import com.pandaq.pandaeye.model.video.MovieInfo;
import com.pandaq.pandaeye.model.video.MovieResponse;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.rxbus.RxConstants;
import com.pandaq.pandaeye.ui.ImplView.IVideoInfoFrag;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PandaQ on 2017/3/9.
 * 视频详情
 */

public class VideoInfoFragPresenter extends BasePresenter {
    private IVideoInfoFrag mVideoInfoFrag;

    public VideoInfoFragPresenter(IVideoInfoFrag videoInfoFrag) {
        mVideoInfoFrag = videoInfoFrag;
    }

    /**
     * 获取视频详情页
     */
    public void loadVideoInfo() {
        ApiManager.getInstence()
                .getMovieService()
                .getMovieDaily(mVideoInfoFrag.getDataId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse<MovieInfo>>() {
                    @Override
                    public void onError(Throwable e) {
                        mVideoInfoFrag.loadInfoFail(Constants.ERRO, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(MovieResponse<MovieInfo> movieInfo) {
                        if (movieInfo.getData().getList() != null) {
                            mVideoInfoFrag.loadInfoSuccess(movieInfo.getData());
                            RxBus.getDefault().postWithCode(RxConstants.LOADED_DATA_CODE, movieInfo.getData());
                        } else {
                            mVideoInfoFrag.loadInfoFail(movieInfo.getCode(), movieInfo.getMsg());
                        }
                    }
                });
    }
}
