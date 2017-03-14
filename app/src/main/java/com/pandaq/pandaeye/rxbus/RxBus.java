package com.pandaq.pandaeye.rxbus;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by PandaQ on 2016/12/9.
 * email : 767807368@qq.com
 */

public class RxBus {

    private static volatile RxBus defaultInstance;

    private final Subject<Object, Object> bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例RxBus
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    // 发送一个新的事件，所有订阅此事件的订阅者都会收到
    public void post(Object action) {
        bus.onNext(action);
    }

    // 用 code 指定订阅此事件的对应 code 的订阅者
    public void postWithCode(int code, Object action) {
        bus.onNext(new Action(code, action));
    }


    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者,
    public <T> Observable<T> toObservableWithCode(final int code, Class<T> eventType) {
        return bus.ofType(Action.class)
                .filter(new Func1<Action, Boolean>() {
                    @Override
                    public Boolean call(Action action) {
                        return action.code == code;
                    }
                })
                .map(new Func1<Action, Object>() {
                    @Override
                    public Object call(Action action) {
                        return action.data;
                    }
                })
                .cast(eventType);
    }
}
