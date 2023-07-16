package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.readwrite;

import java.util.concurrent.TimeUnit;

public class MyFirstStorage implements Storage {
    private volatile String value;
    private final Object readLock = new Object();
    private volatile int activeReadersCount;
    private final Object activeReadersCountLock = new Object();
    private final Object writeLock = new Object();
    private volatile boolean writerInside;

    @Override
    public String read() {
        if (writerInside) {
            synchronized (writeLock) {
                if (writerInside) {
                    try {
                        writeLock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        synchronized (activeReadersCountLock) {
            activeReadersCount++;
        }
        //reading...
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String val = value;
        synchronized (activeReadersCountLock) {
            activeReadersCount--;
        }
        if (activeReadersCount == 0) {
            synchronized (readLock) {
                readLock.notifyAll();
            }
        }
        return val;
    }

    @Override
    public String write(String newVal) {
        writerInside = true;
        synchronized (writeLock) {
            if (activeReadersCount > 0) {
                synchronized (readLock) {
                    try {
                        readLock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            //writing...
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String oldValue = value;
            value = newVal;
            writerInside = false;
            writeLock.notifyAll();
            return oldValue;
        }
    }
}
