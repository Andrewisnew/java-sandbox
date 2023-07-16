package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.philosophers;

import edu.andrewisnew.java.topics.concurrency.lessons.lesson03.philosophers.visual.Visual;

import java.util.concurrent.TimeUnit;

public class Waiter {
    private static final Table TABLE = new Table();
    private static final Visual VISUAL = new Visual();

    public static void main(String[] args) {
        Thread waiter = Thread.currentThread();
        waiter.setName("WaiterThread");
        Philosopher mo = createPhilosopherWithDeadLock("Mo", waiter);
        Philosopher lao = createPhilosopherWithDeadLock("Lao", waiter);
        Philosopher kun = createPhilosopherWithDeadLock("Kun", waiter);
        Philosopher men = createPhilosopherWithDeadLock("Men", waiter);
        Philosopher chuan = createPhilosopherWithDeadLock("Chuan", waiter);
        invitePhilosopher(mo);
        invitePhilosopher(lao);
        invitePhilosopher(kun);
        invitePhilosopher(men);
        invitePhilosopher(chuan);
        System.out.println(TABLE);
    }

    private static Philosopher createPhilosopherWithDeadLock(String name, Thread waiter) {
        return new Philosopher(name) {
            @Override
            public void run() {
                try {
                    waiter.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    synchronized (TABLE.getLeftStick(this)) {
                        synchronized (TABLE.getRightStick(this)) {
                            System.out.println(name + " start eating");
                            try {
                                TimeUnit.MILLISECONDS.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(name + " end eating");
                        }
                    }
                }
            }
        };
    }

    private static Philosopher createPhilosopherWithoutDeadLock(String name, Thread waiter) {
        return new Philosopher(name) {
            @Override
            public void run() {
                try {
                    waiter.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    Stick lowerStick = TABLE.getLeftStick(this);
                    Stick higherStick = TABLE.getRightStick(this);
                    //назначается "приоритет" мониторам. В итоге невозможен dead lock так как если мы захватив ресурс
                    // приоритета n и пытаемся захватить n + m, то если n + m кто-то удерживает, то этот удерживающий
                    // очевидно не будет удерживая n + m пытаться захватить n, так как n < n + m
                    //монитор
                    if (higherStick.getStickId() < lowerStick.getStickId()) {
                        Stick tmp = lowerStick;
                        lowerStick = higherStick;
                        higherStick = tmp;
                    }
                    synchronized (lowerStick) {
                        VISUAL.setWaiting(this);
                        synchronized (higherStick) {
                            VISUAL.setBusy(this);
                            System.out.println(name + " start eating");
                            try {
                                TimeUnit.MILLISECONDS.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(name + " end eating");
                            VISUAL.setFree(this);
                        }
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private static void invitePhilosopher(Philosopher philosopher) {
        TABLE.placePhilosopher(philosopher);
        VISUAL.addPhilosopher(philosopher);
        philosopher.start();
    }
}
