package edu.andrewisnew.java.topics.concurrency.lessons.lesson02;

public class Block3Thread {
    public static void main(String[] args) {
        /*
        процесс имеет свой id (pid). И ОС работает с процессами. Ему выделяется какое-то количество памяти,
        он может у ОС запросить например файл для чтения, и ОС предоставит ему дескриптор файла, в котором укажет,
        что данный файл используется этим процессом, таким образом любой другой процесс не сможет например удалить
        файл.
        В процессе может быть запущено некоторое количество потоков, которые из себя представляют нити исполнения программы
         */
        Thread currentThread = Thread.currentThread(); //получение ссылки на объект, представляющий текущий поток
        /*
        Изначально при запуске java программы свою работу начинает процесс. В нем один основной поток (main),
        который начнет свое выполнение с метода main(), и еще несколько демонов.

        Создавая новый поток, он создастся в контексте текущего процесса.

        При работе нескольких процессов один не может обратиться к памяти другого, это запрещено. Для коммуникации между процессами:
        1. Некоторые ОС предоставляют такой механизм как shared memory.
        2. Используются pipes (каналы). Один процесс пишет в канал, другой из него читает
        3. Сокеты.

        Потоки имеют доступ к памяти своего процесса. Но каждый поток обладает своим собственным стэком
         */

        //способы создания потоков
        //расширить Thread переопределив run()
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Approach 1: " + Thread.currentThread());
            }
        };

        //передать Runnable в thread
        Thread thread2 = new Thread(() -> System.out.println("Approach 2: " + Thread.currentThread()));

        //задание имя потоку
        thread1.setName("Appr1");
        //задание приоритета 1-10
        thread1.setPriority(5);
        //запуск потока
        thread1.start();
        try {
            thread1.join(); //так как main вызвал этот метод, то main будет приостановлен до тех пор, пока tread1 не завершит свою работу
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
    }


}
