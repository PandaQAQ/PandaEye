package com.pandaq.pandaeye.modules.video.videodetail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.video.videodetail.mvp.MovieInfo;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2017/3/9.
 * 视频详情简介的 Adapter
 */

public class VideoInfoAdapter extends BaseRecyclerAdapter {

    private Context mContext;
    private int image_width;
    private int image_height;

    public VideoInfoAdapter(Fragment fragment) {
        mContext = fragment.getContext();
        float value = fragment.getResources().getDimension(R.dimen.video_type_card_height);
        image_width = (int) value;
        image_height = (int) value * 5 / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_type_item, parent, false);
        return new VideoInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, BaseItem data) {
        VideoInfoAdapter.ViewHolder holder = (VideoInfoAdapter.ViewHolder) viewHolder;
        MovieInfo.ListBean.ChildListBean listBean = (MovieInfo.ListBean.ChildListBean) data.getData();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.mRlParent.getLayoutParams();
        params.height = image_height;
        holder.mRlParent.setLayoutParams(params);
        String pic = listBean.getPic();
        if (!TextUtils.isEmpty(pic)) {
            Picasso.with(mContext)
                    .load(pic) //加载第一张图
                    .resize(image_width, image_height)
                    .into(holder.mIvVideoType);
        }
        holder.mTvVideoType.setText(listBean.getTitle());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_parent)
        RelativeLayout mRlParent;
        @BindView(R.id.iv_video_type)
        ImageView mIvVideoType;
        @BindView(R.id.tv_video_type)
        TextView mTvVideoType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            //获取到的是 SP 转换成 PX 后的值因此设置大小时要指定单位为 PX
            mTvVideoType.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.text_size_min));
        }
    }
}
