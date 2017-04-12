package com.pandaq.pandaeye.modules.setting;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2017/3/31.
 * 相册列表Adapter
 */

public class AlbumAdapter extends RecyclerView.Adapter {
    private ArrayList<ImageFileBean> mImageBeen;
    private Context mContext;

    public AlbumAdapter(ArrayList<ImageFileBean> imageBeen, Context context) {
        mImageBeen = imageBeen;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final  int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.mTvFileCount.setText("" + mImageBeen.get(position).getImages().size() + "张");
        mHolder.mTvFileName.setText(mImageBeen.get(position).getFileName());
        Picasso.with(mContext)
                .load("file://" + mImageBeen.get(position).getTopImage())
                .fit()
                .centerCrop()
                .into(mHolder.mIvCover);
        mHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.OnItemClick(mImageBeen.get(position).getImages());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageBeen.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView mIvCover;
        @BindView(R.id.tv_file_name)
        TextView mTvFileName;
        @BindView(R.id.tv_file_count)
        TextView mTvFileCount;
        @BindView(R.id.rb_check_status)
        RadioButton mRbCheckStatus;
        @BindView(R.id.card_item)
        CardView mCardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(ArrayList<String> images);
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
