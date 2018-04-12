package com.example.wu.wanandroiddemo.util;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by wu on 2018/3/27.
 */

public class RxBus {

    private static volatile RxBus sRxBus;

    private final FlowableProcessor<Object> mBus;

    //PublishSubject只会把订阅发生的时间点之后来自原始Observable的数据发送个观察者
    public RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    //单例RxBus
    public static RxBus getInstance() {
        if (sRxBus == null) {
            synchronized (RxBus.class) {
                if (sRxBus == null) {
                    sRxBus = new RxBus();
                }
            }
        }
        return sRxBus;
    }

    //提供一个新的事件
    public void post(Object o) {
        mBus.onNext(o);
    }

    //根据传递的eventType类型返回特定类型（eventType）的被观察者
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }
}

