package com.naah.admin.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * JsonUtils
 *
 * @author Naah
 * @date 2017年11月20日
 */
public class JsonUtils {
    private static Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    /**
     * json转对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {

        return gson.fromJson(json, clazz);
    }

    /**
     * json转list
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T[]> clazz) {
        T[] array = gson.fromJson(json, clazz);
        return new CopyOnWriteArrayList<>(Arrays.asList(array));
    }

    /**
     * json转数组
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T[] jsonToArray(String json, Class<T[]> clazz) {
        T[] array = gson.fromJson(json, clazz);
        return array;
    }

    /**
     * 对象转json
     *
     * @param obj
     * @return
     */
    public static String objectToJSON(Object obj) {
        return gson.toJson(obj).toString();
    }

     public static <T> T jsonToObject(String json,Type type) {

        return gson.fromJson(json, type);
    }

}
