package com.pandaq.pandaeye.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.entity.ZhiHu.ZhiHuTopStory;
import com.pandaq.pandaeye.utils.TranslateHelper;
import com.pandaq.pandaeye.ui.zhihu.ZhihuStoryInfoActivity;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/14.
 * email : 767807368@qq.com
 */
public class ZhihuTopPagerAdapter extends PagerAdapter {

    private ArrayList<ZhiHuTopStory> mTopStories;
    private Fragment mFragment;

    public ZhihuTopPagerAdapter(Fragment fragment, ArrayList<ZhiHuTopStory> topStories) {
        this.mTopStories = topStories;
        this.mFragment = fragment;
    }

    @Override
    public int getCount() {
        return mTopStories.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.zhihutop_item, container, false);
        final ImageView mTopStoryImg = (ImageView) view.findViewById(R.id.top_story_img);
        TextView mTopStoryTitle = (TextView) view.findViewById(R.id.top_story_title);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.parentPanel);
        mTopStoryTitle.setText(mTopStories.get(position).getTitle());
        Glide.with(mFragment)
                .load(mTopStories.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mTopStoryImg);
        mTopStoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到其他界面
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mFragment.getActivity(), ZhihuStoryInfoActivity.class);
                bundle.putString(Constants.BUNDLE_KEY_TITLE, mTopStories.get(position).getTitle());
                bundle.putInt(Constants.BUNDLE_KEY_ID, mTopStories.get(position).getId());
                intent.putExtras(bundle);
                //多个控件共享用pairs
                Pair[] pairs = TranslateHelper.createSafeTransitionParticipants(mFragment.getActivity(), false,
                        new Pair<>(mTopStoryImg, mFragment.getString(R.string.zhihu_story_img)),
                        new Pair<>(relativeLayout, mFragment.getString(R.string.zhihu_story_parent)));
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mFragment.getActivity(), pairs);
                mFragment.getActivity().startActivity(intent, transitionActivityOptions.toBundle());
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
