package edu.andrewisnew.java.topics.concurrency.lessons.lesson04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Block4ThreadPoolExecutor {
    public static void main(String[] args) {
        //один поток. ходит в блокирующую очередь, достает задачи и выполняет их одну за другой
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Executor thread");
            }
        });

        //экзекьютор на 4 потока. Достают задачи из общей очереди
        executorService = Executors.newFixedThreadPool(5);

        executorService = Executors.newFixedThreadPool(4, Thread::new); //с ThreadFactory

        //изначально без потоков. Создает потоки по необходимости.
        executorService = Executors.newCachedThreadPool();

        //все вышеприведенные варианты создают ThreadPoolExecutor
        executorService = new ThreadPoolExecutor(2, 10, 4, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return null;
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                    }
                });

        executorService = Executors.unconfigurableExecutorService(executorService);
    }
}
