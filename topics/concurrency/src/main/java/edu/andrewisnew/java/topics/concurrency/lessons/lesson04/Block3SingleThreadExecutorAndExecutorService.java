package edu.andrewisnew.java.topics.concurrency.lessons.lesson04;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static edu.andrewisnew.java.topics.concurrency.utils.ConcurrencyUtils.sleep;

public class Block3SingleThreadExecutorAndExecutorService {
    public static void main(String[] args) {
        others();

    }

    private static void execute() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            System.out.println("Start");
            sleep(1, TimeUnit.SECONDS);
            System.out.println("Hello");
        };

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
    }

    private static void shutdown() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            System.out.println("Start");
            sleep(1, TimeUnit.SECONDS);
            System.out.println("Hello");
        };

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        executor.shutdown(); //доделать задачи, но новые не принимать. Неблокирующий
        try {
            executor.execute(runnable); //RejectedExecutionException
        } catch (RejectedExecutionException e) {
            System.err.println(e);
        }

        executor = new ThreadPoolExecutor(1, 1, 0/*doesn't matter. Number of threads won't be less than core size*/, TimeUnit.SECONDS,
                new SynchronousQueue<>(), Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.err.println("Task rejected. Task: " + r + ", executor: " + executor);
                    }
                });

        executor.execute(runnable);
        executor.shutdown();
        executor.execute(runnable); //serr
        System.out.println("Finish");
    }

    private static void shutdownNow() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            System.out.println("Start");
            sleep(1, TimeUnit.SECONDS);
            System.out.println("Hello");
        };

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        List<Runnable> runnables = executor.shutdownNow();//возвращает не начатые задачи и шлет interrupt в потоки
        System.out.println(runnables);
        try {
            executor.execute(runnable); //RejectedExecutionException
        } catch (RejectedExecutionException e) {
            System.err.println(e);
        }
        //тоже можно навесить RejectedExecutionHandler
    }

    private static void awaitTermination() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            System.out.println("Start");
            sleep(1, TimeUnit.SECONDS);
            System.out.println("Hello");
        };

        executor.execute(runnable);

        try {
            boolean termination = executor.awaitTermination(3000, TimeUnit.MILLISECONDS);
            System.out.println("Is terminated: " + termination); //false
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Continue");

        Executors.newSingleThreadExecutor().execute(() -> {
            sleep(1, TimeUnit.SECONDS);
            executor.shutdownNow(); //shutdown не перевел бы в terminated
            System.out.println("Shutdown");
        });

        try {
            boolean termination = executor.awaitTermination(1500, TimeUnit.MILLISECONDS);
            System.out.println("Is terminated: " + termination); //true
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void others() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.isShutdown(); //shutdown/shutdownNow called
        executor.isTerminated(); //is shutdown and all tasks are completed
        //есть набор методов, возвращающих Future. Future будет рассматриваться позднее
        Future<?> future = executor.submit(() -> System.out.println("Do smth"));
        Future<Integer> future2 = executor.submit(() -> {
            System.out.println("Do smth");
            return 7;
        });
        Future<Integer> future3 = executor.submit(() -> System.out.println("Do smth"), 7);

        try {
            //внезапно блокирующий...
            List<Future<Integer>> futureList = executor.invokeAll(List.of(() -> {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Do smth here");
                return 7;
            }));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Continue");

        try {
            //и этот. Запускает сразу все, и как только одна выполнилась интераптит остальные
            Integer invokeAny = executor.invokeAny(List.of(() -> {
                        System.out.println("start 1");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Do smth here");
                        return 7;
                    },
                    () -> {
                        System.out.println("start 2");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Do smth here");
                        return 8;
                    },
                    () -> {
                        System.out.println("start 3");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Do smth here");
                        return 4;
                    }));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }


        try {
            executor.invokeAny(List.of(), 1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}

