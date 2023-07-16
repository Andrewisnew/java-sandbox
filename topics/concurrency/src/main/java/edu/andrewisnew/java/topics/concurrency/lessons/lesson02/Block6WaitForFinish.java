package edu.andrewisnew.java.topics.concurrency.lessons.lesson02;

public class Block6WaitForFinish {
    private static long val = 0;

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                val++;
            }
        });
        thread.start();
        //1
        while (thread.getState() != Thread.State.TERMINATED); //busy wait
        //2
        while (thread.isAlive()); //busy wait
        //3
        try {
            thread.join(); //wait на мониторе thread. Ждет пока thread не завершится
            thread.join(10); //со временем ожидания. По истечении времени поток возобновляет работу
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(val);
    }
}
