package com.pandaq.pandaeye.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.adapters.VideoCommentAdapter;
import com.pandaq.pandaeye.model.video.CommentBean;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.rxbus.RxConstants;
import com.pandaq.pandaeye.ui.base.BaseFragment;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by PandaQ on 2017/3/2.
 * 评论 fragment
 */

public class VideoCommentFrag extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_empty_msg)
    TextView mTvEmptyMsg;
    @BindView(R.id.mrv_comment)
    MagicRecyclerView mMrvComment;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private VideoCommentAdapter mAdapter;
    //本次加载数据是否是刷新
    private boolean refresh = true;
    //是否正在加载
    private boolean loading = false;
    //是否正在刷新
    private boolean refresing = false;
    //是否还有评论需要加载
    private boolean loadAble = true;
    private Subscription subscription;
    private Subscription innerSubscription;
    private Subscription msgSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_comment_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mMrvComment.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //屏蔽掉默认的动画，房子刷新时图片闪烁
        mMrvComment.getItemAnimator().setChangeDuration(0);
        mSrlRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white_FFFFFF));
        mSrlRefresh.setOnRefreshListener(this);
        mSrlRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mMrvComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mMrvComment.loadAble() && !loading && loadAble) {
                    loadMore();
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) { //可见时才去加载数据
            initRxbus();
            onRefresh();
        } else {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
                msgSubscription.unsubscribe();
                innerSubscription.unsubscribe();
            }
        }
    }

    public void refresh() {
        if (!refresing) {
            refresing = true;
            refresh = true;
            mSrlRefresh.setRefreshing(true);
            RxBus.getDefault().postWithCode(RxConstants.APPLY_DATA_CODE, RxConstants.APPLY_DATA_MSG_REFRESHCOMMENT);
        }
    }

    public void loadMore() {
        if (!loading) {
            loading = true;
            refresh = false;
            RxBus.getDefault().postWithCode(RxConstants.APPLY_DATA_CODE, RxConstants.APPLY_DATA_MSG_LOADMORECOMMENT);
        }
    }

    public void noMore() {
        loadAble = false;
        mMrvComment.getFooterView().setVisibility(View.GONE);
    }

    public void showInfo(ArrayList<BaseItem> items) {
        refresing = false;
        loading = false;
        mTvEmptyMsg.setVisibility(View.GONE);
        mSrlRefresh.setRefreshing(false);
        if (mAdapter != null) {
            if (refresh) {
                mAdapter.setBaseDatas(items);
            } else {
                mAdapter.addBaseDatas(items);
            }
        } else {
            mAdapter = new VideoCommentAdapter(this);
            mAdapter.setBaseDatas(items);
            mMrvComment.setAdapter(mAdapter);
        }
    }

    public void setEmpty() {
        mTvEmptyMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    /**
     * 初始化接收评论数据的 RxBus
     */
    private void initRxbus() {
        //接收评论数据的 Rxbus
        subscription = RxBus
                .getDefault()
                .toObservable(CommentBean.class)
                .subscribe(new Action1<CommentBean>() {
                    @Override
                    public void call(final CommentBean commentBean) {
                        innerSubscription = Observable
                                .create(new Observable.OnSubscribe<CommentBean>() {
                                    @Override
                                    public void call(Subscriber<? super CommentBean> subscriber) {
                                        subscriber.onNext(commentBean);
                                        subscriber.onCompleted();
                                    }
                                })
                                .map(new Func1<CommentBean, List<CommentBean.ListBean>>() {
                                    @Override
                                    public List<CommentBean.ListBean> call(CommentBean commentBean) {
                                        return commentBean.getList();
                                    }
                                })
                                .flatMap(new Func1<List<CommentBean.ListBean>, Observable<CommentBean.ListBean>>() {
                                    @Override
                                    public Observable<CommentBean.ListBean> call(List<CommentBean.ListBean> listBeen) {
                                        return Observable.from(listBeen);
                                    }
                                })
                                .map(new Func1<CommentBean.ListBean, BaseItem>() {
                                    @Override
                                    public BaseItem call(CommentBean.ListBean listBean) {
                                        BaseItem<CommentBean.ListBean> base = new BaseItem<>();
                                        base.setData(listBean);
                                        System.out.println(base);
                                        return base;
                                    }
                                })
                                .toList()
                                .subscribe(new Subscriber<List<BaseItem>>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        setEmpty();
                                    }

                                    @Override
                                    public void onNext(List<BaseItem> baseItems) {
                                        showInfo((ArrayList<BaseItem>) baseItems);
                                    }
                                });
                    }
                });
        //接收加载完成的 Rxbus
        msgSubscription = RxBus
                .getDefault()
                .toObservableWithCode(RxConstants.LOADED_ALL_COMMENT_CODE, String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String msg) {
                        noMore();
                    }
                });
    }
}
