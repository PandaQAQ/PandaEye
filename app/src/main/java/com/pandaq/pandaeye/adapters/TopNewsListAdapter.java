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
import com.pandaq.pandaeye.entity.NetEasyNews.TopNews;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/22.
 * email : 767807368@qq.com
 */

public class TopNewsListAdapter extends BaseRecyclerAdapter<TopNews> {

    private Fragment mFragment;

    public TopNewsListAdapter(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mFragment.getContext()).inflate(R.layout.topnews_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int RealPosition, TopNews data) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).mNewsTitle.setText(data.getTitle());
            ((ViewHolder) holder).mSource.setText(data.getSource());
            String image = data.getImgsrc();
            Glide.with(mFragment)
                    .load(image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((ViewHolder) holder).mNewsImage);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_image)
        ImageView mNewsImage;
        @BindView(R.id.news_title)
        TextView mNewsTitle;
        @BindView(R.id.source)
        TextView mSource;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
