package edu.andrewisnew.java.topics.concurrency.lessons.lesson05;

import edu.andrewisnew.java.topics.concurrency.utils.ConcurrencyUtils;

import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.function.Function;

public class Block8BlockingQueues {
    static class Box {
        protected String destination;

        public Box(String destination) {
            this.destination = destination;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "destination='" + destination + '\'' +
                    '}';
        }
    }

    static class PrioritizedBox extends Box {
        private int priority;

        public PrioritizedBox(String destination, int priority) {
            super(destination);
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public String toString() {
            return "PrioritizedBox{" +
                    "priority=" + priority +
                    ", destination='" + destination + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        delayedBlockingQueue();
    }

    private static void arrayBlockingQueue() {
        int boxNum = 10;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //циклический массив, фиксированная длина, чтение и запись на одном локе
        BlockingQueue<Box> arrayBlockingQueue = new ArrayBlockingQueue<>(boxNum);

        Runnable operator = getOperator(boxNum, arrayBlockingQueue, i -> new Box(String.valueOf(i)));

        Runnable courier = getCourier(boxNum, arrayBlockingQueue);

        executorService.submit(operator);
        executorService.submit(courier);
        executorService.shutdown();
    }

    private static void linkedBlockingQueue() {
        int boxNum = 10;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //список, лок на хвост и на голову, не фиксирована длина. Но проигрывает array по памяти
        BlockingQueue<Box> arrayBlockingQueue = new LinkedBlockingQueue<>(boxNum);

        Runnable operator = getOperator(boxNum, arrayBlockingQueue, i -> new Box(String.valueOf(i)));

        Runnable courier = getCourier(boxNum, arrayBlockingQueue);

        executorService.submit(operator);
        executorService.submit(courier);
        executorService.shutdown();
    }

    private static void synchronousQueue() {
        int boxNum = 10;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //нет хранилища. Каждый поток, который что-то кладет в очередь, ждет пока то, что он положил кто-то не заберет.\
        //не fair будет работать как стек.
        BlockingQueue<Box> arrayBlockingQueue = new SynchronousQueue<>();

        Runnable operator = getOperator(boxNum, arrayBlockingQueue, i -> new Box(String.valueOf(i)));

        Runnable courier = getCourier(boxNum, arrayBlockingQueue);

        executorService.submit(operator);
        executorService.submit(courier);
        executorService.shutdown();
    }

    private static void linkedBlockingDeque() {
        int boxNum = 10;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //как и queue, но можно работать с головой и хвостом
        BlockingDeque<Box> arrayBlockingQueue = new LinkedBlockingDeque<>();

        Runnable operator = getOperator(boxNum, arrayBlockingQueue, i -> new Box(String.valueOf(i)));

        Runnable courier = getCourier(boxNum, arrayBlockingQueue);

        executorService.submit(operator);
        executorService.submit(courier);
        executorService.shutdown();
    }

    private static void transferDeque() {
        int boxNum = 10;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //как и queue, и если методы чтоб продьюсер ждал когда придет консьюмер
        TransferQueue<Box> arrayBlockingQueue = new LinkedTransferQueue<>();

        Runnable operator = getOperator(boxNum, arrayBlockingQueue, i -> new Box(String.valueOf(i)));

        Runnable courier = getCourier(boxNum, arrayBlockingQueue);

        executorService.submit(operator);
        executorService.submit(courier);
        executorService.shutdown();
    }

    private static void priorityBlockingQueue() {
        int boxNum = 10;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //как и queue, располагает элементы по приоритету. Элементы должны быть либо comparable или надо передать компаратор
        Comparator<PrioritizedBox> comparator = Comparator.comparing(PrioritizedBox::getPriority).reversed();
        BlockingQueue<PrioritizedBox> queue = new PriorityBlockingQueue<>(10, comparator);

        Runnable operator = getOperator(boxNum, queue, i -> new PrioritizedBox(String.valueOf(i), i));

        Runnable courier = getCourier(boxNum, queue);

        Future<?> submit = executorService.submit(operator);
        executorService.submit(courier);
        executorService.shutdown();
        try {
            submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    static class DelayedBox extends Box implements Delayed {
        private final long deliveryTime = System.currentTimeMillis() + 10 * 1000;

        public DelayedBox(String destination) {
            super(destination);

        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(deliveryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long d = (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }
    }

    private static void delayedBlockingQueue() {
        int boxNum = 10;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //элемент в очереди становится доступным спустя время задержки
        BlockingQueue<DelayedBox> queue = new DelayQueue<>();

        Runnable operator = getOperator(boxNum, queue, i -> new DelayedBox(String.valueOf(i)));

        Runnable courier = getCourier(boxNum, queue);

        Future<?> submit = executorService.submit(operator);
        executorService.submit(courier);
        executorService.shutdown();
        try {
            submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Runnable getCourier(int boxNum, BlockingQueue<? extends Box> arrayBlockingQueue) {
        Runnable courier = () -> {
            for (int i = 0; i < boxNum; i++) {
                System.err.println("Trying to take box");
                Box box = null;
                try {
                    box = arrayBlockingQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.err.println(String.format("Box %s is taken", box));
                ConcurrencyUtils.sleep(3, TimeUnit.SECONDS);
                System.err.println(String.format("Box %s delivered", box));
            }
        };
        return courier;
    }

    private static <T extends Box> Runnable getOperator(int boxNum, BlockingQueue<T> arrayBlockingQueue, Function<Integer, T> boxCreator) {
        Runnable operator = () -> {
            for (int i = 0; i < boxNum; i++) {
                System.out.println("Prepare box - " + i);
                ConcurrencyUtils.sleep(1, TimeUnit.SECONDS);
                T box = boxCreator.apply(i);
                System.out.println(String.format("Trying put %s to the desk", box));
                try {
                    arrayBlockingQueue.put(box);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(String.format("Box %s is inside", box));
            }
        };
        return operator;
    }
}
