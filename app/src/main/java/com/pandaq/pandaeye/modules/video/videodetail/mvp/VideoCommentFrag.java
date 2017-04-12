package com.pandaq.pandaeye.modules.video.videodetail.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.rxbus.RxConstants;
import com.pandaq.pandaeye.BaseFragment;
import com.pandaq.pandaeye.modules.video.videodetail.VideoCommentAdapter;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by PandaQ on 2017/3/2.
 * 评论 fragment
 */

public class VideoCommentFrag extends BaseFragment implements VideoCommentContract.View, SwipeRefreshLayout.OnRefreshListener {

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
    private String currentId = "currentId";
    private String lastId = "lastId";
    private VideoCommentPresenter mCommentPresenter = new VideoCommentPresenter(this);
    private Disposable mDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_comment_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mCommentPresenter.dispose();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
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
                    loadComment();
                }
            }
        });
        currentId = getArguments().getString(Constants.BUNDLE_KEY_DATAID);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) { //可见时才去加载数据
            initRxbus();
            if (!lastId.equals(currentId)) {
                refreshComment();
            }
        }
    }

    @Override
    public void loadComment() {
        refresh = false;
        if (!loading) {
            mCommentPresenter.loadMoreComment();
            loading = true;
        }
    }

    @Override
    public void refreshComment() {
        mSrlRefresh.setRefreshing(true);
        refresh = true;
        if (!refresing) {
            mCommentPresenter.refreshComment();
            refresing = true;
        }
    }

    @Override
    public String getDataId() {
        return currentId;
    }

    @Override
    public void showComment(ArrayList<BaseItem> items) {
        //加载成功后设置当前 Id
        lastId = currentId;
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

    public void noMore() {
        loadAble = false;
        TextView ftText = (TextView) mMrvComment.getFooterView().findViewById(R.id.tv_footer_text);
        ProgressBar ftPB = (ProgressBar) mMrvComment.getFooterView().findViewById(R.id.cp_progressbar);
        ftPB.setVisibility(View.GONE);
        ftText.setText(getString(R.string.nomore_data));
    }

    @Override
    public void loadFail() {
        mSrlRefresh.setRefreshing(false);
        mTvEmptyMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        refreshComment();
    }

    /**
     * 初始化接收评论数据的 RxBus
     */
    private void initRxbus() {
        //接收 Video ID
        RxBus
                .getDefault()
                .toObservableWithCode(RxConstants.ACCEPT_VIDEO_DATAID, String.class)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        currentId = value;
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
