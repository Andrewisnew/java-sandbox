package edu.andrewisnew.java.topics.concurrency.lessons.lesson07;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
Рассмотрим следующий пример. Мы хотим провести автомобильную гонку. В гонке принимают участие пять автомобилей.
Для начала гонки нужно, чтобы выполнились следующие условия:
Каждый из пяти автомобилей подъехал к стартовой прямой;
Была дана команда «На старт!»;
Была дана команда «Внимание!»;
Была дана команда «Марш!».
Важно, чтобы все автомобили стартовали одновременно.
 */
public class Block2CountDownLatch {
    public static void main(String[] args) {
        //одноразовый. Потоки приходят и ждут пока количество пришедших не стало n
        CountDownLatch countDownLatch = new CountDownLatch(4);
        CountDownLatch startLatch = new CountDownLatch(1);
        long count = countDownLatch.getCount(); //сколько осталось

        Runnable car = () -> {
            try {
                System.out.println(Thread.currentThread() + "подъехал к старту");
                countDownLatch.countDown();//уменьшить на 1
                if (countDownLatch.getCount() == 1) {
                    startLatch.countDown();
                }
                countDownLatch.await();//заблочиться
                System.out.println(Thread.currentThread() + " поехал");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread() + " финишировал");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(car);
        executorService.submit(car);
        executorService.submit(car);

//        while (countDownLatch.getCount() != 1) {
//            try {
//                if (countDownLatch.await(10, TimeUnit.MILLISECONDS)) {
//                    throw new IllegalStateException("Слишком много машин");
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
        try {
            startLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("На старт");
        System.out.println("Внимание");
        System.out.println("Марш!");
        countDownLatch.countDown();
        executorService.shutdown();

    }
}
