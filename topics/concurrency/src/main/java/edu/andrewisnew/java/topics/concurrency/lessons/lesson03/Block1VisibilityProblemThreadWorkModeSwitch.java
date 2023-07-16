package edu.andrewisnew.java.topics.concurrency.lessons.lesson03;

import java.util.concurrent.TimeUnit;

public class Block1VisibilityProblemThreadWorkModeSwitch {
    enum ThreadWorkMode {
        RUNNING,
        PAUSED,
        STOPPED
    }
    /*
    Оба потока имеют ссылку на эти две переменные.
    VISIBILITY PROBLEM
    На машине 4 ядра, каждый имеет доступ к общей памяти, и у каждого есть кэш. 3 уровня: L1, L2, L3.
    L1 - максимально близок к ядру, малый объем памяти. Туда попадают данные, с которыми осуществляется работа в текущий момент.
    L2 - чуть дальше и чуть больше.
    L3 - еще дальше, общий на все ядра

    Когда поток обращается к области памяти, он изначально идет в кэш и проверяет есть ли значение там, если есть, то берет его.
    Если нет (cash miss), то идет дальше по следующему пути:
    L1 -> L2 -> L3 -> RAM

    Если не нашли, то идем в RAM. Каждый раз в RAM ходить дорого. По этой причине у процессора есть cache line - блок памяти,
    который считывается за одно обращение к RAM.
    Предположим понадобилась область памяти размером в 4 байта, она не была найдена в кэше, тогда из RAM считывается не только эти 4 байта,
    а в дополнение еще информация, добивая общий объем до размера cache line. Эта информация сохраняется в L3, L2, L1.

    Таким образом появляется следующая проблема: если другой поток поменяет значение, то первый, обращаясь к своему кэшу,
    не увидит изменений. Это и есть VISIBILITY PROBLEM

    Порой происходит синхронизация кэшей (когерентность кэшей). А когда она происходит будет описано позже todo link

    Для решения visibility problem можно использовать модификатор volatile. Также volatile обеспечивает атомарность чтения/записи long/double
    HAPPENS BEFORE - отношение, показывающее когда один поток гарантировано увидит изменения, сделанные другим потоком.
    атомарность - выполнение за неделимый отрезок, за который ни один поток не успеет повоздействовать.
    чтение и запись в long/double не атомарно (на 32 bit, так как работает сначала со старшими 4 байтами, а потом с младшими)

    Пример:
    long v = 0xAAAAAAAAEEEEEEEE
    th1 хочет считать v
    th2 хочет запсать в v 0xBBBBBBBBCCCCCCCC

    th2 write 0xBBBBBBBB -> v = 0xBBBBBBBBEEEEEEEE
    th1 read 0xBBBBBBBB
    th1 read 0xEEEEEEEEE
    th1 obtain v = 0xBBBBBBBBEEEEEEEE
    th2 write 0xCCCCCCCC -> v = 0xBBBBBBBBCCCCCCCC
     */
    private static /*volatile*/ long val; //с volatile медленнее, зато всегда видно
    private static volatile ThreadWorkMode workMode;

    public static void main(String[] args) throws InterruptedException {
        Runnable counter = () -> {
            ThreadWorkMode visibleWorkMode = workMode;
            while (true) {
                switch (workMode) {
                    case RUNNING:
                        val++; //неатомарная операция
                        break;
                    case PAUSED:
                        break;
                    case STOPPED:
                        return;
                }
                if (visibleWorkMode != workMode) {
                    System.out.println(Thread.currentThread() + ": I see that mode changed from " + visibleWorkMode + " to " + workMode);
                    visibleWorkMode = workMode;
                }
            }
        };

        Thread thread = new Thread(counter);
        workMode = ThreadWorkMode.RUNNING;
        System.out.println("Before start: " + val);
        thread.start();
        System.out.println("After start: " + val);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Before pause: " + val);
        workMode = ThreadWorkMode.PAUSED;
        System.out.println("After pause: " + val);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Before stop: " + val);
        workMode = ThreadWorkMode.STOPPED;
        System.out.println("After stop: " + val);

        thread.join();
        System.out.println("After real stop: " + val);

    }
}
