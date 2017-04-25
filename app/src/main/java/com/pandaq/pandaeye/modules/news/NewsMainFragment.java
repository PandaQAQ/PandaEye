package com.pandaq.pandaeye.modules.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pandaq.pandaeye.BaseFragment;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.news.headline.HeadLineFragment;
import com.pandaq.pandaeye.modules.news.health.HealthFragment;
import com.pandaq.pandaeye.modules.news.military.MilitaryFragment;
import com.pandaq.pandaeye.modules.news.relaxing.RelaxFragment;
import com.pandaq.pandaeye.modules.news.sports.SportNewsFragment;
import com.pandaq.pandaeye.modules.news.technology.TecNewsFragment;
import com.pandaq.pandaeye.modules.news.travel.TravelFragment;
import com.pandaq.pandaeye.utils.DensityUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by PandaQ on 2016/9/9.
 * email : 767807368@qq.com
 * 新闻列表界面
 */
public class NewsMainFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.parentPanel)
    LinearLayout mParentPanel;
    private ArrayList<Fragment> mFragmentArrayList;
    @BindView(R.id.vp_news_list)
    ViewPager mVpNewsList;
    @BindView(R.id.tl_news_tabs)
    TabLayout mTlNewsTabs;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(new HeadLineFragment());
        mFragmentArrayList.add(new TecNewsFragment());
        mFragmentArrayList.add(new SportNewsFragment());
        mFragmentArrayList.add(new HealthFragment());
        mFragmentArrayList.add(new RelaxFragment());
        mFragmentArrayList.add(new MilitaryFragment());
        mFragmentArrayList.add(new TravelFragment());
        mVpNewsList.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentArrayList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentArrayList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getResources().getStringArray(R.array.news_title)[position];
            }
        });
        mVpNewsList.addOnPageChangeListener(this);
        mTlNewsTabs.setupWithViewPager(mVpNewsList);
        mTlNewsTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mVpNewsList.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
