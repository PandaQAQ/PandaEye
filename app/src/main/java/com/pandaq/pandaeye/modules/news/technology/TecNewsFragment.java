package com.pandaq.pandaeye.modules.news.technology;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.news.NewsContract;
import com.pandaq.pandaeye.modules.news.NewsListAdapter;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.modules.news.NewsBean;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.rxbus.RxConstants;
import com.pandaq.pandaeye.BaseFragment;
import com.pandaq.pandaeye.modules.news.newsdetail.NewsDetailActivity;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by PandaQ on 2017/3/28.
 * 科技新闻列表
 */

public class TecNewsFragment extends BaseFragment implements NewsContract.View, SwipeRefreshLayout.OnRefreshListener, BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.newsRecycler)
    MagicRecyclerView mNewsRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.empty_msg)
    TextView mEmptyMsg;
    private TecPresenter mPresenter = new TecPresenter(this);
    private NewsListAdapter mAdapter;
    private boolean loading = false;
    private Disposable mDisposable;
    private LinearLayoutManager mLinearLayoutManager;
    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.headline_newslist_fragment, null, false);
        mUnbinder = ButterKnife.bind(this, view);
        mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mNewsRecycler.setLayoutManager(mLinearLayoutManager);
        //屏蔽掉默认的动画，房子刷新时图片闪烁
        mNewsRecycler.getItemAnimator().setChangeDuration(0);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onHiddenChanged(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRefresh.setRefreshing(false);
        mPresenter.dispose();
        onHiddenChanged(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mAdapter = null;
    }

    private void initView() {
        mNewsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mNewsRecycler.refreshAble()) {
                    mRefresh.setEnabled(true);
                }
                if (mNewsRecycler.loadAble()) {
                    loadMoreNews();
                }
            }
        });
        mRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white_FFFFFF));
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefresh.setRefreshing(true);
        refreshNews();
        mPresenter.loadCache();
        View footer = mNewsRecycler.getFooterView();
        if (footer != null) {
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadMoreNews();
                }
            });
        }
    }

    @Override
    public void showRefreshBar() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideRefreshBar() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void refreshNews() {
        mPresenter.refreshNews();
    }

    @Override
    public void refreshNewsFail(String errorMsg) {
        if (mAdapter == null) {
            mEmptyMsg.setVisibility(View.VISIBLE);
            mNewsRecycler.setVisibility(View.INVISIBLE);
            mRefresh.requestFocus();
        }
    }

    @Override
    public void refreshNewsSuccessed(ArrayList<BaseItem> topNews) {
        if (topNews == null || topNews.size() <= 0) {
            mEmptyMsg.setVisibility(View.VISIBLE);
            mNewsRecycler.setVisibility(View.INVISIBLE);
            mRefresh.requestFocus();
        } else {
            mEmptyMsg.setVisibility(View.GONE);
            mNewsRecycler.setVisibility(View.VISIBLE);
        }
        if (mAdapter == null) {
            mAdapter = new NewsListAdapter(this);
            mAdapter.setBaseDatas(topNews);
            mNewsRecycler.setAdapter(mAdapter);
            //实质是是对 adapter 设置点击事件所以需要在设置 adapter 之后调用
            mNewsRecycler.addOnItemClickListener(this);
        } else {
            mAdapter.setBaseDatas(topNews);
        }
        mNewsRecycler.showFooter();
    }

    @Override
    public void loadMoreNews() {
        if (!loading) {
            mPresenter.loadMore();
            loading = true;
        }
    }

    @Override
    public void loadMoreFail(String errorMsg) {
        loading = false;
    }

    @Override
    public void loadMoreSuccessed(ArrayList<BaseItem> topNewses) {
        loading = false;
        mAdapter.addBaseDatas(topNewses);
    }

    @Override
    public void loadAll() {
        mNewsRecycler.hideFooter();
    }

    @Override
    public void onRefresh() {
        refreshNews();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden && mRefresh.isRefreshing()) { // 隐藏的时候停止 SwipeRefreshLayout 转动
            mRefresh.setRefreshing(false);
        }
        if (!hidden) {
            RxBus.getDefault()
                    .toObservableWithCode(RxConstants.BACK_PRESSED_CODE, String.class)
                    .subscribeWith(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(String value) {
                            if (value.equals(RxConstants.BACK_PRESSED_DATA) && mNewsRecycler != null) {
                                //滚动到顶部
                                mLinearLayoutManager.smoothScrollToPosition(mNewsRecycler, null, 0);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

    @Override
    public void onItemClick(int position, BaseItem data, View view) {
        //跳转到其他界面
        NewsBean topNews = (NewsBean) data.getData();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(TecNewsFragment.this.getActivity(), NewsDetailActivity.class);
        bundle.putString(Constants.BUNDLE_KEY_TITLE, topNews.getTitle());
        bundle.putString(Constants.BUNDLE_KEY_ID, topNews.getDocid());
        bundle.putString(Constants.BUNDLE_KEY_IMG_URL, topNews.getImgsrc());
        bundle.putString(Constants.BUNDLE_KEY_HTML_URL, topNews.getUrl());
        intent.putExtras(bundle);
        String transitionName = getString(R.string.top_news_img);
        Pair pairImg = new Pair<>(view.findViewById(R.id.news_image), transitionName);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairImg);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

}
