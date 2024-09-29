package edu.andrewisnew.java.spring.data_access;

import edu.andrewisnew.java.spring.data_access.entities.User;
import edu.andrewisnew.java.spring.data_access.jdbc_template.Config;
import edu.andrewisnew.java.spring.data_access.jdbc_template.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringJUnitConfig(classes = Config.class)
public class JDBCTemplateTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void test() throws InterruptedException, ExecutionException {
        User john = userDao.insertUser(new User(0, "John", 14, 8));
        User mary = userDao.insertUser(new User(0, "Mary", 15, 4));
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<?> future = executorService.submit(() -> userDao.changeApples(john, mary, 2, TimeUnit.SECONDS.toMillis(5)));
        TimeUnit.SECONDS.sleep(2);
        executorService.submit(() -> {
            System.out.println(userDao.getUser(john.id()));
            System.out.println(userDao.getUser(mary.id()));
        });
        future.get();
        System.out.println(userDao.getUser(john.id()));
        System.out.println(userDao.getUser2(john.id()));
        System.out.println(userDao.getUser(mary.id()));
        System.out.println(userDao.getAllUsers());
        executorService.shutdown();
    }
}
