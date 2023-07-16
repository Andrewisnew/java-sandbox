package edu.andrewisnew.java.topics.concurrency.lessons.lesson06;

import edu.andrewisnew.java.topics.concurrency.utils.ConcurrencyUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Block4CASMarkableReference {

    public static void main(String[] args) {
        A ref1 = new A();
        A ref2 = new A();
        A ref3 = new A();

        AtomicMarkableReference<A> atomic = new AtomicMarkableReference<>(ref1, true);
        AtomicStampedReference<A> atomicStamped = new AtomicStampedReference<>(ref1, 0); // как markable только boolean -> int
        atomicStamped.getStamp();

        ExecutorService service = Executors.newCachedThreadPool();

        service.submit(() -> {
            boolean[] wrapper = {true};
            A ref;
            do {
                ref = atomic.get(wrapper);  // сетит в аргумент текущий марк
                ConcurrencyUtils.sleep(1, TimeUnit.SECONDS);
            } while (!atomic.compareAndSet(ref, ref2, true, false)); // еще проверяет и меняют марку
        });

        service.submit(() -> {
            boolean[] wrapper = {true};
            ConcurrencyUtils.sleep(100, TimeUnit.MILLISECONDS);
            A ref;
            do {
                ref = atomic.get(wrapper);  // ref1
            } while (!atomic.compareAndSet(ref, ref3, true, false)); // ref3 B
        });

        service.submit(() -> {
            boolean[] wrapper = {true};
            ConcurrencyUtils.sleep(200, TimeUnit.MILLISECONDS);
            A ref;
            do {
                ref = atomic.get(wrapper);  // ref1
            } while (!atomic.compareAndSet(ref, ref1, false, false)); // ref3 B
        });

        service.shutdown();
    }

    private void aba() {
        A ref1 = new A();
        A ref2 = new A();
        A ref3 = new A();

        AtomicReference<A> atomic = new AtomicReference<>(ref1);
        AtomicStampedReference<A> atomicStamped = new AtomicStampedReference<>(ref1, 0);
        atomicStamped.getStamp();

        ExecutorService service = Executors.newCachedThreadPool();

        service.submit(() -> {
            try {
                A ref = atomic.get();  // ref1 A
                TimeUnit.SECONDS.sleep(1);
                atomic.compareAndSet(ref, ref2); // ABA
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                A ref = atomic.get();  // ref1
                atomic.compareAndSet(ref, ref3); // ref3 B
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                A ref = atomic.get();  // ref3
                atomic.compareAndSet(ref, ref1);  // ref1 A
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.shutdown();
    }


    static class A {
        volatile int field;
    }
}
