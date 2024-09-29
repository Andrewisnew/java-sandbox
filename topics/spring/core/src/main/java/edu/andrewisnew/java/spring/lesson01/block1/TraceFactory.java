package edu.andrewisnew.java.spring.lesson01.block1;

import java.util.random.RandomGenerator;

public class TraceFactory {
    private final RandomGenerator randomGenerator;

    public TraceFactory(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Trace createTrace() {
        return new Trace(randomGenerator.nextInt(50, 200));
    }
}
