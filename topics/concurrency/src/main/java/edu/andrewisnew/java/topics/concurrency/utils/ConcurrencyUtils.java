package edu.andrewisnew.java.topics.concurrency.utils;

import java.util.concurrent.TimeUnit;

public class ConcurrencyUtils {
    public static void sleep(long timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
