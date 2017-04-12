package com.pandaq.pandaeye.modules.zhihu.home;

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
import com.pandaq.pandaeye.modules.zhihu.home.mvp.ZhiHuStory;
import com.pandaq.pandaeye.utils.DateUtils;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/13.
 * email : 767807368@qq.com
 */
public class ZhihuDailyAdapter extends BaseRecyclerAdapter {

    private Context mContext;
    private int image_width;
    private int image_height;

    public ZhihuDailyAdapter(Fragment fragment) {
        mContext = fragment.getContext();
        float width = fragment.getResources().getDimension(R.dimen.news_image_width);
        image_width = (int) width;
        image_height = image_width * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view;
        if (viewType == RecyclerItemType.TYPE_TAGS.getiNum()) {
            view = LayoutInflater.from(mContext).inflate(R.layout.zhihu_story_date_tag, parent, false);
            return new TitleHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.zhihu_story_item, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, BaseItem data) {
        if (data.getItemType() == RecyclerItemType.TYPE_NORMAL) { //普通内容
            ZhiHuStory story = (ZhiHuStory) data.getData();
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.mNewsTitle.setText(story.getTitle());
            String image = story.getImages().get(0);
            if (!TextUtils.isEmpty(image))
                Picasso.with(mContext)
                        .load(image) //加载第一张图
                        .centerCrop()
                        .resize(image_width, image_height)
                        .into(holder.mNewsImage);
        } else if (data.getItemType() == RecyclerItemType.TYPE_TAGS) { //日期标签
            String title = (String) data.getData();
            int year = Integer.parseInt(title.substring(0, 4));
            int mon = Integer.parseInt(title.substring(4, 6));
            int day = Integer.parseInt(title.substring(6, 8));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, mon - 1, day);
            TitleHolder holder = (TitleHolder) viewHolder;
            holder.mItemTitle.setText(DateUtils.formatDate(calendar) + " | " + DateUtils.getWeek(calendar));
        }
    }

    class TitleHolder extends Holder {
        @BindView(R.id.item_title)
        TextView mItemTitle;

        TitleHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder extends Holder {
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