package com.example.eventbusdemo.eventbus;

import java.lang.reflect.Method;

public class SubscribleMethod {
    //方法体本省
    private Method mMethod;

    //线程模式
    private ThreadMode mTreadMode;

    //回调方法类型
    private Class<?> type;

    public SubscribleMethod(Method mMethod, ThreadMode mTreadMode, Class<?> type) {
        this.mMethod = mMethod;
        this.mTreadMode = mTreadMode;
        this.type = type;
    }

    public Method getmMethod() {
        return mMethod;
    }

    public void setmMethod(Method mMethod) {
        this.mMethod = mMethod;
    }

    public ThreadMode getmTreadMode() {
        return mTreadMode;
    }

    public void setmTreadMode(ThreadMode mTreadMode) {
        this.mTreadMode = mTreadMode;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
