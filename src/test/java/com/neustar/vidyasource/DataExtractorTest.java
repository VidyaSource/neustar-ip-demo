package com.neustar.vidyasource;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit test to verify that <code>DataExtractor</code> recursion works properly.
 */
public class DataExtractorTest {
    private DataExtractor dataExtractor;

    @Before
    public void setUp() {
        dataExtractor = new DataExtractor();
    }

    /**
     * Easiest case with one-dimensional map. Should return the same.
     */
    @Test
    public void testParse1Level() {
        Map map = new HashMap();
        map.put("key", "value");
        Map<String, String> data = dataExtractor.parseFrom(map);
        assertEquals(data.size(), 1);
        assertEquals(data.get("key"), "value");
    }

    /**
     * Should flatten the map with a nested map.
     */
    @Test
    public void testParse2Levels() {
        Map map = new HashMap();
        Map map2 = new HashMap();
        map.put("key", map2);
        map2.put("city", "Vienna");
        map2.put("state", "VA");
        map2.put("zip", "22180");
        Map<String, String> data = dataExtractor.parseFrom(map);
        assertEquals(data.size(), 3);
        assertEquals(data.get("city"), "Vienna");
        assertEquals(data.get("state"), "VA");
        assertEquals(data.get("zip"), "22180");
    }

    /**
     * Ignore null values in the map
     */
    @Test
    public void testParseWithNull() {
        Map map = new HashMap();
        Map map2 = new HashMap();
        map.put("key", map2);
        map2.put("city", "Vienna");
        map2.put("state", "VA");
        map2.put("zip", null);
        Map<String, String> data = dataExtractor.parseFrom(map);
        assertEquals(data.size(), 2);
        assertEquals(data.get("city"), "Vienna");
        assertEquals(data.get("state"), "VA");
    }
}
