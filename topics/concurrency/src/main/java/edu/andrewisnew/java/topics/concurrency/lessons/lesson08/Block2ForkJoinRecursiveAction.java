package edu.andrewisnew.java.topics.concurrency.lessons.lesson08;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

/*
fork - кладет в очередь (в голову) и возвращает управление
join - ждет пока задача не закончилась. В этот момент может уйти и заняться чем-то другим.
(поискать у себя, поискать у других, поискать во входной очереди, заблочиться)


При делении нужно держаться отношения задач к потокам равным 10-100
 */
public class Block2ForkJoinRecursiveAction {
    public static void main(String[] args) {
        int[] arr = ThreadLocalRandom.current().ints(50, 0, 100).toArray();
        System.out.println(Arrays.toString(arr));
        try (ForkJoinPool forkJoinPool = new ForkJoinPool(3)) {
            QuickSortAction quickSortAction = new QuickSortAction(arr);
            forkJoinPool.invoke(quickSortAction);
        }
        System.out.println(Arrays.toString(arr));
    }

    private static class QuickSortAction extends RecursiveAction {
        private final int[] arr;
        private final int from;
        private final int to;

        public QuickSortAction(int[] arr) {
            this.arr = arr;
            from = 0;
            to = arr.length;
        }

        private QuickSortAction(int[] arr, int from, int to) {
            this.arr = arr;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void compute() {
            if (to <= from) {
                return;
            }
            if (to - from < 10) {
                Arrays.sort(arr, from, to);
                return;
            }
            int partition = partition(arr, from, to);
            QuickSortAction leftPart = new QuickSortAction(arr, from, partition);
            QuickSortAction rightPart = new QuickSortAction(arr, partition + 1, to);
            System.out.println(Thread.currentThread() + ": " + " from: " + from + " to: " + to + " arr " + Arrays.toString(arr));

            leftPart.fork(); //отправили в голову своей же очереди
            rightPart.fork();

            rightPart.join(); //не идем дальше, но не лочимся
            leftPart.join();

//            invokeAll(leftPart, rightPart); // или так
        }

        private static int partition(int[] arr, int from, int to) {
            int i = from;
            int pivot = arr[to - 1];
            for (int j = from; j < to - 1; j++) {
                if (arr[j] < pivot) {
                    int tmp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = tmp;
                    i++;
                }
            }
            int tmp = arr[to - 1];
            arr[to - 1] = arr[i];
            arr[i] = tmp;
            return i;
        }
    }
}
