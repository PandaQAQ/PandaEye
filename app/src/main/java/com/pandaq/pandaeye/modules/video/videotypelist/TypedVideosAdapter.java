package com.pandaq.pandaeye.modules.video.videotypelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.video.videotypelist.mvp.TypedVideos;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2017/3/15.
 * 分类视频的 Adapter
 */

public class TypedVideosAdapter extends BaseRecyclerAdapter {
    private Context mContext;
    private int image_width;
    private int image_height;

    public TypedVideosAdapter(Context context) {
        mContext = context;
        float value = mContext.getResources().getDimension(R.dimen.typed_video_item_height);
        image_width = (int) value * 3 / 4;
        image_height = (int) value;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.typed_video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, BaseItem data) {
        TypedVideos.ListBean listBean = (TypedVideos.ListBean) data.getData();
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTvVideoName.setText(listBean.getTitle());
        holder.mTvAirTime.setText(mContext.getString(R.string.air_time) + listBean.getAirTime() + "");
        holder.mTvDuration.setText(mContext.getString(R.string.duration) + listBean.getDuration() + "");
        holder.mRbVideoStarts.setRating(listBean.getScore() / 2);
        holder.mTvVideoRate.setText(String.valueOf(listBean.getScore()));
        String image = listBean.getPic();
        if (!TextUtils.isEmpty(image)) {
            Picasso.with(mContext)
                    .load(image)
                    .resize(image_width, image_height)
                    .centerCrop()
                    .into(holder.mIvVideo);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video)
        ImageView mIvVideo;
        @BindView(R.id.tv_video_name)
        TextView mTvVideoName;
        @BindView(R.id.rb_video_starts)
        RatingBar mRbVideoStarts;
        @BindView(R.id.tv_video_rate)
        TextView mTvVideoRate;
        @BindView(R.id.tv_airTime)
        TextView mTvAirTime;
        @BindView(R.id.tv_duration)
        TextView mTvDuration;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
