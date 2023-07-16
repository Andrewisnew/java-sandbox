package edu.andrewisnew.java.topics.concurrency.lessons.lesson06;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
/*
Хранит не в одной переменной, а в массиве, где количество элементов равно количеству ядер.
При write записывает в свою ячейку, при read суммирует значение из всех ячеек
 */
public class Block6LongAdder {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        long sum = longAdder.sum();
        longAdder.add(3);
        longAdder.decrement();
        longAdder.increment();
        longAdder.reset();
        longAdder.sumThenReset();

        LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
        longAccumulator.accumulate(3);
        long l = longAccumulator.get();
        longAccumulator.reset();
        long thenReset = longAccumulator.getThenReset();

    }

}
