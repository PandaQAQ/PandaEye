package com.pandaq.pandaeye.modules.douban.mvp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 电影条目实体类
 */
public class MovieSubject implements Serializable{

    /**
     * max : 10
     * average : 9
     * stars : 45
     * min : 0
     */

    private RatingBean rating;
    /**
     * rating : {"max":10,"average":9.6,"stars":"45","min":0}
     * genres : ["剧情"]
     * title : 楚门的世界
     * casts : [{"alt":"https://movie.douban.com/celebrity/1054438/","avatars":{"small":"http://img3.douban.com/img/celebrity/small/615.jpg","large":"http://img3.douban.com/img/celebrity/large/615.jpg","medium":"http://img3.douban.com/img/celebrity/medium/615.jpg"},"name":"金·凯瑞","id":"1054438"},{"alt":"https://movie.douban.com/celebrity/1053572/","avatars":{"small":"http://img3.doubanio.com/img/celebrity/small/516.jpg","large":"http://img3.doubanio.com/img/celebrity/large/516.jpg","medium":"http://img3.doubanio.com/img/celebrity/medium/516.jpg"},"name":"劳拉·琳妮","id":"1053572"},{"alt":"https://movie.douban.com/celebrity/1048024/","avatars":{"small":"http://img3.doubanio.com/img/celebrity/small/277.jpg","large":"http://img3.doubanio.com/img/celebrity/large/277.jpg","medium":"http://img3.doubanio.com/img/celebrity/medium/277.jpg"},"name":"艾德·哈里斯","id":"1048024"}]
     * collect_count : 483196
     * original_title : The Truman Show
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1022721/","avatars":{"small":"http://img3.douban.com/img/celebrity/small/4360.jpg","large":"http://img3.douban.com/img/celebrity/large/4360.jpg","medium":"http://img3.douban.com/img/celebrity/medium/4360.jpg"},"name":"彼得·威尔","id":"1022721"}]
     * year : 1998
     * images : {"small":"http://img3.douban.com/view/movie_poster_cover/ipst/public/p479682972.jpg","large":"http://img3.douban.com/view/movie_poster_cover/lpst/public/p479682972.jpg","medium":"http://img3.douban.com/view/movie_poster_cover/spst/public/p479682972.jpg"}
     * alt : https://movie.douban.com/subject/1292064/
     * id : 1292064
     */

    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    /**
     * small : http://img3.douban.com/view/movie_poster_cover/ipst/public/p479682972.jpg
     * large : http://img3.douban.com/view/movie_poster_cover/lpst/public/p479682972.jpg
     * medium : http://img3.douban.com/view/movie_poster_cover/spst/public/p479682972.jpg
     */

    private ImagesBean images;
    private String alt;
    private String id;
    private List<String> genres;
    /**
     * alt : https://movie.douban.com/celebrity/1054438/
     * avatars : {"small":"http://img3.douban.com/img/celebrity/small/615.jpg","large":"http://img3.douban.com/img/celebrity/large/615.jpg","medium":"http://img3.douban.com/img/celebrity/medium/615.jpg"}
     * name : 金·凯瑞
     * id : 1054438
     */

    private List<CastsBean> casts;
    /**
     * alt : https://movie.douban.com/celebrity/1022721/
     * avatars : {"small":"http://img3.douban.com/img/celebrity/small/4360.jpg","large":"http://img3.douban.com/img/celebrity/large/4360.jpg","medium":"http://img3.douban.com/img/celebrity/medium/4360.jpg"}
     * name : 彼得·威尔
     * id : 1022721
     */

    private List<DirectorsBean> directors;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    public static class RatingBean implements Serializable{
        private int max;
        private float average;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean implements Serializable{
        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class CastsBean implements Serializable{
        private String alt;
        /**
         * small : http://img3.douban.com/img/celebrity/small/615.jpg
         * large : http://img3.douban.com/img/celebrity/large/615.jpg
         * medium : http://img3.douban.com/img/celebrity/medium/615.jpg
         */

        private AvatarsBean avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBean implements Serializable{
            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class DirectorsBean implements Serializable{
        private String alt;
        /**
         * small : http://img3.douban.com/img/celebrity/small/4360.jpg
         * large : http://img3.douban.com/img/celebrity/large/4360.jpg
         * medium : http://img3.douban.com/img/celebrity/medium/4360.jpg
         */

        private AvatarsBean avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBean implements Serializable{
            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }
}
