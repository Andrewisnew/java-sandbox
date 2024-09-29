package edu.andrewisnew.java.spring.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {
    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    public int sum(int a, int b) {
        log.info("Sum of {} and {} called", a, b);
        return a + b;
    }
}
