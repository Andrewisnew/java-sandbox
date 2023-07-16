package edu.andrewisnew.java.topics.concurrency.lessons.lesson02;

import java.util.concurrent.TimeUnit;

public class Block5ThreadState {
    public static void main(String[] args) {
        Object lock = new Object();
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Before start (NEW): " + thread.getState());
        thread.start();
        System.out.println("After start (RUNNABLE): " + thread.getState());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("At sleep (TIMED_WAITING): " + thread.getState());
        synchronized (lock) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Wait for monitor (BLOCKED): " + thread.getState());
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Wait on monitor in wait method (WAITING): " + thread.getState());
        synchronized (lock) {
            lock.notify();
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("After end (TERMINATED): " + thread.getState());
    }
}
