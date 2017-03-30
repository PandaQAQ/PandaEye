package com.pandaq.pandaeye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.pandaq.pandaeye.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2017/3/30.
 * email:767807368@qq.com
 */

public class CheckPicAdapter extends BaseAdapter {
    private ArrayList<String> mPicPaths;
    private Context mContext;

    public CheckPicAdapter(Context context, ArrayList<String> picPaths) {
        mPicPaths = picPaths;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mPicPaths == null ? 0 : mPicPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.check_pic_item, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext)
                .load(mPicPaths.get(position))
                .into(holder.mIvPic);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView mIvPic;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setPicPaths(ArrayList<String> picPaths) {
        mPicPaths = picPaths;
        notifyDataSetChanged();
    }
}
