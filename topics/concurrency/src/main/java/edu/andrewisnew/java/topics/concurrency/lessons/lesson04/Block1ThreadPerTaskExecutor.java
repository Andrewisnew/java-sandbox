package edu.andrewisnew.java.topics.concurrency.lessons.lesson04;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class Block1ThreadPerTaskExecutor {
    public static void main(String[] args) {
        //Выполняет переданные задачи в каком-то потоке (возможно даже вызывающем)
        Executor executor;
        executor = new ThreadPerTaskExecutor();
        Runnable runnable = () -> {
            System.out.println("Start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Hello");
        };

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
    }

    static class ThreadPerTaskExecutor implements Executor {
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }
}

