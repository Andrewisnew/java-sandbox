package edu.andrewisnew.java.spring.data_access.jdbc;

import edu.andrewisnew.java.spring.data_access.entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
  create table users (
  id bigserial primary key,
  name varchar not null,
  age integer not null,
  apples integer not null
  );
 */
public class UserDao {
    public UserDao() {
        //Когда загружается класс, он регистрирует себя в DriverManager#registeredDrivers.
        //Этот шаг можно опустить так как DriverManager.getConnection зарегистрирует всех java.sql.Driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
//        DriverManager.setLogWriter(new PrintWriter(System.out));
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        //Происходит перебор всех зарегистрированных драйверов, и берётся первый подошедший
        try (Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "root")) {
            try (Statement stmt = c.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;")) {
                    while (rs.next()) {
                        long id = rs.getLong("id");
                        String name = rs.getString("name");
                        int age = rs.getInt("age");
                        int apples = rs.getInt("apples");
                        users.add(new User(id, name, age, apples));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User getUser(long id) {
        //Происходит перебор всех зарегистрированных драйверов, и берётся первый подошедший
        try (Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "root")) {
            try (PreparedStatement stmt = c.prepareStatement("SELECT * FROM USERS WHERE id=?;")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        int age = rs.getInt("age");
                        int apples = rs.getInt("apples");
                        return new User(id, name, age, apples);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public User insertUser(User user) {
        try (Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "root")) {
            try (PreparedStatement stmt = c.prepareStatement("INSERT INTO USERS(name,age,apples) VALUES(?,?,?);", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.name());
                stmt.setInt(2, user.age());
                stmt.setInt(3, user.apples());
                int count = stmt.executeUpdate();
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    generatedKeys.next();
                    long id = generatedKeys.getLong(1);
                    return new User(id, user.name(), user.age(), user.apples());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
