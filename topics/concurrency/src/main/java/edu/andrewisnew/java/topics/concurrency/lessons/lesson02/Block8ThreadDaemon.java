package edu.andrewisnew.java.topics.concurrency.lessons.lesson02;

public class Block8ThreadDaemon {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Booo");
            }
        });
        daemonThread.setDaemon(true); //обязательно до запуска потока, иначе IllegalThreadStateException
        daemonThread.start();
        //jvm будет работать пока есть потоки демоны

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
