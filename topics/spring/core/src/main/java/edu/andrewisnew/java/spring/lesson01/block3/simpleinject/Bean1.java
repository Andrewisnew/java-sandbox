package edu.andrewisnew.java.spring.lesson01.block3.simpleinject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bean1 {
    @Autowired
    private Bean2 bean2;
    private Bean3 bean3;
    private Bean4 bean4;

    //@Autowired is not required here. Only if there are several constructors
    public Bean1(Bean3 bean3) {
        this.bean3 = bean3;
    }

    @Autowired
    public void setBean4(Bean4 bean4) {
        this.bean4 = bean4;
    }

    @Override
    public String toString() {
        return "Bean1{" +
                "bean2=" + bean2 +
                ", bean3=" + bean3 +
                ", bean4=" + bean4 +
                '}';
    }
}
