package edu.andrewisnew.java.spring.data_access.jdbc_template;

import edu.andrewisnew.java.spring.data_access.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
  create table users (
  id bigserial primary key,
  name varchar not null,
  age integer not null,
  apples integer not null
  );
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> getAllUsers() {
        List<User> users = jdbcTemplate.query("SELECT * FROM USERS;", (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            int apples = rs.getInt("apples");
            return new User(id, name, age, apples);
        });
        return users;
    }

    public User getUser(long id) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id=?;", (rs, rowNum) -> {
            String name = rs.getString("name");
            int age = rs.getInt("age");
            int apples = rs.getInt("apples");
            return new User(id, name, age, apples);
        }, id);
        return user;
    }

    public User getUser2(long id) {
        User user = namedParameterJdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id=:id;", Map.of("id", id), (rs, rowNum) -> {
            String name = rs.getString("name");
            int age = rs.getInt("age");
            int apples = rs.getInt("apples");
            return new User(id, name, age, apples);
        });
        return user;
    }

    public User insertUser(User user) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory("INSERT INTO USERS(name,age,apples) VALUES(?,?,?);",
                Types.VARCHAR, Types.INTEGER, Types.INTEGER);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        int update = jdbcTemplate.update(preparedStatementCreatorFactory.newPreparedStatementCreator(List.of(user.name(), user.age(), user.apples())), generatedKeyHolder);
        long id = (long) generatedKeyHolder.getKeys().get("id");
        return new User(id, user.name(), user.age(), user.apples());
    }

    public void changeApples(User provider, User consumer, int numberOfApples, long delayBetweenUpdates) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "root");
            //https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
            //если true то после завершения работы каждого стейтмента будет вызван commit
            c.setAutoCommit(false);
            try (PreparedStatement stmt = c.prepareStatement("UPDATE USERS SET apples=? WHERE id=?;")) {
                stmt.setInt(1, provider.apples() - numberOfApples);
                stmt.setLong(2, provider.id());
                stmt.executeUpdate();
            }
            System.out.println("Wait");
            TimeUnit.MILLISECONDS.sleep(delayBetweenUpdates);
            try (PreparedStatement stmt = c.prepareStatement("UPDATE USERS SET apples=? WHERE id=?;")) {
                stmt.setInt(1, consumer.apples() + numberOfApples);
                stmt.setLong(2, consumer.id());
                stmt.executeUpdate();
            }
            c.commit();
        } catch (Exception e) {
            if (c != null) {
                try {
                    c.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }
}
