package com.pandaq.pandaeye.modules.news.newsdetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Description : 新闻详情实体类
 */
public class NewsContent implements Serializable {
    /**
     * docid
     */
    @SerializedName("docid")
    private String docid;
    /**
     * title
     */
    @SerializedName("title")
    private String title;
    /**
     * source
     */
    @SerializedName("source")
    private String source;
    /**
     * body
     */
    @SerializedName("body")
    private String body;
    /**
     * ptime
     */
    @SerializedName("ptime")
    private String ptime;
    /**
     * cover
     */
    @SerializedName("cover")
    private String cover;
    /**
     * 图片列表
     */
    @SerializedName("img")
    private List<ImgBean> img;


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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public static class ImgBean {
        /**
         * ref : <!--IMG#0-->
         * pixel : 584*421
         * alt :
         * src : http://dingyue.nosdn.127.net/CLeKeFBgqYWFGrj1rVqXmfCZHYydZMvsGKP7jVmGAdQZ61490139332009transferflag.png
         */

        private String ref;
        private String pixel;
        private String alt;
        private String src;

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getPixel() {
            return pixel;
        }

        public void setPixel(String pixel) {
            this.pixel = pixel;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
