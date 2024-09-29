package edu.andrewisnew.java.spring.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Config.class})
public class SpringTest {
    @Autowired
    private Calculator calculator;

    @Test
    public void testCalculator() {
        assertEquals(5, calculator.sum(2, 3));
    }
}
