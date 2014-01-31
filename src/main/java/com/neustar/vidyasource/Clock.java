package com.neustar.vidyasource;

/**
 * Since system time is part of the digital signature, extracting the interface of the clock allows us to abstract the
 * implementation of the time component from the signature creation mechanism.
 */
public interface Clock {
    public long getTime();
}
