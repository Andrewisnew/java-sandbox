package edu.andrewisnew.java.spring.lesson01.block4.scope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigLauncher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ScopePrototypeBean bean = context.getBean(ScopePrototypeBean.class);
        ScopePrototypeBean bean1 = context.getBean(ScopePrototypeBean.class);
        System.out.println(bean != bean1);
        System.out.println(bean.power() == bean.power());

        ScopePrototypeWithTargetClass bean2 = context.getBean(ScopePrototypeWithTargetClass.class);
        ScopePrototypeWithTargetClass bean21 = context.getBean(ScopePrototypeWithTargetClass.class);
        System.out.println(bean2 == bean21);
        System.out.println(bean2.power() != bean2.power());

        ScopePrototypeWithInterfaces bean3 = context.getBean(ScopePrototypeWithInterfaces.class);
        ScopePrototypeWithInterfaces bean31 = context.getBean(ScopePrototypeWithInterfaces.class);
        System.out.println(bean3.getClass());
        System.out.println(bean3 != bean31);
        System.out.println(bean3.power() == bean3.power());
    }
}
