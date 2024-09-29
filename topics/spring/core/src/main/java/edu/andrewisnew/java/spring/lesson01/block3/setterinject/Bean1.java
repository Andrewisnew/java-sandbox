package edu.andrewisnew.java.spring.lesson01.block3.setterinject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bean1 {
    private Bean2 bebeen2;

    @Override
    public String toString() {
        return "Bean1{" +
                "bean2=" + bebeen2 +
                '}';
    }

    @Autowired
    private void setBebeen2(Bean2 bebeen2, Bean2 bebeen1) {//можно даже несколько за раз и модификатор неважен
        this.bebeen2 = bebeen2;
        System.out.println(bebeen1);
        System.out.println(bebeen2);
    }
}
