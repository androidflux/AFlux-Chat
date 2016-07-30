package com.example.flux.android.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntop on 16/7/30.
 */
public class utils {
    public static <T> T findOne(List<T> list, Filter<T> filter) {
        for(T t : list) {
            if (filter.apply(t)) {
                return t;
            }
        }
        return null;
    }

    public static <T> List<T> findAll(List<T> list, Filter<T> filter) {
        List<T> temp = new ArrayList<>();
        for(T t : list) {
            if (filter.apply(t)) {
                temp.add(t);
            }
        }
        return temp;
    }

    public static <T> List<T> delete(List<T> list, Filter<T> filter) {
        List<T> temp = new ArrayList<>();
        for(T t : list) {
            if (!filter.apply(t)) {
                temp.add(t);
            }
        }

        return temp;
    }

    public static <T,R> List<R> map(List<T> list, Map<T, R> map) {
        List<R> temp = new ArrayList<>();
        for(T t : list) {
            temp.add(map.convert(t));
        }
        return temp;
    }

    public static <T> int sum(List<T> list, Filter filter) {
        int n = 0;
        for(T t : list) {
            if (filter.apply(t)) {
                n ++;
            }
        }
        return n;
    }

    public interface Filter<T> {
        boolean apply(T t);
    }

    public interface Map<T, R> {
        R convert(T t);
    }
}
