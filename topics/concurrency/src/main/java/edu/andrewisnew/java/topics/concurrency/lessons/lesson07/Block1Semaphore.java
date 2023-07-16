package edu.andrewisnew.java.topics.concurrency.lessons.lesson07;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.IntStream;

//https://habr.com/ru/articles/277669/
/*
Рассмотрим следующий пример. Существует парковка, которая одновременно может вмещать не более 5 автомобилей.
Если парковка заполнена полностью, то вновь прибывший автомобиль должен подождать пока не освободится хотя бы одно место.
После этого он сможет припарковаться.
 */
public class Block1Semaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5, false);

        ExecutorService executorService = Executors.newCachedThreadPool();
        Function<Semaphore, Runnable> car = s -> () -> {
            try {
                s.acquire();
                System.out.println(Thread.currentThread() + " заехал");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread() + " выехал");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                s.release();
            }
        };
        List<? extends Future<?>> futures = IntStream.range(0, 10)
                .boxed()
                .map(i -> executorService.submit(car.apply(semaphore)))
                .toList();
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        //на самом деле пермитов в семафоре может быть и отрицательное количество.
        Semaphore semaphore2 = new Semaphore(-2);
        executorService.submit(car.apply(semaphore));//будет ждать положительного числа пермитов

        executorService.submit(() -> semaphore2.release(3));//даст 3 пермита

        try {
            semaphore2.acquire(4);//можно и забрать сразу несколько
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        semaphore2.acquireUninterruptibly();//не интераптится, но если был интерапт, то взводит интераптед флаг

        int i = semaphore2.availablePermits();

        int i1 = semaphore2.drainPermits(); //делает число пермитов равным нулю

        int queueLength = semaphore2.getQueueLength();//оценка количества потоков, ждущих пермита

        boolean b = semaphore2.hasQueuedThreads();

        boolean b1 = semaphore2.tryAcquire();//пытается захватить, можно с таймаутом.
    }
}
