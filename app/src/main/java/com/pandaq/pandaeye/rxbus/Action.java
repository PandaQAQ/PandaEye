package com.pandaq.pandaeye.rxbus;

/**
 * Created by PandaQ on 2017/3/10.
 * RxBus 传递 Action 的实体
 */

public class Action<T> {
    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionMsg() {
        return actionMsg;
    }

    public void setActionMsg(String actionMsg) {
        this.actionMsg = actionMsg;
    }

    public T getActionData() {
        return actionData;
    }

    public void setActionData(T actionData) {
        this.actionData = actionData;
    }

    // 消息传递 code
    private int actionCode;
    // 消息传递 信息
    private String actionMsg;
    // 消息传递数据实体
    private T actionData;
}
