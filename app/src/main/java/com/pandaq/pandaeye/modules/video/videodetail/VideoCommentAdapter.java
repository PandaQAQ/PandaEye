package com.pandaq.pandaeye.modules.video.videodetail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.video.videodetail.mvp.CommentBean;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PandaQ on 2017/3/14.
 * 评论列表
 */

public class VideoCommentAdapter extends BaseRecyclerAdapter {
    private Context mContext;
    private int widthPx;
    private int heighPx;

    public VideoCommentAdapter(Fragment fragment) {
        mContext = fragment.getContext();
        float value = mContext.getResources().getDimension(R.dimen.comment_userimag_size);
        widthPx = (int) value;
        heighPx = (int) value;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, BaseItem data) {
        CommentBean.ListBean comment = (CommentBean.ListBean) data.getData();
        if (viewHolder instanceof ViewHolder) {
            ((ViewHolder) viewHolder).mTvLike.setText(String.valueOf(comment.getLikeNum()));
            ((ViewHolder) viewHolder).mTvUsername.setText(comment.getPhoneNumber());
            ((ViewHolder) viewHolder).mTvCommentText.setText(comment.getMsg());
            ((ViewHolder) viewHolder).mTvTimeText.setText(comment.getTime());
            String image =comment.getUserPic();
            if (!TextUtils.isEmpty(image)) {
                Picasso.with(mContext)
                        .load(image)
                        .resize(widthPx, heighPx)
                        .error(R.mipmap.ic_launcher)
                        .into(((ViewHolder) viewHolder).mCivUser);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_user)
        CircleImageView mCivUser;
        @BindView(R.id.tv_like)
        TextView mTvLike;
        @BindView(R.id.tv_comment_text)
        TextView mTvCommentText;
        @BindView(R.id.tv_time_text)
        TextView mTvTimeText;
        @BindView(R.id.tv_username)
        TextView mTvUsername;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
