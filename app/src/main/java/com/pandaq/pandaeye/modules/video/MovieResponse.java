package com.pandaq.pandaeye.modules.video;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PandaQ on 2017/2/28.
 * 电影实体包装类
 */

public class MovieResponse<T> {

    /**
     * msg : 成功
     * ret : {}
     * code : 200
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("ret")
    private T data;
    @SerializedName("code")
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setRet(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
