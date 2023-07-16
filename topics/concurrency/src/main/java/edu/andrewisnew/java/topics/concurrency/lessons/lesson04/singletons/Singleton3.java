package edu.andrewisnew.java.topics.concurrency.lessons.lesson04.singletons;

public class Singleton3 {
    public static volatile Singleton3 SINGLETON;

    private static final Object lock = new Object();

    private Singleton3() {
    }

    public static Singleton3 getSingleton() {
        if (SINGLETON != null) {
            return SINGLETON;
        }
        //DCL
        synchronized (lock) {
            return SINGLETON != null ? SINGLETON : (SINGLETON = new Singleton3()); //и это можно ускорить записывая SINGLETON в локальную переменную
        }
    }
}

/*
    NEW edu/andrewisnew/java/topics/concurrency/lessons/lesson04/singletons/Singleton3
    DUP
    INVOKESPECIAL edu/andrewisnew/java/topics/concurrency/lessons/lesson04/singletons/Singleton3.<init> ()V
    DUP
    PUTSTATIC edu/andrewisnew/java/topics/concurrency/lessons/lesson04/singletons/Singleton3.SINGLETON : Ledu/andrewisnew/java/topics/concurrency/lessons/lesson04/singletons/Singleton3;

    потенциально INVOKESPECIAL может быть после PUTSTATIC и ссылка уже будет проинициализирована, но объект еще не сконструирован

 */
