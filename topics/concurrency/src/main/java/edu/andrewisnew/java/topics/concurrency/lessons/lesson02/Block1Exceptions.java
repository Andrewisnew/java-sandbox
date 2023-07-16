package edu.andrewisnew.java.topics.concurrency.lessons.lesson02;

public class Block1Exceptions {
    public static void main(String[] args) throws Throwable {
        //method1(); //исключение вылетит за пределы точки входа потока (сейчас это main), и там его обработает дефолтный обработчик

        //обработчик можно переопределить
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                System.out.println("Thread:" + thread + ", stacktrace:");
                throwable.printStackTrace();
            }
        });
        method1();

        Thread mainThread = Thread.currentThread();
        System.out.println(mainThread); // Thread[main,5,main] - Thread[<имя>,<приоритет>,<группа>]
        mainThread.setUncaughtExceptionHandler((thread, throwable) -> System.out.println("Handler from main"));
        new Thread(() -> {throw new RuntimeException("exception");}).start(); // вызовется дефолтный обработчик
    }

    private static void method1() throws Throwable {
        try {
            method2();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e.fillInStackTrace(); //перезапиывает стэктрейс как будто исключение было создано тут
        }
    }

    private static void method2() {
        RuntimeException exception = new RuntimeException("You shall not pass"); //при создании заполняет стэктрейс
        throw exception;
    }
}
