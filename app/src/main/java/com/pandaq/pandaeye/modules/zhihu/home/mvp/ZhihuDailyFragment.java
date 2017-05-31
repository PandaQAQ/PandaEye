package com.pandaq.pandaeye.modules.zhihu.home.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.zhihu.home.ZhihuDailyAdapter;
import com.pandaq.pandaeye.modules.zhihu.home.ZhihuTopPagerAdapter;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.rxbus.RxConstants;
import com.pandaq.pandaeye.BaseFragment;
import com.pandaq.pandaeye.utils.DateUtils;
import com.pandaq.pandaeye.utils.TagAnimationUtils;
import com.pandaq.pandaeye.modules.zhihu.zhihudetail.ZhihuStoryInfoActivity;
import com.pandaq.pandaqlib.loopbander.AutoScrollViewPager;
import com.pandaq.pandaqlib.loopbander.ViewGroupIndicator;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by PandaQ on 2016/9/9.
 * email : 767807368@qq.com
 * 知乎日报列表Fragment
 */
public class ZhihuDailyFragment extends BaseFragment implements ZhiHuHomeContract.View, SwipeRefreshLayout.OnRefreshListener,
        MagicRecyclerView.OnTagChangeListener, BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.zhihudaily_list)
    MagicRecyclerView mZhihudailyList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.tv_tag)
    TextView mTvTag;
    @BindView(R.id.empty_msg)
    TextView mEmptyMsg;
    private ZhiHuPresenter mPresenter = new ZhiHuPresenter(this);
    private ZhihuDailyAdapter mZhihuDailyAdapter;
    private ArrayList<BaseItem> mBaseItems;
    private AutoScrollViewPager scrollViewPager;
    private ViewGroupIndicator viewGroupIndicator;
    private ZhihuTopPagerAdapter mTopPagerAdapter;
    private boolean initTag;
    private boolean loading = false;
    private Disposable mDisposable;
    private LinearLayoutManager mLinearLayoutManager;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihulist_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
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

    private void initView() {
        mBaseItems = new ArrayList<>();
        mZhihudailyList.setItemAnimator(new DefaultItemAnimator());
        mZhihudailyList.getItemAnimator().setChangeDuration(0);
        mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mZhihudailyList.setLayoutManager(mLinearLayoutManager);
        mZhihudailyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mZhihudailyList.refreshAble()) {
                    mRefresh.setEnabled(true);
                }
                if (mZhihudailyList.loadAble()) {
                    loadMoreData();
                }
                if (mZhihudailyList.tagGone() && mTvTag.getVisibility() == View.VISIBLE) {
                    hideTagAnim(mTvTag);
                    mTvTag.setVisibility(View.GONE);
                }
            }
        });
        mRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white_FFFFFF));
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        FrameLayout headerView = (FrameLayout) mZhihudailyList.getHeaderView();
        scrollViewPager = (AutoScrollViewPager) headerView.findViewById(R.id.scroll_pager);
        viewGroupIndicator = (ViewGroupIndicator) headerView.findViewById(R.id.scroll_pager_indicator);
        mPresenter.loadCache();
        refreshData();
        mZhihudailyList.addOnTagChangeListener(this);
    }

    @Override
    public void showRefreshBar() {
        if (!mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(true);
        }
    }

    @Override
    public void hideRefreshBar() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void refreshData() {
        initTag = false;
        mPresenter.refreshZhihuDaily();
    }

    @Override
    public void refreshSuccessed(ZhiHuDaily stories) {
        if (stories == null || stories.getStories().size() <= 0) {
            mEmptyMsg.setVisibility(View.VISIBLE);
            mZhihudailyList.setVisibility(View.INVISIBLE);
            mRefresh.requestFocus();
            return;
        } else {
            mEmptyMsg.setVisibility(View.GONE);
            mZhihudailyList.setVisibility(View.VISIBLE);
        }
        mBaseItems.clear();
        //配置顶部故事
        if (mTopPagerAdapter == null) {
            mTopPagerAdapter = new ZhihuTopPagerAdapter(this, stories.getTop_stories());
            scrollViewPager.setAdapter(mTopPagerAdapter);
        } else {
            mTopPagerAdapter.resetData(stories.getTop_stories());
            mTopPagerAdapter.notifyDataSetChanged();
        }
        viewGroupIndicator.setParent(scrollViewPager);
        //配置底部列表故事
        for (ZhiHuStory story : stories.getStories()) {
            if (!initTag) {
                initTag = true;
                BaseItem<String> baseItem = new BaseItem<>();
                baseItem.setData(stories.getDate());
                baseItem.setItemType(BaseRecyclerAdapter.RecyclerItemType.TYPE_TAGS);
                mBaseItems.add(baseItem);
            }
            BaseItem<ZhiHuStory> baseItem = new BaseItem<>();
            baseItem.setData(story);
            mBaseItems.add(baseItem);
        }
        if (mZhihuDailyAdapter == null) {
            mZhihuDailyAdapter = new ZhihuDailyAdapter(this);
            mZhihuDailyAdapter.setBaseDatas(mBaseItems);
            mZhihudailyList.setAdapter(mZhihuDailyAdapter);
            //实质是是对 adapter 设置点击事件所以需要在设置 adapter 之后调用
            mZhihudailyList.addOnItemClickListener(this);
        } else {
            if (mBaseItems.size() != 0) {
                mZhihuDailyAdapter.setBaseDatas(mBaseItems);
            }
        }
    }

    @Override
    public void refreshFail(String errMsg) {
        if (mZhihuDailyAdapter == null) {
            mEmptyMsg.setVisibility(View.VISIBLE);
            mZhihudailyList.setVisibility(View.INVISIBLE);
            mRefresh.requestFocus();
        }
    }

    @Override
    public void loadMoreData() {
        if (!loading) {
            loading = true;
            mPresenter.loadMoreData();
        }
    }

    @Override
    public void loadSuccessed(ArrayList<BaseItem> stories) {
        mBaseItems.addAll(stories);
        mZhihuDailyAdapter.addBaseDatas(stories);
        loading = false;
    }

    @Override
    public void loadFail(String errMsg) {
        loading = false;
    }

    @Override
    public void onRefresh() {
        refreshData();
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
                            if (value.equals(RxConstants.BACK_PRESSED_DATA) && mZhihudailyList != null) {
                                //滚动到顶部
                                mLinearLayoutManager.smoothScrollToPosition(mZhihudailyList, null, 0);
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
    public void onChange(String newTag) {
        if (mTvTag.getVisibility() == View.GONE) {
            mTvTag.setVisibility(View.VISIBLE);
            showTagAnim(mTvTag);
        }
        int year = Integer.parseInt(newTag.substring(0, 4));
        int mon = Integer.parseInt(newTag.substring(4, 6));
        int day = Integer.parseInt(newTag.substring(6, 8));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mon - 1, day);
        mTvTag.setText(DateUtils.formatDate(calendar));
    }

    private void hideTagAnim(final View view) {
        Animation animation = TagAnimationUtils.moveToViewTop();
        view.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void showTagAnim(final View view) {
        Animation animation = TagAnimationUtils.moveToViewLocation();
        view.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onItemClick(int position, BaseItem data, View view) {
        //跳转到其他界面
        ZhiHuStory story = (ZhiHuStory) data.getData();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(ZhihuDailyFragment.this.getActivity(), ZhihuStoryInfoActivity.class);
        bundle.putString(Constants.BUNDLE_KEY_TITLE, story.getTitle());
        bundle.putInt(Constants.BUNDLE_KEY_ID, story.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
