package edu.andrewisnew.java.spring.lesson01.block2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
@PropertySource("classpath:lesson01/block2/props.properties")
public class PropertySourceConfig {
    private static final Logger log = LoggerFactory.getLogger(PropertySourceConfig.class);

    @Value("${db.url}")
    private String dbUrl;

    @Autowired
    private Environment environment;

    public PropertySourceConfig() {
        System.err.println("No db url at this moment: " + dbUrl); //см edu/andrewisnew/java/spring/lesson01/block5/lifecycle.md
    }

    @PostConstruct
    private void init() {
        log.info("Db url: {}", dbUrl);

        log.info("Property from env: {}", environment.getProperty("db.url"));
    }
}
