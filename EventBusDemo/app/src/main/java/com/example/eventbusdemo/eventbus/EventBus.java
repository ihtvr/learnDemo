package com.example.eventbusdemo.eventbus;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class EventBus {
    private static volatile EventBus instance;

    private Map<Object, List<SubscribleMethod>> cacheMap;

    public EventBus() {
        cacheMap = new HashMap<>();
    }

    /**
     * 生成EventBus对象
     * 单例（双锁）
     *
     * @return
     */
    public static EventBus getDefault() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }


    /**
     * EventBus 注册
     * <p>
     * 讲所有obj中所有带 @Subscrble注解的方法存起来
     *
     * @param obj
     */
    public void register(Object obj) {
        //根据obj获取其所有已注解的方法
        List<SubscribleMethod> list = cacheMap.get(obj);
        if (list == null) {
            list = findSubscribleMethods(obj);
            cacheMap.put(obj, list);
        }
    }

    private List<SubscribleMethod> findSubscribleMethods(Object obj) {
        List<SubscribleMethod> list = new ArrayList<>();
        Class<?> clazz = obj.getClass();

        while (clazz != null) { //将父类Subscrbile注解的方法循环存入
            String className = clazz.getName();
            //排除系统类 系统类不会带有自定义注解（SubScrible)方法
            if (className.startsWith("java") || className.startsWith("javax") || className.startsWith("android"))
                break;
            Method[] methods = clazz.getDeclaredMethods();//获取当前类中的所有方法
//                                clazz.getMethods();//获取当前类 及其父类的 public方法
            for (Method method : methods) {
                //只寻找带有Subscrbile注解的方法
                Subscrbile subscrbile = method.getAnnotation(Subscrbile.class);
                if (subscrbile == null)
                    continue;
                //获取方法中的参数
                Class<?>[] types = method.getParameterTypes();
                if (types.length > 1) {
                    Log.e("ERROR", "参数错误");
                }
                ThreadMode threadMode = subscrbile.threadMode();
                SubscribleMethod subscribleMethod = new SubscribleMethod(method, threadMode, types[0]);
                list.add(subscribleMethod);
            }

            clazz = clazz.getSuperclass();//获取父类Class
        }
        return list;
    }

    public void post(Object type) {
        Set<Object> set = cacheMap.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()){
            Object object = iterator.next();
            List<SubscribleMethod> list = cacheMap.get(object);
            for(SubscribleMethod subscribleMethod:list){
                if(subscribleMethod.getType().isAssignableFrom(type.getClass())){
                    invoke(subscribleMethod,object,type);
                }
            }
        }
    }

    private void invoke(SubscribleMethod subscribleMethod, Object object, Object type) {
        Method method = subscribleMethod.getmMethod();
        try {
            method.invoke(object,type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
