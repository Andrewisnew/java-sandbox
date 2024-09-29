package edu.andrewisnew.java.spring.lesson01.block6;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Bean1 {
    @Value("1,100;4,500")
    private RangedValues rangedValues;

    public RangedValues rangedValues() {
        return rangedValues;
    }
}
