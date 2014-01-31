package com.neustar.vidyasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Creates digital signature as specified by the Neustar IP Intelligence GeoPoint On-Demand (GPP)
 * REST service.
 */
@Component
public class SignatureMaker {
    @Value("${apiKey}")
    private String apiKey;
    @Value("${sharedSecret}")
    private String sharedSecret;
    @Autowired
    @Qualifier("clockImpl")
    private Clock clock;

    /**
     * Rips off the code found in the <a href="https://ipintelligence.neustar.biz/portal/home#documentation">documentation.</a>
     *
     * @return digital signature required for REST call
     */
    public String create() {
        MessageDigest md = messageDigest();
        String input = apiKey + sharedSecret + clock.getTime();
        md.update(input.getBytes());
        return String.format("%032x", new BigInteger(1, md.digest()));
    }

    /**
     * Convenience method to wrap checked exception in unchecked exception since for this purpose it is unrecoverable.
     * @return MD5 digest
     */
    private MessageDigest messageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsax) {
            throw new RuntimeException(nsax);
        }
    }

}
