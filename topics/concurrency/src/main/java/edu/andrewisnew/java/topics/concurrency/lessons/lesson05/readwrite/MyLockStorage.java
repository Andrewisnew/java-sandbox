package edu.andrewisnew.java.topics.concurrency.lessons.lesson05.readwrite;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyLockStorage implements Storage {
    private volatile String value;
    private final Lock readLock;
    private volatile int activeReadersCount;
    private final Object activeReadersCountLock = new Object();
    private final Lock writeLock;
    private volatile boolean writerInside;

    public MyLockStorage() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
    }

    @Override
    public String read() {
        readLock.lock();
        try {
            //reading...
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return value;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String write(String newVal) {
        writeLock.lock();
        try {
            //writing...
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String oldValue = value;
            value = newVal;
            return oldValue;
        } finally {
            writeLock.unlock();
        }
    }
}
