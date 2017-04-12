package com.pandaq.pandaeye.modules.news;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 * 网易头条新闻实体类,不同类型的新闻知识外层json key不同，内部结构都为此类
 */

public class NewsBean implements Serializable {
    /**
     * docid
     */
    @SerializedName("docid")
    private String docid;
    /**
     * 标题
     */
    @SerializedName("title")
    private String title;
    /**
     * 小内容
     */
    @SerializedName("digest")
    private String digest;
    /**
     * 图片地址
     */
    @SerializedName("imgsrc")
    private String imgsrc;
    /**
     * 来源
     */
    @SerializedName("source")
    private String source;
    /**
     * 时间
     */
    @SerializedName("ptime")
    private String ptime;
    /**
     * url
     */
    @SerializedName("url")
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
