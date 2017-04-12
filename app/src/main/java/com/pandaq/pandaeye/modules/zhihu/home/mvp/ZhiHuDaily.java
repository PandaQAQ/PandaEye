package com.pandaq.pandaeye.modules.zhihu.home.mvp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/13.
 * email : 767807368@qq.com
 */
public class ZhiHuDaily implements Serializable{

    /**
     * date : 20160913
     * stories : [{"images":["http://pic4.zhimg.com/0f13af30abcd22ca3d7215726397d7ef.jpg"],"type":0,"id":8789711,"ga_prefix":"091310","title":"疫苗从研发到投入市场需要哪些步骤？"},{"images":["http://pic1.zhimg.com/6d803d4e20c6a8846591498bf7c849d8.jpg"],"type":0,"id":8789169,"ga_prefix":"091309","title":"警察实施抓捕时有没有必要避开孩子？"},{"images":["http://pic1.zhimg.com/788c5c8d099e0f9b4bc6e31cd34d517c.jpg"],"type":0,"id":8789606,"ga_prefix":"091308","title":"还是包子铺的例子，说说为什么「现金流比利润重要」"},{"images":["http://pic4.zhimg.com/9d9b868f2a3e46f0839b87abd1b3e983.jpg"],"type":0,"id":8786267,"ga_prefix":"091307","title":"培养孩子的创造力，可以参考这个例子里的六个步骤"},{"images":["http://pic1.zhimg.com/921dd6c5d2477a0fc39fac3049e6f6ac.jpg"],"type":0,"id":8789583,"ga_prefix":"091307","title":"苹果实在是太想让你\u000b丢掉那根线了，为此不惜砍掉耳机口"},{"images":["http://pic1.zhimg.com/98919b43f382b8206361aa0920daec14.jpg"],"type":0,"id":8787843,"ga_prefix":"091307","title":"和平民结婚的王室成员越来越多，贵族还跟过去一样吗？"},{"images":["http://pic3.zhimg.com/97921c61c2e9aaca92f46272157a0a96.jpg"],"type":0,"id":8789511,"ga_prefix":"091307","title":"读读日报 24 小时热门 TOP 5 · 女版毒枭"},{"images":["http://pic1.zhimg.com/66d620916e26b4d65feb84177c6d0230.jpg"],"type":0,"id":8788036,"ga_prefix":"091306","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic1.zhimg.com/e0d81917fc92e0437d62f6d9f3385078.jpg","type":0,"id":8789583,"ga_prefix":"091307","title":"苹果实在是太想让你\u000b丢掉那根线了，为此不惜砍掉耳机口"},{"image":"http://pic2.zhimg.com/ea4c5d5087e4f0b75f13265d512471c5.jpg","type":0,"id":8789511,"ga_prefix":"091307","title":"读读日报 24 小时热门 TOP 5 · 女版毒枭"},{"image":"http://pic1.zhimg.com/261cd537adcc97c717c870a9f3bc6534.jpg","type":0,"id":8789169,"ga_prefix":"091309","title":"警察实施抓捕时有没有必要避开孩子？"},{"image":"http://pic4.zhimg.com/6a5f7544b5ab519497f67548b35f7c3b.jpg","type":0,"id":8788023,"ga_prefix":"091217","title":"知乎好问题 · 为什么刷朋友圈让人「着迷又焦虑」？"},{"image":"http://pic4.zhimg.com/19cbdd9a5f0f4066a2844b3fb13698a3.jpg","type":0,"id":8785325,"ga_prefix":"091211","title":"不谈钱，只谈杨振宁说「不宜建造」的大型对撞机是什么"}]
     */
    @SerializedName("date")
    private String date;
    /**
     * images : ["http://pic4.zhimg.com/0f13af30abcd22ca3d7215726397d7ef.jpg"]
     * type : 0
     * id : 8789711
     * ga_prefix : 091310
     * title : 疫苗从研发到投入市场需要哪些步骤？
     */
    @SerializedName("stories")
    private ArrayList<ZhiHuStory> stories;
    /**
     * image : http://pic1.zhimg.com/e0d81917fc92e0437d62f6d9f3385078.jpg
     * type : 0
     * id : 8789583
     * ga_prefix : 091307
     * title : 苹果实在是太想让你丢掉那根线了，为此不惜砍掉耳机口
     */
    @SerializedName("top_stories")
    private ArrayList<ZhiHuTopStory> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhiHuStory> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhiHuStory> stories) {
        this.stories = stories;
    }

    public ArrayList<ZhiHuTopStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(ArrayList<ZhiHuTopStory> top_stories) {
        this.top_stories = top_stories;
    }

}
