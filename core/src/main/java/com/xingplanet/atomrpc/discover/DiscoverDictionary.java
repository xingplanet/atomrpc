package com.xingplanet.atomrpc.discover;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjin
 */
public class DiscoverDictionary {

    private final static ConcurrentHashMap<String, Set<String>> DICTIONARY = new ConcurrentHashMap<>();

    public static void put(String key, List<String> value) {
        Set<String> values = DICTIONARY.getOrDefault(key, new HashSet<>());
        // TODO value处理
        values.addAll(value);
        DICTIONARY.put(key, values);
    }

    public static void remove(String key, String value) {
        Set<String> values = DICTIONARY.get(key);
        if (values != null) {
            values.remove(value);
        }
    }

    public static Set<String> get(String key) {
        return DICTIONARY.get(key);
    }
}
