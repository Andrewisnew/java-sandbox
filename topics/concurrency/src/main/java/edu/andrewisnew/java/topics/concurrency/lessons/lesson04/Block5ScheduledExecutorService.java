package edu.andrewisnew.java.topics.concurrency.lessons.lesson04;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static edu.andrewisnew.java.topics.concurrency.utils.ConcurrencyUtils.sleep;

public class Block5ScheduledExecutorService {
    private final static Object lock = new Object();
    public static void main(String[] args) {
        try(ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10)) {
            serviceExamples(scheduledExecutorService);
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Finish");
        }
    }

    private static void serviceExamples(ScheduledExecutorService scheduledExecutorService) {
        //запустит задачу через 500 мс
        scheduledExecutorService.schedule(() -> {
            System.out.println("Do it!");
            sleep(1, TimeUnit.SECONDS);
        }, 500, TimeUnit.MILLISECONDS);
        int[] counter = {0};

        //будет запускать задачу каждые 1 с (но дожидается завершения предыдущей!)
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(counter[0]++ + " do it");
            sleep(2, TimeUnit.SECONDS);
            System.out.println(counter[0] + " it is done");
        }, 1, 1, TimeUnit.SECONDS);

        int[] counter2 = {0};

        //будет запускать задачу периодически, при чем каждая задача будет запускаться через 1 с после завершения предыдущей
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println(counter2[0]++ + " do that");
            sleep(2, TimeUnit.SECONDS);
            System.out.println(counter2[0] + " that is done");

        }, 1, 1, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(() -> {
            synchronized (lock) {
                lock.notify();
            }
        }, 10, TimeUnit.SECONDS);
    }
}
