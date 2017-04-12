package com.pandaq.pandaeye.modules.video;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PandaQ on 2017/2/28.
 * 搜索电影结果数据的实体类
 */

public class SearchData {

        /**
         * all : 64
         * movieNum : 9
         * pnum : 3
         * totalRecords : 64
         * trailerNum : 11
         * informationNum : 16
         * records : 100
         * otherNum : 28
         * list : [{"airTime":1997,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png","director":"格里芬·邓恩","videoType":"剧情,喜剧,爱情","description":"山姆深爱的未婚妻琳达，琳达却移情别恋毅然与他分手了。山姆没想到自己的无限量牺牲换不来女友的珍惜。山姆不愿就此放手，于是他在琳达与新男友安东尼的新居附近租下了房子，企图日夜监视琳达的生活，继续了解琳达是生活并希望琳达会有回心转意的一天。","pic":"http://yweb2.cnliveimg.com/img/CMCC_MOVIE/618565371_699022_HSJ1080V.jpg","title":"不知不觉爱上你","duration":"01:36:18","loadtype":"video","actors":"梅格·瑞恩,马修·布罗德里克,凯利·普雷斯顿","score":0,"dataId":"CMCC_00000000000000001_618565371","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_618565371","shareURL":"https://h5.svipmovie.com/jctj/CMCC_00000000000000001_618565371.shtml?fromTo=shoujimovie","region":"欧美"}]
         * totalPnum : 3
         */

        private String all;
        private String movieNum;
        private int pnum;
        private int totalRecords;
        private String trailerNum;
        private String informationNum;
        private int records;
        private String otherNum;
        private int totalPnum;
        private List<ListBean> list;

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public String getMovieNum() {
            return movieNum;
        }

        public void setMovieNum(String movieNum) {
            this.movieNum = movieNum;
        }

        public int getPnum() {
            return pnum;
        }

        public void setPnum(int pnum) {
            this.pnum = pnum;
        }

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public String getTrailerNum() {
            return trailerNum;
        }

        public void setTrailerNum(String trailerNum) {
            this.trailerNum = trailerNum;
        }

        public String getInformationNum() {
            return informationNum;
        }

        public void setInformationNum(String informationNum) {
            this.informationNum = informationNum;
        }

        public int getRecords() {
            return records;
        }

        public void setRecords(int records) {
            this.records = records;
        }

        public String getOtherNum() {
            return otherNum;
        }

        public void setOtherNum(String otherNum) {
            this.otherNum = otherNum;
        }

        public int getTotalPnum() {
            return totalPnum;
        }

        public void setTotalPnum(int totalPnum) {
            this.totalPnum = totalPnum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * airTime : 1997
             * angleIcon : http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png
             * director : 格里芬·邓恩
             * videoType : 剧情,喜剧,爱情
             * description : 山姆深爱的未婚妻琳达，琳达却移情别恋毅然与他分手了。山姆没想到自己的无限量牺牲换不来女友的珍惜。山姆不愿就此放手，于是他在琳达与新男友安东尼的新居附近租下了房子，企图日夜监视琳达的生活，继续了解琳达是生活并希望琳达会有回心转意的一天。
             * pic : http://yweb2.cnliveimg.com/img/CMCC_MOVIE/618565371_699022_HSJ1080V.jpg
             * title : 不知不觉爱上你
             * duration : 01:36:18
             * loadtype : video
             * actors : 梅格·瑞恩,马修·布罗德里克,凯利·普雷斯顿
             * score : 0
             * dataId : CMCC_00000000000000001_618565371
             * loadURL : https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_618565371
             * shareURL : https://h5.svipmovie.com/jctj/CMCC_00000000000000001_618565371.shtml?fromTo=shoujimovie
             * region : 欧美
             */

            private int airTime;
            private String angleIcon;
            private String director;
            private String videoType;
            private String description;
            private String pic;
            private String title;
            private String duration;
            private String loadtype;
            private String actors;
            private int score;
            private String dataId;
            private String loadURL;
            private String shareURL;
            private String region;

            public int getAirTime() {
                return airTime;
            }

            public void setAirTime(int airTime) {
                this.airTime = airTime;
            }

            public String getAngleIcon() {
                return angleIcon;
            }

            public void setAngleIcon(String angleIcon) {
                this.angleIcon = angleIcon;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public String getActors() {
                return actors;
            }

            public void setActors(String actors) {
                this.actors = actors;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDataId() {
                return dataId;
            }

            public void setDataId(String dataId) {
                this.dataId = dataId;
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

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }
        }
}
