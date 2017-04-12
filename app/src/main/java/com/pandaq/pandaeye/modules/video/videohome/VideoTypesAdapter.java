package com.pandaq.pandaeye.modules.video.videohome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.video.videohome.mvp.RetDataBean;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2017/3/1.
 * 视频主界面 recyclerView 的 adapter
 */

public class VideoTypesAdapter extends BaseRecyclerAdapter {

    private Context mContext;
    private int image_width;
    private int image_height;

    public VideoTypesAdapter(Fragment fragment) {
        mContext = fragment.getContext();
        float value = fragment.getResources().getDimension(R.dimen.video_type_card_height);
        image_height = (int) value;
        image_width = (int) value * 4 / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_type_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, BaseItem data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        RetDataBean.ListBean listBean = (RetDataBean.ListBean) data.getData();
        String pic = listBean.getChildList().get(0).getPic();
        if (!TextUtils.isEmpty(pic)) {
            Picasso.with(mContext)
                    .load(pic) //加载第一张图
                    .resize(image_width, image_height)
                    .into(holder.mIvVideoType);
        }
        holder.mTvVideoType.setText(listBean.getTitle());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video_type)
        ImageView mIvVideoType;
        @BindView(R.id.tv_video_type)
        TextView mTvVideoType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
