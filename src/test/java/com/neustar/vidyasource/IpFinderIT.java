package com.neustar.vidyasource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Integration test to verify that <code>IPFinder</code> works within the Spring container. Real dependencies are
 * injected.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")
public class IpFinderIT {
    @Autowired
    private IpFinder ipFinder;

    /**
     * The test makes a REST call to the real Neustar IP Intelligence GeoPoint On-Demand (GPP) service and verifies the
     * output is as it should be for presentation on the front-end.
     */
    @Test
    public void testFindLocationBy() {
        Map<String, String> map = ipFinder.findLocationBy("70.174.135.25");
        assertEquals(map.get("state"), "virginia");
        assertEquals(map.get("area_code"), "703");
        assertEquals(map.get("continent"), "north america");
        assertEquals(map.get("state"), "virginia");
        assertEquals(map.get("country"), "united states");
    }

}
