package com.pandaq.pandaeye.model.neteasynews;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 * 网易头条返回实体中顶部binder实体类
 */

class TopNewsADs {
    private String docid;
    private String title;
    private String tag;
    private String imgsrc;
    private String subtitle;
    private String url;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
