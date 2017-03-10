package com.pandaq.pandaeye.rxbus;

import rx.Observable;
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
    public RxBus() {
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

    // 发送一个新的事件
    public void post(Action action) {
        //如果发送的是一个异常的话，直接触发监听者的异常
        if (action.getActionData() instanceof Throwable) {
            bus.onError((Throwable) action.getActionData());
        } else {
            bus.onNext(action);
        }
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
//        ofType = filter + cast
//        return bus.filter(new Func1<Object, Boolean>() {
//            @Override
//            public Boolean call(Object o) {
//                return eventType.isInstance(o);
//            }
//        }) .cast(eventType);
    }
}
