package edu.andrewisnew.java.spring.lesson01.block2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleConfig {
    @Bean
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }
}
