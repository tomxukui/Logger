package com.ablingbling.library.logger.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xukui on 2018/1/23.
 */

public class GsonUtil {

    private static Gson gson = null;

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }

        return gson;
    }

    /**
     * Object -> Json
     */
    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    /**
     * Json -> Bean
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        return getGson().fromJson(json, cls);
    }

    /**
     * Json -> List<T>
     */
    public static <T> List<T> listFromJson(String json, Class<T> cls) {
        List<T> list = new ArrayList();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(getGson().fromJson(elem, cls));
        }
        return list;
    }

    /**
     * Json -> List<Map<String, T>>
     */
    public static <T> List<Map<String, T>> mapListFromJson(String json) {
        return getGson().fromJson(json, new TypeToken<List<Map<String, T>>>() {
        }.getType());
    }

    /**
     * Json -> Map<String, T>
     */
    public static <T> Map<String, T> mapFromJson(String json) {
        return getGson().fromJson(json, new TypeToken<Map<String, T>>() {
        }.getType());
    }

}
