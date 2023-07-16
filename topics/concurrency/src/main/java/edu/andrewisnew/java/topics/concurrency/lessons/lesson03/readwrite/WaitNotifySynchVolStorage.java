package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.readwrite;

import java.util.concurrent.TimeUnit;

public class WaitNotifySynchVolStorage implements Storage {
    private final Object readLock = new Object();
    private final Object writeLock = new Object();
    private String value = "DEFAULT";
    private int reading = 0;

    public String read() {
        synchronized (writeLock) {
            ++reading;
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1);
            return value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            synchronized (readLock) {
                if (--reading == 0) {
                    readLock.notify();
                }
            }
        }
    }

    public String write(String newValue) {
        synchronized (writeLock) {
            synchronized (readLock) {
                while (reading != 0) {
                    try {
                        readLock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String oldVal = value;
            value = newValue;
            return oldVal;
        }
    }
}
