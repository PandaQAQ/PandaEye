package com.pandaq.pandaeye.presenter.video;

import com.pandaq.pandaeye.model.api.ApiManager;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.model.video.MovieInfo;
import com.pandaq.pandaeye.model.video.MovieResponse;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.IVedioInfoActivity;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PandaQ on 2017/3/2.
 * 视频详情页面的 Presenter
 */

public class VideoInfoPresenter extends BasePresenter {
    private IVedioInfoActivity mInfoActivity;
    private int currentPage = 1;
    private boolean refreshComment = true;
    private boolean hasLoadAll = false; //是否加载了所有评论的标识位

    public VideoInfoPresenter(IVedioInfoActivity infoActivity) {
        mInfoActivity = infoActivity;
    }

    /**
     * 获取视频详情页
     */
    public void loadVideoInfo() {
        Subscription subscription = ApiManager.getInstence()
                .getMovieService()
                .getMovieDaily(mInfoActivity.getDataId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResponse<MovieInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mInfoActivity.loadVideoInfoFail(Constants.ERRO, e.getMessage());
                    }

                    @Override
                    public void onNext(MovieResponse<MovieInfo> movieInfo) {
                        mInfoActivity.loadVideoInfoSuccess(movieInfo.getData());
                    }
                });
        addSubscription(subscription);
    }

//    /**
//     * 获取视频评论
//     */
//    private void loadVideoComment() {
//        Subscription subscription = ApiManager.getInstence()
//                .getMovieService()
//                .getCommentList(mInfoActivity.getDataId(), String.valueOf(currentPage))
//                .map(new Func1<MovieResponse<CommentBean>, ArrayList<CommentBean.ListBean>>() {
//                    @Override
//                    public ArrayList<CommentBean.ListBean> call(MovieResponse<CommentBean> response) {
//                        currentPage = response.getData().getPnum();
//                        int totalPum = response.getData().getTotalPnum();
//                        if (currentPage == totalPum) { //加载完所有的评论后
//                            mInfoActivity.noMoreComment();
//                        }
//                        ArrayList<CommentBean.ListBean> comments = new ArrayList<>();
//                        comments.addAll(response.getData().getList());
//                        return comments;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ArrayList<CommentBean.ListBean>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (refreshComment) {
//                            mInfoActivity.refreshCommentFail(Constants.ERRO, e.getMessage());
//                        } else {
//                            mInfoActivity.loadCommentFail(Constants.ERRO, e.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onNext(ArrayList<CommentBean.ListBean> listBeen) {
//                        if (refreshComment) {
//                            mInfoActivity.refreshCommentSuccess(listBeen);
//                        } else {
//                            mInfoActivity.loadCommentSuccess(listBeen);
//                        }
//                    }
//                });
//        addSubscription(subscription);
//
//    }

//    /**
//     * 刷新评论
//     */
//    public void refreshComment() {
//        refreshComment = true;
//        currentPage = 1;
//        loadVideoComment();
//    }
//
//    /**
//     * 加载更多评论
//     */
//    public void loadMoreComment() {
//        refreshComment = false;
//        loadVideoComment();
//    }
}
