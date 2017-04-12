package com.pandaq.pandaeye.modules.video.videodetail.mvp;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PandaQ on 2017/2/28.
 * 电影详情页的实体类
 */

public class MovieInfo implements Serializable {


    /**
     * airTime : 2016
     * couponNum : 0
     * ultraClearURL : http://cmcc.ips.cnlive.com/content/movie?contentId=625110524&productid=2028593060&ratelevel=3&devModel=2
     * HDURL : http://cmcc.ips.cnlive.com/content/movie?contentId=625110524&productid=2028593060&ratelevel=2&devModel=2
     * director : 周隼
     * videoType : 爱情,悬疑,剧情,犯罪
     * downloadURL :
     * htmlURL : https://h5.svipmovie.com/dkjc/CMCC_00000000000000001_625110524.shtml?fromTo=shoujimovie
     * description : 　　一天午后，袁氏家族的女主人接到了警方的电话，12年前被绑架撕票的小女儿阿樱，竟然还活着…… 　　12年前，年少的阿樱和姐姐争吵后，在洛杉矶的家中意外失踪。报警后不久，便传来阿樱被撕票的噩耗，袁家也决意离开伤心之地，举家搬回香港。阿樱失踪带来的伤痛和自责渐渐被时间抚平，一家人的生活逐渐归于平淡。阿樱的意外获救，让家人惊喜的同时，也让这家人的生活再起波澜。家里接二连三的发生命案，让姐姐渐渐对阿樱产生了怀疑……
     * list : [{"showType":"horizontal","childList":[{"airTime":2017,"duration":"00:01:12","loadtype":"video","score":0,"angleIcon":"","dataId":"CMCC_00000000000000001_623256747","description":"《那年夏天你去了哪里》心劫预告 胡歌饰演\u201c史上最坏角色\u201d","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_623256747","shareURL":"","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/12/26/1482744417596025324.jpg","title":"《那年夏天你去了哪里》心劫预"},{"airTime":2016,"duration":"00:46:53","loadtype":"video","score":0,"angleIcon":"","dataId":"882150d2928f534faabc6a00bf653e9ecc","description":"10月11日，悬疑电影《那年夏天你去了哪里》在北京举办发布会，当天，监制邓汉强、导演周隼、主演宋佳、林家栋、颜卓灵、胡歌等都到了现场。场上，片方公布了首款预告片，并宣布定档12月2日。出品人江志强表示，对电影非常的有信心，也希望可以在贺岁档中有一个比较好的票房。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=882150d2928f534faabc6a00bf653e9ecc","shareURL":"","pic":"http://y1.cnliveimg.com:8080/image/itv/2016/1011/2150d2928f534faabc6a00bf653e9ecc_100.jpg","title":"《那年夏天你去了哪里》发布会回放"},{"airTime":0,"duration":"00:00:39","loadtype":"video","score":0,"angleIcon":"","dataId":"CMCC_00000000000000001_622218439","description":"《那年夏天你去了哪里》宋佳颜卓灵\u201c姐妹\u201d貌合神离","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_622218439","shareURL":"","pic":"http://yweb1.cnliveimg.com/img/CMCC_MOVIE/622218439_336_220.jpg","title":"《那年夏天你去了哪里》宋佳颜卓灵\u201c姐妹\u201d貌合神离"}],"title":"相关视频"},{"showType":"vertical","childList":[{"airTime":2006,"duration":"01:54:39","loadtype":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png","dataId":"CMCC_00000000000000001_623970885","description":"硕英（李秉宪 饰）甚受女性欢迎，却为了寻找初恋情人静因（秀爱 饰）到电视台寻求帮助。这件事引起了记者洙真（李世恩 饰）与制片央宿（柳海真 饰）的注意，他们想发掘事情的真相。接着两人便开始寻找硕英与静因的过去，当线索越来越多，他们便发现了一段不平常的爱情故事。　　原来，硕英在1969年参加了到农村参加劳动的大学生活动，他不介意同伴们称呼他懒骨头，依旧游手好闲。一天，他遇到了失去父母的静因，更对静因一见钟情。他被静因坚强的个性深深吸引，静因也逐渐向他敞开了心扉。两人都十分享受爱情带来的甜蜜，可是夏天不知不觉就要过去了，活动也将要结束了\u2026\u2026","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_623970885","shareURL":"https://h5.svipmovie.com/rhjx/CMCC_00000000000000001_623970885.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/04/1486175374739082008.jpg","title":"那年夏天"},{"airTime":2011,"duration":"01:36:32","loadtype":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png","dataId":"CMCC_00000000000000001_602150511","description":"31岁的王昕蕾生于上海，海外留学后到北京担任某时尚杂志主编，她工作与交流方式直率，令不少员工和追求者颇感无可奈何。杂志社中层吴清晨来自青岛，大学毕业后一直留京发展多年，如今小有所成，并娶了北京姑娘姗姗为妻，但姗姗心高气傲，坦言嫁给他不过是一场迁就，作为经济适用男的吴清晨苦苦维系着这场婚姻。杂志社新丁陈曦刚刚从校门步入社会，所期望的是一份体面又能解决北京户口的工作。几个在北京打拼的外地人的故事，在寻...","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_602150511","shareURL":"https://h5.svipmovie.com/jctj/CMCC_00000000000000001_602150511.shtml?fromTo=shoujimovie","pic":"http://sdk2.cmvideo.cn:8082/isdk/zhengshi/3005/057/804/3005057804/display/V_CONTENT.jpg","title":"你是哪里人"},{"airTime":2012,"duration":"01:24:41","loadtype":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png","dataId":"CMCC_00000000000000001_622362520","description":"雷纳德与简正在经历婚姻危机，过去的一桩悲剧的阴影在俩人之间萦绕不断。为了重新点燃两人的激情，雷纳德与简共同旅行前去那不勒斯。在那里简遇到了美国男青年卡勒比，她的人生面临意想不到的变化。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_622362520","shareURL":"https://h5.svipmovie.com/hlwyy/CMCC_00000000000000001_622362520.shtml?fromTo=shoujimovie","pic":"http://yweb2.cnliveimg.com/img/CMCC_MOVIE/622362520_699022_HSJ1080V.jpg","title":"那年此时"},{"airTime":2015,"duration":"01:18:49","loadtype":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png","dataId":"CMCC_00000000000000001_601267810","description":"讲述了一对高中情侣，女孩患病将要不久人世，本来叛逆、不求上进的男孩却成熟起来，帮助女孩完成最后一个愿望，他带着女孩走遍附近所有的山、海边、公园，男孩带着相机把女孩最美的时刻一一记录下来。在一次拍摄日出的时候女孩忽然晕倒，醒来后就在家附近的医院。女孩对自己的身体很清楚，于是，她对男孩说把他们剩下的旅程走完，记录下最美的日出。把男孩支走后，女孩做了一个惊人的决定，她要像荆棘鸟一样，给男孩留下最后的礼物...","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_601267810","shareURL":"https://h5.svipmovie.com/jctj/CMCC_00000000000000001_601267810.shtml?fromTo=shoujimovie","pic":"http://sdk2.cmvideo.cn:8082/isdk/zhengshi/3003/903/029/3003903029/display/XX_V_CONTENT.jpg","title":"那年匆匆"},{"airTime":2003,"duration":"00:16:40","loadtype":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png","dataId":"CMCC_00000000000000001_600562007","description":"1960年夏天,在Lower Rhine地区的一小镇上,凯利·斯派拉斯正在为他9岁生日以及经历了一个神奇的夏天进行庆祝.他背着父母组建了一个动物园,里面养着一只猴子.他认为自己发现了爸爸的一个秘密:吸引爸爸起床的是迷人的邻居.他还体会到什么是初涉爱河以及初吻的滋味.在经历了这样一个充满友谊,秘密,爱情和快乐的夏天之后,凯利变了一个样.","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_600562007","shareURL":"https://h5.svipmovie.com/hlwyy/CMCC_00000000000000001_600562007.shtml?fromTo=shoujimovie","pic":"http://sdk2.cmvideo.cn:8082/isdk/zhengshi/3001/812/715/3001812715/display/XX_V_CONTENT.jpg","title":"第十个夏天"},{"airTime":2014,"duration":"01:27:24","loadtype":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png","dataId":"CMCC_00000000000000001_600655951","description":"毕业临近，校园里弥漫着各种分手的气息。而求职择业更是迫在眉睫，处处碰壁的音乐系硕士研究生林辰和闺蜜高雅决定帮男友孟里去大自然里采风完成毕业音乐作品《天籁》。毕业后孟里放弃对《天籁》的追求，听从了父母的安排带着自己不爱的校长女儿夏月出国继续深造。未婚生子的高雅没有选择对她百般体贴的医生顾歌，却依旧对情感骗子曹孟德抱有幻想。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_600655951","shareURL":"https://h5.svipmovie.com/jctj/CMCC_00000000000000001_600655951.shtml?fromTo=shoujimovie","pic":"http://sdk2.cmvideo.cn:8082/isdk/zhengshi/3002/223/841/3002223841/display/MYNNFSJ_V_CONTENT.jpg","title":"毕业那年分手季"}],"title":"猜你喜欢"}]
     * title : 那年夏天你去了哪里
     * SDURL :
     * smoothURL :
     * duration : 01:24:53
     * actors : 宋佳,颜卓灵,林家栋,胡歌
     */

