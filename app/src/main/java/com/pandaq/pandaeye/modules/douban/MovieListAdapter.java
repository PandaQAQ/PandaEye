package com.pandaq.pandaeye.modules.douban;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.douban.mvp.MovieSubject;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/12.
 * email : 767807368@qq.com
 */
public class MovieListAdapter extends BaseRecyclerAdapter {

    private Fragment mFragment;

    public MovieListAdapter(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, BaseItem data) {
        MovieSubject movieSubject = (MovieSubject) data.getData();
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mMovieName.setText(movieSubject.getTitle());
        float rate = movieSubject.getRating().getAverage();
        holder.mMovieRate.setText(toString(rate));
        holder.mMovieStarts.setRating(rate / 2);
        String image = movieSubject.getImages().getMedium();
        if (!TextUtils.isEmpty(image)) {
            Picasso.with(mFragment.getContext())
                    .load(image)
                    .into(holder.mMoviePic);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_pic)
        ImageView mMoviePic;
        @BindView(R.id.movie_name)
        TextView mMovieName;
        @BindView(R.id.movie_starts)
        RatingBar mMovieStarts;
        @BindView(R.id.movie_rate)
        TextView mMovieRate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private String toString(Object string) {
        return string + "";
    }

}
