package edu.andrewisnew.java.topics.concurrency.lessons.lesson05;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import static edu.andrewisnew.java.topics.concurrency.utils.ConcurrencyUtils.sleep;

public class Block1Future {
    public static void main(String[] args) {
//        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
//            cancel(executorService);
//            get(executorService);
//            others(executorService);
//        }
        scheduledWork();

    }

    private static void others(ExecutorService executorService) {
        Future<Integer> future = submitWithDelay(executorService);
        Throwable throwable = future.exceptionNow(); //взять исключение, которое было выброшено. Если сейчас его не взять, то ISE
        Integer integer = future.resultNow(); //тут же вернет результат. Если не isDone то ISE
        Future.State state = future.state();
    }

    private static void get(ExecutorService executorService) {
        Future<Integer> future = submitWithException(executorService);
        try {
            Integer integer = future.get();//блокирующая
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getCause());
        } catch (ExecutionException e) { //обертка над исключением, брошенным в задаче
            throw new RuntimeException(e);
        }
        //также бросает CancellationException.

        future = submitWithDelay(executorService);

        try {
            Integer integer = future.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) { //если дошли до таймаута
            throw new RuntimeException(e);
        }
        boolean done = future.isDone();
    }

    private static void cancel(ExecutorService executorService) {
        Future<Integer> future = submitWithDelay(executorService);
        boolean cancelAccepted = future.cancel(true);//отмена задачи. Если в процессе выполнения, то продолжит выполняться. true - послать interrupt
        boolean cancelled = future.isCancelled();
    }

    private static Future<Integer> submitWithDelay(ExecutorService executorService) {
        return executorService.submit(() -> {
            sleep(1, TimeUnit.SECONDS);
            return 42;
        });
    }

    private static Future<Integer> submitWithException(ExecutorService executorService) {
        return executorService.submit(() -> {
            throw new Exception("Oops");
        });
    }

    private static void scheduledWork() {
        try (var executor = Executors.newScheduledThreadPool(2)) {
            Map<String, Integer> delaysMap = Map.of("Dolls", 1, "Bolls", 2);
            Map<String, AtomicLong> countersMap = Map.of("Dolls", new AtomicLong(), "Bolls", new AtomicLong());
            Map<String, Future<?>> futures = new ConcurrentHashMap<>();
            delaysMap.forEach((name, delay) -> doIt(countersMap, name, executor, delay, futures));
            waitForFinish(futures);
        }
    }

    private static void waitForFinish(Map<String, Future<?>> futures) {
        futures.forEach((name, future) -> {
            try {
                future.get();
                if (future.equals(futures.get(name))) {
                    System.out.println(name + " finished");
                    futures.remove(name);
                } else {
                    waitForFinish(futures);
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void doIt(Map<String, AtomicLong> countersMap, String name, ScheduledExecutorService executor, int delay, Map<String, Future<?>> futures) {
        if (countersMap.get(name).get() >= 10) {
            return;
        }
        futures.put(name, executor.schedule(() -> {
            System.out.println(name + " become " + countersMap.get(name).incrementAndGet());
            doIt(countersMap, name, executor, delay, futures);
        }, delay, TimeUnit.SECONDS));
    }
}
