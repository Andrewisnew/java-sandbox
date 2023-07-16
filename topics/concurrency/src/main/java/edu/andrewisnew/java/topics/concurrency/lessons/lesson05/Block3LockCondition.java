package edu.andrewisnew.java.topics.concurrency.lessons.lesson05;

import edu.andrewisnew.java.topics.concurrency.utils.ConcurrencyUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Block3LockCondition {//a la wait notify
    public static void main(String[] args) {
        try(ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            doIt(executorService);
        }
    }

    private static void doIt(ExecutorService executorService) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        executorService.submit(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " started and slept");
            try {
                condition.awaitUninterruptibly();
                System.out.println(Thread.currentThread().getName() + " notified");
            } finally {
                lock.unlock();
            }
        });

        ConcurrencyUtils.sleep(1, TimeUnit.SECONDS);

        executorService.submit(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " started and notifies");
            try {
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        });
    }
}
