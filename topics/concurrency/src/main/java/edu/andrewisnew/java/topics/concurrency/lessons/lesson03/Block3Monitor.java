package edu.andrewisnew.java.topics.concurrency.lessons.lesson03;

/*Монитор объекта.
В java у любого объекта ссылочного типа в заголовке отводится 2 бита на хранение состояния монитора объекта (захвачен, свободен, biased захват).

В тот момент когда поток захватывает монитор, jvm обновляет состояниие монитора и в заголовок объекта монитора записывает ссылку на захвативший поток
(ссылка нужна для возможности тем же потоком захватить монитор повторно)
 */
public class Block3Monitor {

    private static volatile long val;

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Runnable inc = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                synchronized (lock) { //захват монитора. Оба потока одновременно в критическую секцию не войдут.
                    val++;
                } // монитор отпускается либо при выходе из блока либо при выбросе исключения
            }
        };
        Runnable dec = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                synchronized (lock) { //первый захватил монитор, следовалельно на этой инструкции текущий поток приостановится в ожидании освобождения монитора
                    val--;
                }
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

    private synchronized void foo() { //синхронизируется на this

    }

    private static synchronized void foo1() { //синхронизируется на getClass()

    }

    //при наследовании можно добавлять и убирать synchronized
}

//* - монитор
//                           object
//                  <-  *      0
//    0          <- read       0 ->
//    1             modify     0 ->
//    1             store   -> 1 ->
//                  * ->       1 * ->
//                             1        read ->       1
//                             1        modify        0
//                             0    <-  store         0
//                             0 <-  *


/*
Если несколько потоков залочены на мониторе, то при освобождении монитора он будет отдан случайному потоку

starvation - когда поток голодает, редко получает ресурс (например, при розыгрыше монитора, процессорного времени)
 */