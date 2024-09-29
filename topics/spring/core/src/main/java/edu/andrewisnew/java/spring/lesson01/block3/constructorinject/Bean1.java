package edu.andrewisnew.java.spring.lesson01.block3.constructorinject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Bean1 {
    private Bean2 bebeen2;


    public Bean1(@Qualifier("anotherBean2") Bean2 bean2) {
        this.bebeen2 = bean2;
    }


    @Override
    public String toString() {
        return "Bean1{" +
                "bean2=" + bebeen2 +
                '}';
    }
}
