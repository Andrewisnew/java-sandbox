package edu.andrewisnew.java.spring.data_access.transactional;

import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * A - atomic. Операция неразрывна, всё или ничего
 * C - consistency. Переводит из одного консистентного состояния в другое
 * I - isolation. Транзакции выполняющиеся параллельно не влияют друг на друга
 * D - durability. Надёжность. Если транзакция завершилась, то можно быть уверенным, что изменения зафиксируются
 * <p>
 * Работает на AOP. Необходим PlatformTransactionManager и EnableTransactionManagement
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"edu.andrewisnew.java.spring.data_access.transactional"})
public class Config {
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource(new Driver(), "jdbc:postgresql://localhost:5432/test", "postgres", "root");
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
