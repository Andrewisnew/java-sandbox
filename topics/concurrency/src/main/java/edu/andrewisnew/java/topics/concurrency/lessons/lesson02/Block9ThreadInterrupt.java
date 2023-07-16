package edu.andrewisnew.java.topics.concurrency.lessons.lesson02;

public class Block9ThreadInterrupt {
    static long v = 0;

    public static void main(String[] args) {
        example1();
        example2();
    }

    private static void example1() {
        Thread counterThread = new Thread(() -> {
            while (v < 1_000_000_000) {
                System.out.println(Thread.currentThread().isInterrupted()); //получение значения interrupted сигнала
                System.out.println(Thread.interrupted()); //получает значение и сбрасывает в false
                v++;
            }
            System.out.println("Result: " + v);
        });
        counterThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counterThread.interrupt(); //это не вынужденное прерывание, а сигнал на него. Сигнал можно обработать
        System.out.println("Result from main: " + v);
    }

    private static void example2() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                e.printStackTrace();
            }
        });
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt(); //это не вынужденное прерывание, а сигнал на него. Сигнал можно обработать
    }
}
