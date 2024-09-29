package edu.andrewisnew.java.spring.lesson01.block1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.random.RandomGenerator;

public class CarFactory {
    private final RandomGenerator randomGenerator;

    public CarFactory(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }
    private static final AtomicInteger carSequence = new AtomicInteger();
    public Car createCar() {
        return new Car("car" + carSequence.getAndIncrement(),
                randomGenerator.nextInt(20, 50), randomGenerator.nextInt(6, 10));
    }
}
