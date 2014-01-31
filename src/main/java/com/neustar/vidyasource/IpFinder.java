package com.neustar.vidyasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * The central "business" point of the application. Makes the call to the Neustar IP Intelligence GeoPoint On-Demand (GPP)
 * REST service and transforms the result for consumption by clients.
 */
@Component
public class IpFinder {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SignatureMaker signatureMaker;
    @Value("${serviceBaseUrl}")
    private String serviceBaseUrl;
    @Value("${apiKey}")
    private String apiKey;
    @Autowired
    private DataExtractor dataExtractor;

    /**
     * Takes an IP address from somewhere and uses it to make the REST call to the the Neustar IP Intelligence GeoPoint
     * On-Demand (GPP) service. The response is transformed for subsequent consumption.
     * @param ip The IP address
     * @return One-dimensional mapping of the key-value pairs in the service response
     */
    public Map<String, String> findLocationBy(String ip) {
        return dataExtractor.parseFrom(restTemplate.getForObject(serviceBaseUrl, Map.class, parameterMap(ip)));
    }

    /**
     * Interpolates the parameters in the service URL with appropriate values
     * @param ip The IP address
     * @return Mapping of parameter names to values
     */
    private Map<String, String> parameterMap(String ip) {
        Map<String, String> map = new HashMap<>();
        map.put("ip", ip);
        map.put("apiKey", apiKey);
        map.put("sig", signatureMaker.create());

        return map;
    }

}
