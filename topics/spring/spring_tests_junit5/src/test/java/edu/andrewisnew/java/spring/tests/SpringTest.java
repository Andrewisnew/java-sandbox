package edu.andrewisnew.java.spring.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
//@SpringJUnitConfig(classes = Config.class) - вместо двух аннотаций сверху
public class SpringTest {
    @Autowired
    private Calculator calculator;

    @Test
    public void testCalculator() {
        assertEquals(5, calculator.sum(2, 3));
    }
}
