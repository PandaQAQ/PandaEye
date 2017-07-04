package com.pandaq.pandaeye.modules.webphto;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pandaq.pandaeye.disklrucache.SecretUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PandaQ on 2017/7/4.
 * 网页图片查看适配器
 */

public class ImagesAdapter extends PagerAdapter {

    private Map<String, ImageView> mImageViewMap = new HashMap<>();
    private ArrayList<String> imageUrls;
    private Context mContext;

    public ImagesAdapter(Context context, ArrayList<String> urls) {
        imageUrls = urls;
        mContext = context;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViewMap.get(SecretUtil.getMD5Result(imageUrls.get(position))));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = null;
        boolean hasImageView = mImageViewMap.containsKey(SecretUtil.getMD5Result(imageUrls.get(position)));
        if (hasImageView) {
            imageView = mImageViewMap.get(SecretUtil.getMD5Result(imageUrls.get(position)));
        } else {
            imageView = new ImageView(mContext);
            mImageViewMap.put(SecretUtil.getMD5Result(imageUrls.get(position)), imageView);
        }
        Picasso.with(mContext)
                .load(imageUrls.get(position))
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }
}
