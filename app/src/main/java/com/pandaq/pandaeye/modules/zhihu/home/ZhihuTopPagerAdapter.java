package com.pandaq.pandaeye.modules.zhihu.home;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.modules.zhihu.home.mvp.ZhiHuTopStory;
import com.pandaq.pandaeye.modules.zhihu.zhihudetail.ZhihuStoryInfoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/14.
 * email : 767807368@qq.com
 */
public class ZhihuTopPagerAdapter extends PagerAdapter {

    private ArrayList<ZhiHuTopStory> mTopStories;
    private Context mContext;
    private Activity mActivity;

    public ZhihuTopPagerAdapter(Fragment fragment, ArrayList<ZhiHuTopStory> topStories) {
        this.mTopStories = topStories;
        mActivity = fragment.getActivity();
        mContext = fragment.getContext();
    }

    @Override
    public int getCount() {
        return mTopStories != null ? mTopStories.size() : 0;
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
        mTopStoryTitle.setText(mTopStories.get(position).getTitle());
        String image = mTopStories.get(position).getImage();
        if (!TextUtils.isEmpty(image)) {
            Picasso.with(mContext)
                    .load(image)
                    .into(mTopStoryImg);
        }
        mTopStoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到其他界面
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mContext, ZhihuStoryInfoActivity.class);
                bundle.putString(Constants.BUNDLE_KEY_TITLE, mTopStories.get(position).getTitle());
                bundle.putInt(Constants.BUNDLE_KEY_ID, mTopStories.get(position).getId());
                bundle.putBoolean(Constants.BUNDLE_KEY_TRANSLATION_EXPORD, false);
                intent.putExtras(bundle);
                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void resetData(ArrayList<ZhiHuTopStory> topStories) {
        mTopStories.clear();
        mTopStories.addAll(topStories);
        notifyDataSetChanged();
    }
}
