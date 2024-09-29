package edu.andrewisnew.java.spring.lesson01.block9.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.annotation.PostConstruct;

@ComponentScan("edu.andrewisnew.java.spring.lesson01.block9.profiles")
public class Config {
    @Autowired
    private ConfigurableEnvironment env;

    @PostConstruct
    private void init() {
        env.setActiveProfiles("light");
    }
}
