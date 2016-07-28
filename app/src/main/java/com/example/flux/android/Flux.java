package com.example.flux.android;

import com.example.flux.android.dispatcher.Dispatcher;
import com.example.flux.android.stores.Store;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ntop on 16/7/27.
 */
public class Flux {
    private static Map<Class, Store> stores = new HashMap<Class, Store>();
    private static Dispatcher dispatcher;

    public static void init(Dispatcher dispatcher) {
        Flux.dispatcher = dispatcher;
    }

    public static <T> T getStore(Class<T> clz) {
        return (T)stores.get(clz);
    }

    public static <T> void register(Class<? extends Store> clz) {
        Store store = newStore(clz);
        dispatcher.register(store);
        stores.put(clz, store);
    }

    private static <T> T newStore(Class<T> clz) {
        try {
            return clz.newInstance();
        } catch (Exception ex){}
        return null;
    }

    public static Dispatcher getDispatcher() {
        return dispatcher;
    }
}
