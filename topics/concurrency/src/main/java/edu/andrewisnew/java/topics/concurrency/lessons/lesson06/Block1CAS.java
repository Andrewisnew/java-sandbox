package edu.andrewisnew.java.topics.concurrency.lessons.lesson06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/*
 CAS - Compare And Set/Swap
 За одну неделимую (атомарную) операцию позволяют выполнить три вещи
 1. Обратиться к участку памяти
 2. Сравнение значение внутри с предоставленным
 3. Если совпало, то записывается новое
 */
public class Block1CAS {
    static final AtomicLong VALUE = new AtomicLong(0);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // Если совпадает, метод возвращает true. Меняет только если совпадает. Lock-free
        boolean b = VALUE.compareAndSet(5, 6);
        long l1 = VALUE.get(); //возвращает текущее
        long l2 = VALUE.incrementAndGet();


        executorService.submit(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                long l;
                do {
                    l = VALUE.get();
                } while (!VALUE.compareAndSet(l, l + 1));
            }
        });

        executorService.submit(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                long l;
                do {
                    l = VALUE.get();
                    // Если совпадает, метод возвращает true.
                    //Проблема ABA за это время значение могли увеличить на 1 и вернуть обратно
                } while (!VALUE.compareAndSet(l, l - 1));
            }
        });

        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(VALUE.get());
        executorService.shutdown();
    }

}
