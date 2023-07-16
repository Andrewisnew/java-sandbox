package edu.andrewisnew.java.topics.concurrency.lessons.lesson03;

import java.util.concurrent.TimeUnit;

public class Block8SuspendResume {
    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();

        Thread counter = new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.holdsLock(object)); //обладает ли поток монитором
                long value = 0;
                for (long i = 0; i < Integer.MAX_VALUE; ++i) {
                    ++value;
                }
                System.out.println(value);
            }
        });
        counter.start();

        Thread.sleep(500);
        //приостанавливает поток
        //counter останется RUNNABLE
        //нет методов isSuspended
        counter.suspend();

        synchronized (object) {
            System.out.println("Монитор у counter");
        }

        TimeUnit.SECONDS.sleep(1);
        //возобновляет
        counter.resume();
        System.out.println("After resume");
    }
}
