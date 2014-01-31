package com.neustar.vidyasource;

import org.springframework.stereotype.Component;

/**
 * Simple implementation using system clock time. Though purists may disagree, there is no test for this class as it
 * seems too trivial. A test will only verify the environment and compiler.
 */
@Component
public class ClockImpl implements Clock {
    @Override
    public long getTime() {
        return System.currentTimeMillis() / 1000;
    }
}
