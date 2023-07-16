package edu.andrewisnew.java.topics.concurrency.lessons.lesson03;

public class Block2RaceConditionProblem {

    private static volatile long val;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable inc = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                //synchronized (lock) {
                    val++;
                //}
            }
        };
        Runnable dec = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                //synchronized (lock) {
                    val--;
                //}
            }
        };
        Thread incThread = new Thread(inc);
        Thread decThread = new Thread(dec);

        incThread.start();
        decThread.start();

        incThread.join();
        decThread.join();
        System.out.println(val);
    }
}

// race-condition
//   INC                     value                   DEC
//                             0
//    0          <- read       0
//    0                        0        read ->       0
//    1             modify     0        modify       -1
//    1             store   -> 1                     -1
//                            -1   <-   store        -1
