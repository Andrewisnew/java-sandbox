package edu.andrewisnew.java.topics.concurrency.lessons.lesson03;

import java.util.concurrent.TimeUnit;

public class Block7SStop {
    public static void main(String[] args) throws InterruptedException {
        Storage storage = new Storage();

        Runnable task = () -> {
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                storage.send();
            }
        };
        Thread thread = new Thread(task);
        thread.start();


        TimeUnit.MILLISECONDS.sleep(500);
        //кидает java.lang.ThreadDeath в поток.
        //внешняя остановка может привести к неконсистентности
        thread.stop();

        TimeUnit.MICROSECONDS.sleep(100);
        System.out.println(thread.getState());


//        new Thread(task).start();
//
//        TimeUnit.MICROSECONDS.sleep(10);
//
        synchronized (storage) {
            System.out.println(storage.getX());
            System.out.println(storage.getY());
        }
    }
}

// x + y = 100
class Storage {
    private volatile int x = 100;
    private volatile int y = 0;

    public synchronized void send() {
        try {
            --x;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ++y;
        } catch (Error error) {
            error.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public synchronized int[] getValues() {
        return new int[]{x, y};
    }
}
