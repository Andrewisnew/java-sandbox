package edu.andrewisnew.java.topics.concurrency.lessons.lesson02;

import java.util.concurrent.TimeUnit;

public class Block4ThreadPriority {
    public static void main(String[] args) {
        Runnable task = () -> {
            long v = 0;
            while (!Thread.currentThread().isInterrupted()) {
                v++;
                //Thread.yield(); //готов уступить несколько квантов времени другим потокам
            }
            System.out.println(Thread.currentThread() + ": " + v);
        };
        int n = Runtime.getRuntime().availableProcessors() + 2;
        Thread[] counters = new Thread[n];
        for (int i = 0; i < counters.length; i++) {
            counters[i] = new Thread(task);
        }
        counters[0].setPriority(Thread.MIN_PRIORITY);
        counters[3].setPriority(Thread.MAX_PRIORITY);

        for (Thread counter : counters) {
            counter.start();
        }

        try {
            TimeUnit.SECONDS.sleep(5); //то, через сколько поток придет к планировщику потоков, готовый на дальнейшее исполнение
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread counter : counters) {
            counter.interrupt();
        }
        System.out.println("Main ends");

        //точно неизвестно сколько квантов времени будет выделено на каждый из потоков. Но приоритетом можно повлиять на распределение квантов времени
    }
}



