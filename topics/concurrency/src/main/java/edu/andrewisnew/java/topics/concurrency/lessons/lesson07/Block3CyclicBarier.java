package edu.andrewisnew.java.topics.concurrency.lessons.lesson07;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
Рассмотрим следующий пример. Существует паромная переправа. Паром может переправлять одновременно по три автомобиля.
Чтобы не гонять паром лишний раз, нужно отправлять его, когда у переправы соберется минимум три автомобиля.
 */
public class Block3CyclicBarier {
    public static void main(String[] args) {
        //похож на предыдущий, но многоразовый, и при достижении 0 может выполнить нечто
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("Паром выехал!"));
        long count = cyclicBarrier.getParties(); //сколько ВСЕГО
        int numberWaiting = cyclicBarrier.getNumberWaiting();//сколько уже пришло

        Runnable car = () -> {
            try {
                System.out.println(Thread.currentThread() + "подъехал к старту");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + " поехал");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread() + " доехал");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {//если с одним из потоков что-то случилось, или с барьером, или исключение в run action
                throw new RuntimeException(e);
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(car);
        executorService.submit(car);
        executorService.submit(car);
        while (!cyclicBarrier.isBroken()) ;
        cyclicBarrier.reset();
        executorService.shutdown();

    }
}
