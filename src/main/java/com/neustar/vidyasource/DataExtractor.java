package com.neustar.vidyasource;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Takes a map with arbitrary values, which can be maps themselves, and produces a one-level data structure.
 */
@Component
public class DataExtractor {
    /**
     * "Flattens" the map as they say in functional programming.
     *
     * @param map Map to arbitrary depth
     * @return Map with only data (rather than other maps) for values
     */
    public Map<String, String> parseFrom(Map map) {
        Map<String, String> data = new HashMap<>();
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            if (value instanceof Map) {
                data.putAll(parseFrom((Map) value));  //Recursion FTW
            } else if (value != null) {
                data.put((String) key, value.toString());
            }
        }

        return data;
    }
}
