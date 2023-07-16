package edu.andrewisnew.java.topics.concurrency.lessons.lesson03;

import java.util.concurrent.TimeUnit;

public class Block5DeadLock {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("Thread1");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("Thread2");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
/*
"Thread-0" #13 prio=5 os_prio=0 cpu=0.00ms elapsed=162.11s tid=0x00000295e7dca800 nid=0x3650 waiting for monitor entry  [0x000000a4031ff000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at edu.andrewisnew.java.topics.concurrency.lessons.lesson03.Block4DeadLock.lambda$main$0(Block4DeadLock.java:18)
        - waiting to lock <0x00000007010dfb10> (a java.lang.Object)
        - locked <0x00000007010dfb20> (a java.lang.Object)
        at edu.andrewisnew.java.topics.concurrency.lessons.lesson03.Block4DeadLock$$Lambda$14/0x0000000840064840.run(Unknown Source)
        at java.lang.Thread.run(java.base@11.0.4/Thread.java:834)

   Locked ownable synchronizers:
        - None

"Thread-1" #14 prio=5 os_prio=0 cpu=0.00ms elapsed=162.11s tid=0x00000295e7dcb800 nid=0x288 waiting for monitor entry  [0x000000a4032fe000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at edu.andrewisnew.java.topics.concurrency.lessons.lesson03.Block4DeadLock.lambda$main$1(Block4DeadLock.java:31)
        - waiting to lock <0x00000007010dfb20> (a java.lang.Object)
        - locked <0x00000007010dfb10> (a java.lang.Object)
        at edu.andrewisnew.java.topics.concurrency.lessons.lesson03.Block4DeadLock$$Lambda$15/0x0000000840064c40.run(Unknown Source)
        at java.lang.Thread.run(java.base@11.0.4/Thread.java:834)

   Locked ownable synchronizers:
        - None



Found one Java-level deadlock:
=============================
"Thread-0":
  waiting to lock monitor 0x00000295e7bdad80 (object 0x00000007010dfb10, a java.lang.Object),
  which is held by "Thread-1"
"Thread-1":
  waiting to lock monitor 0x00000295e7bdac80 (object 0x00000007010dfb20, a java.lang.Object),
  which is held by "Thread-0"

 */