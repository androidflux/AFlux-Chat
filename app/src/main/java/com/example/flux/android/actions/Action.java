package com.example.flux.android.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ntop on 18/12/15.
 */
public class Action<T> {
    private final String type;
    private final T data;

    Map<String,Object> extra = new HashMap<>();

    public Action(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    public void putExtra(String key, Object object) {
        extra.put(key, object);
    }

    public<T> T getExtra(String key) {
        return (T)extra.get(key);
    }
}
