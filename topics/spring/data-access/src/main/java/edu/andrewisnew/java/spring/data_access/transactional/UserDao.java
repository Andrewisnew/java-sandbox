package edu.andrewisnew.java.spring.data_access.transactional;

import edu.andrewisnew.java.spring.data_access.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public void changeApples(User provider, User consumer, int numberOfApples, long delayBetweenUpdates) {
        namedParameterJdbcTemplate.update("UPDATE USERS SET apples=:apples WHERE id=:id;", Map.of(
                "apples", provider.apples() - numberOfApples,
                "id", provider.id()
        ));
        try {
            TimeUnit.MILLISECONDS.sleep(delayBetweenUpdates);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        namedParameterJdbcTemplate.update("UPDATE USERS SET apples=:apples WHERE id=:id;", Map.of(
                "apples", consumer.apples() + numberOfApples,
                "id", consumer.id()
        ));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void setApples(User user, int numberOfApples) {
        namedParameterJdbcTemplate.update("UPDATE USERS SET apples=:apples WHERE id=:id;", Map.of(
                "apples", numberOfApples,
                "id", user.id()
        ));
    }

    public User insertUser(User user) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int update = namedParameterJdbcTemplate.update("INSERT INTO USERS(name,age,apples) VALUES(:name,:age,:apples);",
                new MapSqlParameterSource(Map.of(
                        "name", user.name(),
                        "age", user.age(),
                        "apples", user.apples()
                )), generatedKeyHolder);
        long id = (long) generatedKeyHolder.getKeys().get("id");
        return new User(id, user.name(), user.age(), user.apples());
    }

    public User getUser(long id) {
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id=:id", Map.of("id", id), (rs, rowNum) -> {
            String name = rs.getString("name");
            int age = rs.getInt("age");
            int apples = rs.getInt("apples");
            return new User(id, name, age, apples);
        });
    }
}
