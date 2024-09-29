package edu.andrewisnew.java.spring.lesson01.block5;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.err.println("Before init: %s".formatted(beanName));
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.err.println("After init: %s".formatted(beanName));
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    public static void main(String[] args) {
        System.out.println("broker132d".matches("broker\\d+"));
    }
}