    private int airTime;
    private int couponNum;
    private String ultraClearURL;
    private String HDURL;
    private String director;
    private String videoType;
    private String downloadURL;
    private String htmlURL;
    private String description;
    private String title;
    private String SDURL;
    private String smoothURL;
    private String duration;
    private String actors;
    private List<ListBean> list;

    public String getVideoUrl() {
        if (!TextUtils.isEmpty(HDURL))
            return HDURL;
        else if (!TextUtils.isEmpty(SDURL))
            return SDURL;
        else if (!TextUtils.isEmpty(smoothURL))
            return smoothURL;
        else
            return "";
    }

    public int getAirTime() {
        return airTime;
    }

    public void setAirTime(int airTime) {
        this.airTime = airTime;
    }

    public int getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(int couponNum) {
        this.couponNum = couponNum;
    }

    public String getUltraClearURL() {
        return ultraClearURL;
    }

    public void setUltraClearURL(String ultraClearURL) {
        this.ultraClearURL = ultraClearURL;
    }

    public String getHDURL() {
        return HDURL;
    }

    public void setHDURL(String HDURL) {
        this.HDURL = HDURL;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getHtmlURL() {
        return htmlURL;
    }

    public void setHtmlURL(String htmlURL) {
        this.htmlURL = htmlURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSDURL() {
        return SDURL;
    }

    public void setSDURL(String SDURL) {
        this.SDURL = SDURL;
    }

    public String getSmoothURL() {
        return smoothURL;
    }

    public void setSmoothURL(String smoothURL) {
        this.smoothURL = smoothURL;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * showType : horizontal
         * childList : [{"airTime":2017,"duration":"00:01:12","loadtype":"video","score":0,"angleIcon":"","dataId":"CMCC_00000000000000001_623256747","description":"《那年夏天你去了哪里》心劫预告 胡歌饰演\u201c史上最坏角色\u201d","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_623256747","shareURL":"","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/12/26/1482744417596025324.jpg","title":"《那年夏天你去了哪里》心劫预"},{"airTime":2016,"duration":"00:46:53","loadtype":"video","score":0,"angleIcon":"","dataId":"882150d2928f534faabc6a00bf653e9ecc","description":"10月11日，悬疑电影《那年夏天你去了哪里》在北京举办发布会，当天，监制邓汉强、导演周隼、主演宋佳、林家栋、颜卓灵、胡歌等都到了现场。场上，片方公布了首款预告片，并宣布定档12月2日。出品人江志强表示，对电影非常的有信心，也希望可以在贺岁档中有一个比较好的票房。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=882150d2928f534faabc6a00bf653e9ecc","shareURL":"","pic":"http://y1.cnliveimg.com:8080/image/itv/2016/1011/2150d2928f534faabc6a00bf653e9ecc_100.jpg","title":"《那年夏天你去了哪里》发布会回放"},{"airTime":0,"duration":"00:00:39","loadtype":"video","score":0,"angleIcon":"","dataId":"CMCC_00000000000000001_622218439","description":"《那年夏天你去了哪里》宋佳颜卓灵\u201c姐妹\u201d貌合神离","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_622218439","shareURL":"","pic":"http://yweb1.cnliveimg.com/img/CMCC_MOVIE/622218439_336_220.jpg","title":"《那年夏天你去了哪里》宋佳颜卓灵\u201c姐妹\u201d貌合神离"}]
         * title : 相关视频
         */

        private String showType;
        private String title;
        private List<ChildListBean> childList;

        public String getShowType() {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ChildListBean> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildListBean> childList) {
            this.childList = childList;
        }

        public static class ChildListBean implements Serializable {
            /**
             * airTime : 2017
             * duration : 00:01:12
             * loadtype : video
             * score : 0
             * angleIcon :
             * dataId : CMCC_00000000000000001_623256747
             * description : 《那年夏天你去了哪里》心劫预告 胡歌饰演“史上最坏角色”
             * loadURL : https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_623256747
             * shareURL :
             * pic : http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/12/26/1482744417596025324.jpg
             * title : 《那年夏天你去了哪里》心劫预
             */

            private int airTime;
            private String duration;
            private String loadtype;
            private float score;
            private String angleIcon;
            private String dataId;
            private String description;
            private String loadURL;
            private String shareURL;
            private String pic;
            private String title;

            public int getAirTime() {
                return airTime;
            }

            public void setAirTime(int airTime) {
                this.airTime = airTime;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getLoadtype() {
                return loadtype;
            }

            public void setLoadtype(String loadtype) {
                this.loadtype = loadtype;
            }

            public float getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getAngleIcon() {
                return angleIcon;
            }

            public void setAngleIcon(String angleIcon) {
                this.angleIcon = angleIcon;
            }

            public String getDataId() {
                return dataId;
            }

            public void setDataId(String dataId) {
                this.dataId = dataId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getLoadURL() {
                return loadURL;
            }

            public void setLoadURL(String loadURL) {
                this.loadURL = loadURL;
            }

            public String getShareURL() {
                return shareURL;
            }

            public void setShareURL(String shareURL) {
                this.shareURL = shareURL;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
