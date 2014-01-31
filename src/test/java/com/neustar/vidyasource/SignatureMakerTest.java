package com.neustar.vidyasource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.util.ReflectionTestUtils;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Unit test to verify that <code>SignatureMaker</code> works when it is provided the proper dependencies, which are injected
 * "manually" after being mocked.
 */
@RunWith(JUnit4.class)
public class SignatureMakerTest {
    private SignatureMaker signatureMaker;
    private Clock mockClock;

    @Before
    public void setUp() throws Exception {
        signatureMaker = new SignatureMaker();
        mockClock = createMock(Clock.class);
    }

    /**
     * This test uses Spring's <code>ReflectionTestUtils</code> in order to inject the expected dependencies without having to create
     * setters just to make the test work.
     * <br></br>
     * The test also uses EasyMock to mock the creation of the system time component of the signature so the test is pure
     * (independent of external factors) as all unit tests should be so they are repeatable. The expected value was determined by
     * using <a href="http://www.miraclesalad.com/webtools/md5.php">this online MD5 generator</a>.
     */
    @Test
    public void testCreate() {
        ReflectionTestUtils.setField(signatureMaker, "clock", mockClock);
        ReflectionTestUtils.setField(signatureMaker, "apiKey", "key");
        ReflectionTestUtils.setField(signatureMaker, "sharedSecret", "secret");
        expect(mockClock.getTime()).andReturn(10l);
        replay(mockClock);
        assertEquals("5f4a68de2dab24a778a03484abffcc94", signatureMaker.create());
        verify(mockClock);
    }

    /**
     * Same idea except different values for the mocks.
     */
    @Test
    public void testCreate2() {
        ReflectionTestUtils.setField(signatureMaker, "clock", mockClock);
        ReflectionTestUtils.setField(signatureMaker, "apiKey", "key2");
        ReflectionTestUtils.setField(signatureMaker, "sharedSecret", "secret2");
        expect(mockClock.getTime()).andReturn(20l);
        replay(mockClock);
        assertEquals("7b943942b3fc33b93afe1889349f4fe6", signatureMaker.create());
        verify(mockClock);
    }
}
