package com.pandaq.pandaeye.modules.video.videotypelist.mvp;

import java.util.List;

/**
 * Created by PandaQ on 2017/3/15.
 * 分类视频 json 对象
 */

public class TypedVideos {

    /**
     * pnum : 1
     * totalRecords : 30
     * records : 30
     * list : [{"airTime":0,"duration":"00:01:48","loadtype":"video","score":0,"angleIcon":"","dataId":"b020deb9331246ad8321d498014229a8","description":"@TV娱乐前线：14日，张智霖等艺人出席了某记者会，张智霖谈生女儿一事称顺其自然。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=b020deb9331246ad8321d498014229a8","shareURL":"https://h5.svipmovie.com/zxgl/b020deb9331246ad8321d498014229a8.shtml?fromTo=shoujimovie","pic":"http://y2.cnliveimg.com:8080/image/itv/2017/0315/b020deb9331246ad8321d498014229a8_195229_136.jpg","title":"张智霖称顺其自然生女","roomId":""}]
     * totalPnum : 520
     */

    private int pnum;
    private int totalRecords;
    private int records;
    private int totalPnum;
    private List<ListBean> list;

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

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
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

    public static class ListBean {
        /**
         * airTime : 0
         * duration : 00:01:48
         * loadtype : video
         * score : 0
         * angleIcon :
         * dataId : b020deb9331246ad8321d498014229a8
         * description : @TV娱乐前线：14日，张智霖等艺人出席了某记者会，张智霖谈生女儿一事称顺其自然。
         * loadURL : https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=b020deb9331246ad8321d498014229a8
         * shareURL : https://h5.svipmovie.com/zxgl/b020deb9331246ad8321d498014229a8.shtml?fromTo=shoujimovie
         * pic : http://y2.cnliveimg.com:8080/image/itv/2017/0315/b020deb9331246ad8321d498014229a8_195229_136.jpg
         * title : 张智霖称顺其自然生女
         * roomId :
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
        private String roomId;

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

        public void setScore(float score) {
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

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }
    }
}
