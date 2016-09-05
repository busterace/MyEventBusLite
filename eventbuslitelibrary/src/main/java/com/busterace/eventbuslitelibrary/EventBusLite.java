package com.busterace.eventbuslitelibrary;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 轻量版的EventBus
 * Created by wudq on 2016/9/3.
 */
public class EventBusLite {
    private static EventBusLite eventBusLite;
    private Handler handler = new Handler(Looper.getMainLooper());
    private HashMap<Object, Method> map = new HashMap<>();


    private EventBusLite() {
    }

    public static EventBusLite getDefault() {
        if (eventBusLite == null) {
            synchronized (EventBusLite.class) {
                if (eventBusLite == null) {
                    eventBusLite = new EventBusLite();
                }
            }
        }
        return eventBusLite;
    }


    public void register(Object object) {
        if (isRegister(object)){
            return;
        }
        synchronized (EventBusLite.class){
            try {
                if (!isRegister(object)){
                    Class<?> clazz = object.getClass();
                    Method method = clazz.getMethod("onEventOnMainThread", Bundle.class);
                    map.put(object, method);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean isRegister(Object object) {
        return map.containsKey(object);
    }

    public void unregister(Object object) {
        if (!isRegister(object)){
            return;
        }
        synchronized (EventBusLite.class){
            if (isRegister(object)){
                map.remove(object);
            }
        }
    }

    public void post(final Bundle bundle) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<Object, Method> entry : map.entrySet()) {
                    Method method = entry.getValue();
                    try {
                        method.invoke(entry.getKey(), bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}
