package com.lyl.floatingwindows.utils;

public class ClassUtils {
    public static <T> T instance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) null;
    }
}
