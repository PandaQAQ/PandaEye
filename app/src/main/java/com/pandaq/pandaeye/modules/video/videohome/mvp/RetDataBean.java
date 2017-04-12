package com.pandaq.pandaeye.modules.video.videohome.mvp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PandaQ on 2017/2/28.
 * 首页Json 数据的实体类
 */

public class RetDataBean implements Serializable{

    /**
     * ret : {"list":[{"showStyle":"","loadType":"videoList","changeOpenFlag":"false","line":1,"showType":"banner","childList":[{"airTime":0,"duration":"","loadType":"html","score":0,"angleIcon":"","dataId":"","description":"","loadURL":"http://h5.svipmovie.com/h5/2017oscars/index_app.html","shareURL":"","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/27/1488155797024092878.jpg","title":"不按套路出牌 实力登顶的大咖们","roomId":""},{"airTime":2016,"duration":"01:43:46","loadType":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624701699","description":"故事发生在一个所有哺乳类动物和谐共存的美好世界中，兔子朱迪（金妮弗·古德温 Ginnifer Goodwin 配音）从小就梦想着能够成为一名惩恶扬善的刑警，凭借着智慧和努力，朱迪成功的从警校中毕业进入了疯狂动物城警察局，殊不知这里是大型肉食类动物的领地，作为第一只，也是唯一的小型食草类动物，朱迪会遇到怎样的故事呢？ \r\n　　近日里，城中接连发生动物失踪案件，就在全部警员都致力于调查案件真相之时，朱迪却被局长（伊德瑞斯·艾尔巴 Idris Elba 配音）发配成为了一名无足轻重的交警。某日，正在执勤的兔子遇见了名为尼克（杰森·贝特曼 Jason Bateman 配音）的狐狸，两人不打不相识，之后又误打误撞的接受了寻找失踪的水獭先生的任务，如果不能在两天之内找到水獭先生，朱迪就必须自愿离开警局。朱迪找到了尼克，两人联手揭露了一个隐藏在疯狂动物城之中的惊天秘密。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624701699","shareURL":"https://h5.svipmovie.com/hlwyy/CMCC_00000000000000001_624701699.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/21/1487657076878019954.jpg","title":"89届奥斯卡最佳动画长片获奖影片","roomId":""},{"airTime":2016,"duration":"01:57:41","loadType":"video","score":2,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624733574","description":"上世纪20年代，日本为了对我国的侵略战争，秘密派遣大批间谍潜伏在上海。大批有志的进步人士，和这批日本侵略者以及他们身边的汉奸展开较量。直至抗日战争胜利，直至万恶的旧社会终结，迎来一个新社会。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624733574","shareURL":"https://h5.svipmovie.com/jctj/CMCC_00000000000000001_624733574.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/23/1487817048167015075.jpg","title":"大时代下风花雪月，旧上海里乱世浮沉","roomId":""},{"airTime":2016,"duration":"02:18:30","loadType":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624084693","description":"故事改编自二战上等兵军医戴斯蒙德·道斯的真实经历，他因为在冲绳岛战役中勇救75人生命而被授予美国国会荣誉勋章，同时也是首位获此荣誉的在战场上拒绝杀戮的医疗兵。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624084693","shareURL":"https://h5.svipmovie.com/hlwyy/CMCC_00000000000000001_624084693.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/10/1486694485488095783.jpg","title":"战争可灭信仰不死，史上最残酷战争片","roomId":""},{"airTime":2016,"duration":"01:52:48","loadType":"video","score":8,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624223340","description":"故事发生在德州，描写了19岁的年轻士兵比利·林恩与其他七名幸存的突击小队成员，因为在伊拉克与当地反对武装进行了3分43秒的激战，机缘巧合成为伊拉克战争中的国家英雄，被邀请至球赛的中场休息时亮相，而在此期间的一连串遭遇则使他逐步意识到关于战争的实质与国家的真相。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624223340","shareURL":"https://h5.svipmovie.com/hlwyy/CMCC_00000000000000001_624223340.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/17/1487326678530036068.jpg","title":"李安口碑新作，撼动人心的英雄之路","roomId":""},{"airTime":2016,"duration":"01:52:05","loadType":"video","score":10,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png","dataId":"CMCC_00000000000000001_622938394","description":"人气作家张嘉佳根据自己同名畅销小说集倾情改编并由张一白导演的一部让人笑出声来流下泪来的暖心之作。陈末（邓超 饰）被称为全城最贱，每天和王牌DJ小容针锋相对，谁也不知道他们的仇恨从何而来。陈末的两个兄弟，分别是全城最傻的猪头，全城最纯的茅十八，三人每天横冲直撞，以为可以自在生活，结果都面临人生最大的转折点。陈末相遇了最神秘的幺鸡，猪头打造了最惨烈的婚礼，茅十八经历了最悲伤的别离，这群人的生活一点点崩塌，往事一点点揭开。梦想，爱情，友情都离陈末远去。一个失去所有的人，已经弄丢自己的路，直到听到来自全世界的一段语音\u2026\u2026","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_622938394","shareURL":"https://h5.svipmovie.com/dkjc/CMCC_00000000000000001_622938394.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/12/08/1481165123891009000.jpg","title":"孤独邓超恋上咆哮张天爱","roomId":""},{"airTime":2016,"duration":"02:03:01","loadType":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624174847","description":"每座城市都有属于自己的传奇，这座城市的传奇就是\u201c摆渡人\u201d。据说，他们能消除世间痛苦。酒吧老板陈末（梁朝伟 饰）和合伙人管春（金城武 饰），平时看起来吊儿郎当，但其实是\u201c金牌摆渡人\u201d，空手接白刃，人肉千斤顶，只要你\u201c预约\u201d，就\u201c无所不能\u201d。邻居女孩小玉（杨颖 饰）为了偶像马力（陈奕迅 饰），预约了他们的服务，但在帮助小玉挑战整个城市的过程中，陈末和管春也逐渐发现了自己躲不过的问题。从欢天喜地的生活，到惊天动地的疯狂，摆渡人最辉煌的篇章，从这里开启。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624174847","shareURL":"https://h5.svipmovie.com/jctj/CMCC_00000000000000001_624174847.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/14/1487070577459042149.jpg","title":"每座城市都有属于自己的传奇","roomId":""}],"moreURL":"","title":"Banner","bigPicShowFlag":""}]}
     */

