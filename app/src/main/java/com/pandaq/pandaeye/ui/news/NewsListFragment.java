package com.pandaq.pandaeye.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.adapters.TopNewsListAdapter;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.entity.NetEasyNews.TopNews;
import com.pandaq.pandaeye.presenter.news.NewsPresenter;
import com.pandaq.pandaeye.utils.TranslateHelper;
import com.pandaq.pandaeye.ui.ImplView.INewsListFrag;
import com.pandaq.pandaeye.ui.base.BaseFragment;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/9.
 * email : 767807368@qq.com
 * 新闻列表界面
 */
public class NewsListFragment extends BaseFragment implements INewsListFrag, SwipeRefreshLayout.OnRefreshListener {

    private boolean loadOrRefrshAble = true;
    @BindView(R.id.testRecycler)
    MagicRecyclerView mNewsRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    private NewsPresenter mPresenter = new NewsPresenter(this);
    private ArrayList<TopNews> mTopNewses;
    private TopNewsListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newslist_fragment, container, false);
        ButterKnife.bind(this, view);
        mNewsRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        initView();
        return view;
    }

    private void initView() {
        mTopNewses = new ArrayList<>();
        mNewsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mNewsRecycler.refreshAble()) {
                    mRefresh.setEnabled(true);
                }
//                if (mNewsRecycler.loadAble()) {
//                    loadMoreNews();
//                }
            }
        });
        mRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white_FFFFFF));
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshNews();
        mPresenter.loadCache();
        mNewsRecycler.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //跳转到其他界面
                Bundle bundle = new Bundle();
                Intent intent = new Intent(NewsListFragment.this.getActivity(), TopNewsInfoActivity.class);
                bundle.putString(Constants.BUNDLE_KEY_TITLE, mTopNewses.get(position).getTitle());
                bundle.putString(Constants.BUNDLE_KEY_ID, mTopNewses.get(position).getDocid());
                bundle.putString(Constants.BUNDLE_KEY_IMG_URL, mTopNewses.get(position).getImgsrc());
                intent.putExtras(bundle);
                String transitionName = getString(R.string.top_news_img);
                Pair pairImg = new Pair<>(view.findViewById(R.id.news_image), transitionName);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairImg);
                startActivity(intent, transitionActivityOptions.toBundle());
            }
        });
        View footer = mNewsRecycler.getFooterView();
        if (footer!=null){
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
    public void showLoadBar() {

    }

    @Override
    public void hideLoadBar() {

    }

    @Override
    public void refreshNews() {
        if (loadOrRefrshAble) {
            mPresenter.refreshNews();
            loadOrRefrshAble = false;
        }
    }

    @Override
    public void refreshNewsFail(String errorMsg) {
        loadOrRefrshAble = true;
    }

    @Override
    public void refreshNewsSuccessed(ArrayList<TopNews> topNews) {
        mTopNewses.clear();
        setTopNewses(topNews);
        if (mAdapter == null) {
            mAdapter = new TopNewsListAdapter(this);
            mAdapter.setDatas(mTopNewses);
            mNewsRecycler.setAdapter(mAdapter);
        } else {
            if (mTopNewses.size() != 0) {
                mAdapter.setDatas(mTopNewses);
            }
        }
        loadOrRefrshAble = true;
    }

    @Override
    public void loadMoreNews() {
        if (loadOrRefrshAble) {
            mPresenter.loadMore();
            loadOrRefrshAble = false;
        }
    }

    @Override
    public void loadMoreFail(String errorMsg) {
        loadOrRefrshAble = true;
    }

    @Override
    public void loadMoreSuccessed(ArrayList<TopNews> topNewses) {
        mTopNewses.addAll(topNewses);
        mAdapter.addDatas(topNewses);
        loadOrRefrshAble = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubcription();
    }

    public void setTopNewses(ArrayList<TopNews> topNewses) {
        mTopNewses = topNewses;
    }

    @Override
    public void onRefresh() {
        refreshNews();
    }
}
