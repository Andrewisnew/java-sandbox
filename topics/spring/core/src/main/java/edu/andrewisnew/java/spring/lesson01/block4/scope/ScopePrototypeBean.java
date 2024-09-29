package edu.andrewisnew.java.spring.lesson01.block4.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScopePrototypeBean {
    private final int power;

    public ScopePrototypeBean() {
        power = ThreadLocalRandom.current().nextInt();
    }

    public int power() {
        return power;
    }
}
