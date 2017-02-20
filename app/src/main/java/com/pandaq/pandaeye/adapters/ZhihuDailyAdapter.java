package com.pandaq.pandaeye.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.entity.ZhiHu.ZhiHuStory;
import com.pandaq.pandaeye.utils.DensityUtil;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/13.
 * email : 767807368@qq.com
 */
public class ZhihuDailyAdapter extends BaseRecyclerAdapter<ZhiHuStory> {

    private Fragment mFragment;
    private int image_width;
    private int image_height;
    private ArrayList<ZhiHuStory> mDatas = new ArrayList<>();

    public ZhihuDailyAdapter(Fragment fragment) {
        this.mFragment = fragment;
        float width = fragment.getResources().getDimension(R.dimen.news_image_width);
        image_width = DensityUtil.dip2px(mFragment.getContext(), width);
        image_height = image_width * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mFragment.getContext()).inflate(R.layout.zhihu_story_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, ZhiHuStory data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mNewsTitle.setText(data.getTitle());
        Glide.with(mFragment)
                .load(data.getImages().get(0)) //加载第一张图
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .override(image_width, image_height)
                .into(holder.mNewsImage);
    }

    class ViewHolder extends BaseRecyclerAdapter.Holder {
        @BindView(R.id.news_image)
        ImageView mNewsImage;
        @BindView(R.id.news_title)
        TextView mNewsTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
