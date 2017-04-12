package com.pandaq.pandaeye.modules.douban.mvp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * Top250接口返回数据实体
 */
public class MovieTop250 {
    @SerializedName("total")
    private int total;
    @SerializedName("count")
    private int count;
    @SerializedName("start")
    private int start;
    @SerializedName("title")
    private String title;
    @SerializedName("subjects")
    private ArrayList<MovieSubject> movieSubjects;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<MovieSubject> getMovieSubjects() {
        return movieSubjects;
    }

    public void setMovieSubjects(ArrayList<MovieSubject> movieSubjects) {
        this.movieSubjects = movieSubjects;
    }
}
