package edu.andrewisnew.java.topics.concurrency.lessons.lesson07;

import java.util.concurrent.Phaser;

/*
Расписывает по фазам
 */
public class Block5Phaser {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);//количество потоков для завершения фазы
        System.out.println("Phase: " + phaser.getPhase());//0, текущая фаза
        System.out.println("Registered: " + phaser.getRegisteredParties());//1, сколько зарегано
        System.out.println("Arrived: " + phaser.getArrivedParties());//0, сколько уже есть
        System.out.println("Arrive phase: " + phaser.arrive()); //в какую фазу пришел
        System.out.println("========================");
        System.out.println("Phase: " + phaser.getPhase());//1, текущая фаза
        System.out.println("Registered: " + phaser.getRegisteredParties());//1, сколько зарегано
        System.out.println("Arrived: " + phaser.getArrivedParties());//0, сколько уже есть
        System.out.println("Arrive: " + phaser.arrive()); //сколько пришло после этого
        System.out.println("========================");
        System.out.println("Phase: " + phaser.getPhase());//2, текущая фаза
        phaser.register();
        System.out.println("Registered: " + phaser.getRegisteredParties());//2, сколько зарегано
        phaser.bulkRegister(3);
        System.out.println("Registered: " + phaser.getRegisteredParties());//5, сколько зарегано
//        System.out.println(phaser.arriveAndAwaitAdvance()); //2, текущая фаза. Блочимся до перехода на след фазу
        int registeredParties = phaser.getRegisteredParties();
        for (int i = 0; i < registeredParties; i++) {
            phaser.arriveAndDeregister();
            System.out.println("Registered: " + phaser.getRegisteredParties());
        }
        System.out.println(phaser.isTerminated());
    }
}
