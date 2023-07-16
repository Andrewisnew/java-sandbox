package edu.andrewisnew.java.topics.concurrency.lessons.lesson03;
/*
Wait вызывается только с захваченным монитором. Он усыпляет поток (переводит в WAITING), помещает поток в waiting set
монитора, и отпускает монитор.

При вызове notify, сначала поток, его вызвавший, покидает критическую секцию, и только потом потоки пробуждаются

у кажждого монитора есть entry set и waiting set
 */
public class Block6WaitNotify {
    private static final Object lock = new Object();
    private static volatile boolean isStarted;

    public static void main(String[] args) {
        Runnable run = () -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " on start");
                try {
                    while (!isStarted) { //во избежание низкоуровневых произвольных пробуждений
                        lock.wait(); //можно только на захваченном мониторе. В этот момент переходит в WAITING и отпускает монитор
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " running");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " finished");
            }
        };

        for (int i = 1; i < 6; i++) {
            new Thread(run, "runner" + i).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isStarted = true;
        synchronized (lock) {
            lock.notifyAll(); //разбудит всех. Пробудившийся поток не сразу продолжает работу, а становится в очередь на захват монитора
        }
//        for (int i = 0; i < 5; i++) {
//            synchronized (lock) {
//                lock.notify();
//            }
//        }
    }
}