    @SerializedName("list")
    public List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * showStyle :
         * loadType : videoList
         * changeOpenFlag : false
         * line : 1
         * showType : banner
         * childList : [{"airTime":0,"duration":"","loadType":"html","score":0,"angleIcon":"","dataId":"","description":"","loadURL":"http://h5.svipmovie.com/h5/2017oscars/index_app.html","shareURL":"","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/27/1488155797024092878.jpg","title":"不按套路出牌 实力登顶的大咖们","roomId":""},{"airTime":2016,"duration":"01:43:46","loadType":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624701699","description":"故事发生在一个所有哺乳类动物和谐共存的美好世界中，兔子朱迪（金妮弗·古德温 Ginnifer Goodwin 配音）从小就梦想着能够成为一名惩恶扬善的刑警，凭借着智慧和努力，朱迪成功的从警校中毕业进入了疯狂动物城警察局，殊不知这里是大型肉食类动物的领地，作为第一只，也是唯一的小型食草类动物，朱迪会遇到怎样的故事呢？ \r\n　　近日里，城中接连发生动物失踪案件，就在全部警员都致力于调查案件真相之时，朱迪却被局长（伊德瑞斯·艾尔巴 Idris Elba 配音）发配成为了一名无足轻重的交警。某日，正在执勤的兔子遇见了名为尼克（杰森·贝特曼 Jason Bateman 配音）的狐狸，两人不打不相识，之后又误打误撞的接受了寻找失踪的水獭先生的任务，如果不能在两天之内找到水獭先生，朱迪就必须自愿离开警局。朱迪找到了尼克，两人联手揭露了一个隐藏在疯狂动物城之中的惊天秘密。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624701699","shareURL":"https://h5.svipmovie.com/hlwyy/CMCC_00000000000000001_624701699.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/21/1487657076878019954.jpg","title":"89届奥斯卡最佳动画长片获奖影片","roomId":""},{"airTime":2016,"duration":"01:57:41","loadType":"video","score":2,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624733574","description":"上世纪20年代，日本为了对我国的侵略战争，秘密派遣大批间谍潜伏在上海。大批有志的进步人士，和这批日本侵略者以及他们身边的汉奸展开较量。直至抗日战争胜利，直至万恶的旧社会终结，迎来一个新社会。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624733574","shareURL":"https://h5.svipmovie.com/jctj/CMCC_00000000000000001_624733574.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/23/1487817048167015075.jpg","title":"大时代下风花雪月，旧上海里乱世浮沉","roomId":""},{"airTime":2016,"duration":"02:18:30","loadType":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624084693","description":"故事改编自二战上等兵军医戴斯蒙德·道斯的真实经历，他因为在冲绳岛战役中勇救75人生命而被授予美国国会荣誉勋章，同时也是首位获此荣誉的在战场上拒绝杀戮的医疗兵。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624084693","shareURL":"https://h5.svipmovie.com/hlwyy/CMCC_00000000000000001_624084693.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/10/1486694485488095783.jpg","title":"战争可灭信仰不死，史上最残酷战争片","roomId":""},{"airTime":2016,"duration":"01:52:48","loadType":"video","score":8,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624223340","description":"故事发生在德州，描写了19岁的年轻士兵比利·林恩与其他七名幸存的突击小队成员，因为在伊拉克与当地反对武装进行了3分43秒的激战，机缘巧合成为伊拉克战争中的国家英雄，被邀请至球赛的中场休息时亮相，而在此期间的一连串遭遇则使他逐步意识到关于战争的实质与国家的真相。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624223340","shareURL":"https://h5.svipmovie.com/hlwyy/CMCC_00000000000000001_624223340.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/17/1487326678530036068.jpg","title":"李安口碑新作，撼动人心的英雄之路","roomId":""},{"airTime":2016,"duration":"01:52:05","loadType":"video","score":10,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437470518043130.png","dataId":"CMCC_00000000000000001_622938394","description":"人气作家张嘉佳根据自己同名畅销小说集倾情改编并由张一白导演的一部让人笑出声来流下泪来的暖心之作。陈末（邓超 饰）被称为全城最贱，每天和王牌DJ小容针锋相对，谁也不知道他们的仇恨从何而来。陈末的两个兄弟，分别是全城最傻的猪头，全城最纯的茅十八，三人每天横冲直撞，以为可以自在生活，结果都面临人生最大的转折点。陈末相遇了最神秘的幺鸡，猪头打造了最惨烈的婚礼，茅十八经历了最悲伤的别离，这群人的生活一点点崩塌，往事一点点揭开。梦想，爱情，友情都离陈末远去。一个失去所有的人，已经弄丢自己的路，直到听到来自全世界的一段语音\u2026\u2026","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_622938394","shareURL":"https://h5.svipmovie.com/dkjc/CMCC_00000000000000001_622938394.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/12/08/1481165123891009000.jpg","title":"孤独邓超恋上咆哮张天爱","roomId":""},{"airTime":2016,"duration":"02:03:01","loadType":"video","score":0,"angleIcon":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/11/18/1479437497755052776.png","dataId":"CMCC_00000000000000001_624174847","description":"每座城市都有属于自己的传奇，这座城市的传奇就是\u201c摆渡人\u201d。据说，他们能消除世间痛苦。酒吧老板陈末（梁朝伟 饰）和合伙人管春（金城武 饰），平时看起来吊儿郎当，但其实是\u201c金牌摆渡人\u201d，空手接白刃，人肉千斤顶，只要你\u201c预约\u201d，就\u201c无所不能\u201d。邻居女孩小玉（杨颖 饰）为了偶像马力（陈奕迅 饰），预约了他们的服务，但在帮助小玉挑战整个城市的过程中，陈末和管春也逐渐发现了自己躲不过的问题。从欢天喜地的生活，到惊天动地的疯狂，摆渡人最辉煌的篇章，从这里开启。","loadURL":"https://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=CMCC_00000000000000001_624174847","shareURL":"https://h5.svipmovie.com/jctj/CMCC_00000000000000001_624174847.shtml?fromTo=shoujimovie","pic":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/14/1487070577459042149.jpg","title":"每座城市都有属于自己的传奇","roomId":""}]
         * moreURL :
         * title : Banner
         * bigPicShowFlag :
         */
        @SerializedName("showStyle")
        private String showStyle;
        @SerializedName("loadType")
        private String loadType;
        @SerializedName("changeOpenFlag")
        private String changeOpenFlag;
        @SerializedName("line")
        private int line;
        @SerializedName("showType")
        private String showType;
        @SerializedName("moreURL")
        private String moreURL;
        @SerializedName("title")
        private String title;
        @SerializedName("bigPicShowFlag")
        private String bigPicShowFlag;
        @SerializedName("childList")
        private List<ChildListBean> childList;

        public String getShowStyle() {
            return showStyle;
        }

        public void setShowStyle(String showStyle) {
            this.showStyle = showStyle;
        }

        public String getLoadType() {
            return loadType;
        }

        public void setLoadType(String loadType) {
            this.loadType = loadType;
        }

        public String getChangeOpenFlag() {
            return changeOpenFlag;
        }

        public void setChangeOpenFlag(String changeOpenFlag) {
            this.changeOpenFlag = changeOpenFlag;
        }

        public int getLine() {
            return line;
        }

        public void setLine(int line) {
            this.line = line;
        }

        public String getShowType() {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public String getMoreURL() {
            return moreURL;
        }

        public void setMoreURL(String moreURL) {
            this.moreURL = moreURL;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBigPicShowFlag() {
            return bigPicShowFlag;
        }

        public void setBigPicShowFlag(String bigPicShowFlag) {
            this.bigPicShowFlag = bigPicShowFlag;
        }

        public List<ChildListBean> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildListBean> childList) {
            this.childList = childList;
        }

        public static class ChildListBean implements Serializable{
            /**
             * airTime : 0
             * duration :
             * loadType : html
             * score : 0
             * angleIcon :
             * dataId :
             * description :
             * loadURL : http://h5.svipmovie.com/h5/2017oscars/index_app.html
             * shareURL :
             * pic : http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/02/27/1488155797024092878.jpg
             * title : 不按套路出牌 实力登顶的大咖们
             * roomId :
             */

            private String airTime;
            private String duration;
            private String loadType;
            private float score;
            private String angleIcon;
            private String dataId;
            private String description;
            private String loadURL;
            private String shareURL;
            private String pic;
            private String title;
            private String roomId;

            public String getAirTime() {
                return airTime;
            }

            public void setAirTime(String airTime) {
                this.airTime = airTime;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getLoadType() {
                return loadType;
            }

            public void setLoadType(String loadType) {
                this.loadType = loadType;
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

            public String getRoomId() {
                return roomId;
            }

            public void setRoomId(String roomId) {
                this.roomId = roomId;
            }
        }
    }
}
