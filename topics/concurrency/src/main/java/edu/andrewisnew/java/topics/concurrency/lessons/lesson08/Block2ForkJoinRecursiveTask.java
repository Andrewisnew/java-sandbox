package edu.andrewisnew.java.topics.concurrency.lessons.lesson08;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class Block2ForkJoinRecursiveTask {
    public static void main(String[] args) {
        int[] arr = ThreadLocalRandom.current().ints(50, 0, 100).toArray();
        System.out.println(Arrays.toString(arr));
        try (ForkJoinPool forkJoinPool = new ForkJoinPool(3)) {
            SumTask task = new SumTask(arr);
            Integer fjSum = forkJoinPool.invoke(task);
            int sum = Arrays.stream(arr).sum();
            System.out.println(sum);
            System.out.println(fjSum);
        }
    }

    private static class SumTask extends RecursiveTask<Integer> {
        private final int[] arr;
        private final int from;
        private final int to;

        public SumTask(int[] arr) {
            this.arr = arr;
            from = 0;
            to = arr.length;
        }

        private SumTask(int[] arr, int from, int to) {
            this.arr = arr;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Integer compute() {
            if (to - from < 10) {
                int s = 0;
                for (int i = from; i < to; i++) {
                    s += arr[i];
                }
                return s;
            }
            SumTask leftPart = new SumTask(arr, from, from + (to - from) / 2);
            SumTask rightPart = new SumTask(arr, from + (to - from) / 2, to);
            System.out.println(Thread.currentThread() + ": " + " from: " + from + " to: " + to + " arr " + Arrays.toString(arr));

            leftPart.fork(); //отправили в голову своей же очереди
            rightPart.fork();

            Integer v1 = rightPart.join();//не идем дальше, но не лочимся
            Integer v2 = leftPart.join();
            return v1 + v2;

        }
    }
}
