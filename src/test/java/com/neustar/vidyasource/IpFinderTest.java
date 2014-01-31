package com.neustar.vidyasource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Unit test to verify that <code>IPFinder</code> works when it is provided the proper dependencies, which are injected
 * "manually" after being mocked.
 */
@RunWith(JUnit4.class)
public class IpFinderTest {
    private IpFinder ipFinder;
    private MockRestServiceServer mockServer;
    private SignatureMaker signatureMaker;
    private DataExtractor dataExtractor;

    /**
     * This test uses Spring's ReflectionTestUtils in order to inject the expected dependencies without having to create
     * setters just to make the test work.
     * <br></br>
     * The test also uses EasyMock to mock <code>SignatureMaker</code> and Spring's <code>MockRestServiceServer</code> to mock the call made
     * with RestTemplate by <code>IpFinder</code>.
     */
    @Before
    public void setUp() {
        ipFinder = new IpFinder();
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        signatureMaker = createMock(SignatureMaker.class);
        dataExtractor = createMock(DataExtractor.class);
        ReflectionTestUtils.setField(ipFinder, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(ipFinder, "signatureMaker", signatureMaker);
        ReflectionTestUtils.setField(ipFinder, "serviceBaseUrl", "/test/ip/{ip}?apikey={apiKey}&sig={sig}&format=json");
        ReflectionTestUtils.setField(ipFinder, "apiKey", "key");
        ReflectionTestUtils.setField(ipFinder, "dataExtractor", dataExtractor);
    }

    /**
     * The test verifies the mocks were actually used and used as intended. The main purpose of course is to assert
     * the proper function of the <code>IpFinder</code> instance. It does so only by verifying that the response is JSON and that
     * the top level key is "ipinfo." Though purists may disagree, it seems overkill to take the time to hardcode
     * JSON identical in structure to the real result and to drill down with asserts. That appears more appropriate
     * for integration tests. We simply want to know if we are wiring the beans correctly, calling the service
     * appropriately, and getting the right result back.
     */
    @Test
    public void testFindLocationBy() {
        mockServer.expect(requestTo("/test/ip/12.345.678.90?apikey=key&sig=12345&format=json"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"ipinfo\": \"info\"}", MediaType.APPLICATION_JSON));
        expect(signatureMaker.create()).andReturn("12345");
        replay(signatureMaker);
        expect(dataExtractor.parseFrom(stubMap())).andReturn(stubMap());
        replay(dataExtractor);
        Map<String, String> map = ipFinder.findLocationBy("12.345.678.90");
        assertTrue(map.containsKey("ipinfo"));
        verify(dataExtractor);
        verify(signatureMaker);
        mockServer.verify();
    }

    private Map stubMap() {
        Map map = new HashMap();
        map.put("ipinfo", "info");

        return map;
    }
}
