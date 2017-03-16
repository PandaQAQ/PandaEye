package com.pandaq.pandaeye.ui.news;

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

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.adapters.TopNewsListAdapter;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.model.neteasynews.TopNews;
import com.pandaq.pandaeye.presenter.news.NewsPresenter;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.rxbus.RxConstants;
import com.pandaq.pandaeye.ui.ImplView.INewsListFrag;
import com.pandaq.pandaeye.ui.base.BaseFragment;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by PandaQ on 2016/9/9.
 * email : 767807368@qq.com
 * 新闻列表界面
 */
public class NewsListFragment extends BaseFragment implements INewsListFrag, SwipeRefreshLayout.OnRefreshListener, BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.testRecycler)
    MagicRecyclerView mNewsRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    private NewsPresenter mPresenter = new NewsPresenter(this);
    private TopNewsListAdapter mAdapter;
    private boolean loading = false;
    private Subscription mSubscription;
    private LinearLayoutManager mLinearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newslist_fragment, container, false);
        ButterKnife.bind(this, view);
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
        mPresenter.unSubscribe();
        onHiddenChanged(true);
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

    }

    @Override
    public void refreshNewsSuccessed(ArrayList<BaseItem> topNews) {
        if (mAdapter == null) {
            mAdapter = new TopNewsListAdapter(this);
            mAdapter.setBaseDatas(topNews);
            mNewsRecycler.setAdapter(mAdapter);
            //实质是是对 adapter 设置点击事件所以需要在设置 adapter 之后调用
            mNewsRecycler.addOnItemClickListener(this);
        } else {
            mAdapter.setBaseDatas(topNews);
        }
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
    public void onRefresh() {
        refreshNews();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden && mRefresh.isRefreshing()) { // 隐藏的时候停止 SwipeRefreshLayout 转动
            mRefresh.setRefreshing(false);
        }
        if (!hidden) {
            mSubscription = RxBus
                    .getDefault()
                    .toObservableWithCode(RxConstants.BACK_PRESSED_CODE, String.class)
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            if (s.endsWith(RxConstants.BACK_PRESSED_DATA) && mNewsRecycler != null) {
                                //滚动到顶部
                                mLinearLayoutManager.smoothScrollToPosition(mNewsRecycler, null, 0);
                            }
                        }
                    });
        } else {
            if (mSubscription != null && !mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
            }
        }
    }

    @Override
    public void onItemClick(int position, BaseItem data, View view) {
        //跳转到其他界面
        TopNews topNews = (TopNews) data.getData();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(NewsListFragment.this.getActivity(), TopNewsInfoActivity.class);
        bundle.putString(Constants.BUNDLE_KEY_TITLE, topNews.getTitle());
        bundle.putString(Constants.BUNDLE_KEY_ID, topNews.getDocid());
        bundle.putString(Constants.BUNDLE_KEY_IMG_URL, topNews.getImgsrc());
        intent.putExtras(bundle);
        String transitionName = getString(R.string.top_news_img);
        Pair pairImg = new Pair<>(view.findViewById(R.id.news_image), transitionName);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairImg);
        startActivity(intent, transitionActivityOptions.toBundle());
    }
}
