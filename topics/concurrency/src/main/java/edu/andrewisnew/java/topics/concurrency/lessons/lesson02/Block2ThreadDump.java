package edu.andrewisnew.java.topics.concurrency.lessons.lesson02;

/*
multithreading - есть несколько потоков в приложении (не конкурируют)
concurrent     - когда есть общий распределяемый ресурс (файл, сокет, процессорное время)
 */
public class Block2ThreadDump {
    public static void main(String[] args) {
        try {
            Thread.sleep(100000); //остановим поток и в консоли ctrl+pause чтобы создать thread dump
        } catch (InterruptedException e) { //пока поток спит его кто-то другой прервал
            e.printStackTrace();
        }
    }

/*
2022-05-06 10:21:06
Full thread dump OpenJDK 64-Bit Server VM (11.0.13+8-LTS mixed mode):
Threads class SMR info:
_java_thread_list=0x00000276cb2c3ed0, length=10, elements={
0x00000276a74ad000, 0x00000276cb0fb800, 0x00000276cb104800, 0x00000276cb159000,
0x00000276cb15a000, 0x00000276cb15b000, 0x00000276cb166000, 0x00000276cb173000,
0x00000276cb17d000, 0x00000276cb2ee800
}
"main" #1 prio=5 os_prio=0 cpu=125.00ms elapsed=4.51s tid=0x00000276a74ad000 nid=0x1a68 waiting on condition  [0x000000ada9fff000]
     java.lang.Thread.State: TIMED_WAITING (sleeping)
       at java.lang.Thread.sleep(java.base@11.0.13/Native Method)
       at edu.andrewisnew.java.topics.concurrency.lessons.lesson02.Block2ConcurrencyIntro.main(Block2ConcurrencyIntro.java:10)

"Reference Handler" #2 daemon prio=10 os_prio=2 cpu=0.00ms elapsed=4.47s tid=0x00000276cb0fb800 nid=0x221c waiting on condition  [0x000000adaa6ff000]
        java.lang.Thread.State: RUNNABLE
            at java.lang.ref.Reference.waitForReferencePendingList(java.base@11.0.13/Native Method)
            at java.lang.ref.Reference.processPendingReferences(java.base@11.0.13/Reference.java:241)
            at java.lang.ref.Reference$ReferenceHandler.run(java.base@11.0.13/Reference.java:213)

"Finalizer" #3 daemon prio=8 os_prio=1 cpu=0.00ms elapsed=4.48s tid=0x00000276cb104800 nid=0x1fa0 in Object.wait()  [0x000000adaa7ff000]
        java.lang.Thread.State: WAITING (on object monitor)
            at java.lang.Object.wait(java.base@11.0.13/Native Method)
            - waiting on <0x0000000710f08f98> (a java.lang.ref.ReferenceQueue$Lock)
            at java.lang.ref.ReferenceQueue.remove(java.base@11.0.13/ReferenceQueue.java:155)
            - waiting to re-lock in wait() <0x0000000710f08f98> (a java.lang.ref.ReferenceQueue$Lock)
            at java.lang.ref.ReferenceQueue.remove(java.base@11.0.13/ReferenceQueue.java:176)
            at java.lang.ref.Finalizer$FinalizerThread.run(java.base@11.0.13/Finalizer.java:170)

"Signal Dispatcher" #4 daemon prio=9 os_prio=2 cpu=0.00ms elapsed=4.47s tid=0x00000276cb159000 nid=0x1738 waiting on condition  [0x0000000000000000]
    java.lang.Thread.State: RUNNABLE

"Attach Listener" #5 daemon prio=5 os_prio=2 cpu=0.00ms elapsed=4.47s tid=0x00000276cb15a000 nid=0x1e00 runnable  [0x0000000000000000]
    java.lang.Thread.State: RUNNABLE

"Service Thread" #6 daemon prio=9 os_prio=0 cpu=0.00ms elapsed=4.49s tid=0x00000276cb15b000 nid=0x1434 runnable  [0x0000000000000000]
    java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #7 daemon prio=9 os_prio=2 cpu=15.63ms elapsed=4.50s tid=0x00000276cb166000 nid=0x1b70 waiting on condition  [0x0000000000000000]
    java.lang.Thread.State: RUNNABLE
    No compile task
"C1 CompilerThread0" #9 daemon prio=9 os_prio=2 cpu=31.25ms elapsed=4.50s tid=0x00000276cb173000 nid=0x42ac waiting on condition  [0x0000000000000000]
    java.lang.Thread.State: RUNNABLE
    No compile task
"Sweeper thread" #10 daemon prio=9 os_prio=2 cpu=0.00ms elapsed=4.51s tid=0x00000276cb17d000 nid=0x2be0 runnable  [0x0000000000000000]
    java.lang.Thread.State: RUNNABLE
"Common-Cleaner" #11 daemon prio=8 os_prio=1 cpu=0.00ms elapsed=4.45s tid=0x00000276cb2ee800 nid=0x3dec in Object.wait()  [0x000000adaaffe000]
    java.lang.Thread.State: TIMED_WAITING (on object monitor)
        at java.lang.Object.wait(java.base@11.0.13/Native Method)
        - waiting on <0x0000000710e13da8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(java.base@11.0.13/ReferenceQueue.java:155)
        - waiting to re-lock in wait() <0x0000000710e13da8> (a java.lang.ref.ReferenceQueue$Lock)
        at jdk.internal.ref.CleanerImpl.run(java.base@11.0.13/CleanerImpl.java:148)
        at java.lang.Thread.run(java.base@11.0.13/Thread.java:829)
        at jdk.internal.misc.InnocuousThread.run(java.base@11.0.13/InnocuousThread.java:134)
"VM Thread" os_prio=2 cpu=0.00ms elapsed=4.57s tid=0x00000276cb0d7800 nid=0x3c54 runnable
"GC Thread#0" os_prio=2 cpu=0.00ms elapsed=4.59s tid=0x00000276a54ad000 nid=0xae4 runnable
"G1 Main Marker" os_prio=2 cpu=0.00ms elapsed=4.60s tid=0x00000276a751c000 nid=0x3dcc runnable
"G1 Conc#0" os_prio=2 cpu=0.00ms elapsed=4.60s tid=0x00000276a751d000 nid=0xc20 runnable
"G1 Refine#0" os_prio=2 cpu=0.00ms elapsed=4.60s tid=0x00000276a758e000 nid=0x1874 runnable
"G1 Young RemSet Sampling" os_prio=2 cpu=0.00ms elapsed=4.60s tid=0x00000276ca736800 nid=0x540 runnable
"VM Periodic Task Thread" os_prio=2 cpu=0.00ms elapsed=4.50s tid=0x00000276cb2e6000 nid=0xd3c waiting on condition
JNI global refs: 5, weak refs: 0
Heap
    garbage-first heap   total 262144K, used 1024K [0x0000000701000000, 0x0000000800000000)
    region size 1024K, 2 young (2048K), 0 survivors (0K)
    Metaspace       used 3571K, capacity 4486K, committed 4864K, reserved 1056768K
    class space    used 312K, capacity 386K, committed 512K, reserved 1048576K























"main" #1 prio=5 os_prio=0 cpu=125.00ms elapsed=4.51s tid=0x00000276a74ad000 nid=0x1a68 waiting on condition  [0x000000ada9fff000]
     java.lang.Thread.State: TIMED_WAITING (sleeping)
       at java.lang.Thread.sleep(java.base@11.0.13/Native Method)
       at edu.andrewisnew.java.topics.concurrency.lessons.lesson02.Block2ConcurrencyIntro.main(Block2ConcurrencyIntro.java:10)

"main" - имя
#1 - id
prio=5 - приоритет
at java.lang.Thread.sleep(java.base@11.0.13/Native Method)
       at edu.andrewisnew.java.topics.concurrency.lessons.lesson02.Block2ConcurrencyIntro.main(Block2ConcurrencyIntro.java:10) - стэктрейс










jps - стандартная утилита для просмотра pid, cp, ... для запущенных java приложений
jstack [pid] - show thread dump
 */
}
