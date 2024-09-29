package edu.andrewisnew.java.spring.lesson01.block7.bean_facrory;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Bean {
    private int power;

    public Bean() {
        this.power = ThreadLocalRandom.current().nextInt();
    }

    public int power() {
        return power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bean bean = (Bean) o;
        return power == bean.power;
    }

    @Override
    public int hashCode() {
        return Objects.hash(power);
    }
}
