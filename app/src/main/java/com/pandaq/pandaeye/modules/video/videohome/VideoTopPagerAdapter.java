package com.pandaq.pandaeye.modules.video.videohome;

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

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.modules.video.videohome.mvp.RetDataBean;
import com.pandaq.pandaeye.modules.video.videodetail.VideoInfoActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PandaQ on 2017/3/1.
 * 视频 fragment 顶部的 ViewPager 适配器
 */

public class VideoTopPagerAdapter extends PagerAdapter {

    private List<RetDataBean.ListBean.ChildListBean> mBanders;
    private Context mContext;
    private Activity mActivity;

    public VideoTopPagerAdapter(Fragment fragment, List<RetDataBean.ListBean.ChildListBean> banders) {
        mContext = fragment.getContext();
        mActivity = fragment.getActivity();
        mBanders = banders;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.zhihutop_item, container, false);
        final ImageView mTopStoryImg = (ImageView) view.findViewById(R.id.top_story_img);
        String image = mBanders.get(position).getPic();
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
                Intent intent = new Intent(mContext, VideoInfoActivity.class);
                bundle.putString(Constants.BUNDLE_KEY_ID, mBanders.get(position).getDataId());
                bundle.putString(Constants.BUNDLE_KEY_TITLE, mBanders.get(position).getTitle());
                bundle.putString(Constants.BUNDLE_KEY_IMG_URL, mBanders.get(position).getPic());
                intent.putExtras(bundle);
//                //多个控件共享用pairs
//                Pair[] pairs = TranslateHelper.createSafeTransitionParticipants(mFragment.getActivity(), false,
//                        new Pair<>(mTopStoryImg, mFragment.getString(R.string.zhihu_story_img)));
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mFragment.getActivity(), pairs);
                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mBanders != null ? mBanders.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        notifyDataSetChanged();
    }

    public void resetData(List<RetDataBean.ListBean.ChildListBean> banders) {
        mBanders.clear();
        mBanders.addAll(banders);
        notifyDataSetChanged();
    }
}
