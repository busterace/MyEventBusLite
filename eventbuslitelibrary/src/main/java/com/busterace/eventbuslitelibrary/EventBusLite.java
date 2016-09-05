package com.busterace.eventbuslitelibrary;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 轻量版的EventBus
 * Created by wudq on 2016/9/3.
 */
public class EventBusLite {
    private static EventBusLite eventBusLite;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private HashMap<Object, Method> map =new HashMap<>();


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


    public synchronized void  register(Object object) {
        Class<?> clazz = object.getClass();
        try {
            Method method = clazz.getMethod("onEventOnMainThread", Bundle.class);
            map.put(object,method);
            objectArrayList.add(object);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public synchronized void unregister(Object object) {
        objectArrayList.remove(object);
    }

    public void post(final String eventName, final Bundle bundle) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                for (Object object : objectArrayList) {
                    try {
                        Class<?> clazz = object.getClass();
                        Method onEventMethod = clazz.getMethod(eventName, Bundle.class);
                        onEventMethod.invoke(object, bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}
