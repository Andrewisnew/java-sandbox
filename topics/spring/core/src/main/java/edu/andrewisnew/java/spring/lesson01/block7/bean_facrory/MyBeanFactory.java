package edu.andrewisnew.java.spring.lesson01.block7.bean_facrory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactory implements FactoryBean<Bean> {
    @Override
    public Bean getObject() throws Exception {
        return new Bean();
    }

    @Override
    public Class<?> getObjectType() {
        return Bean.class;
    }
}
