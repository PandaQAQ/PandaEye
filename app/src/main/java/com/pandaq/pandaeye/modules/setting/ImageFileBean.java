package com.pandaq.pandaeye.modules.setting;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/3/31.
 * 读取相册图片后的相册bean类
 */

public class ImageFileBean {

    private String topImage;

    public String getTopImage() {
        return topImage;
    }

    public void setTopImage(String topImage) {
        this.topImage = topImage;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private ArrayList<String> images;
    private String fileName;
}
