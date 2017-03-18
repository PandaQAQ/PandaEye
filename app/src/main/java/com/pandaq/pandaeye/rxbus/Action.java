package com.pandaq.pandaeye.rxbus;

/**
 * Created by PandaQ on 2017/3/14.
 * rxbus 封装对象
 */

public class Action {
    public int code;
    public Object data;

    public Action(int code, Object data) {
        this.code = code;
        this.data = data;
    }
}
