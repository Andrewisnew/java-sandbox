package edu.andrewisnew.java.topics.concurrency.lessons.lesson05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Block2Lock {
    public static void main(String[] args) {

    }

    static class Counter {
        private Lock lock = new ReentrantLock();
        private volatile int count;

        private void inc() {
            try {
                lock.lock();
                count++;
            } finally {
                lock.unlock();
            }
        }

        private void incInterruptibly() {
            try {
                lock.lockInterruptibly();
                count++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }

        private void incWithTry() {
            try {
                while (!lock.tryLock());
                count++;
            } finally {
                lock.unlock();
            }
        }

        private void incWithTryTimed() {
            try {
                while (!lock.tryLock(1, TimeUnit.SECONDS));
                count++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }
}
