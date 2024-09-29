package edu.andrewisnew.java.spring.lesson01.block4.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ScopePrototypeWithTargetClass {
    private final int power;

    public ScopePrototypeWithTargetClass() {
        power = ThreadLocalRandom.current().nextInt();
    }

    public int power() {
        return power;
    }
}
