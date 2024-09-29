package edu.andrewisnew.java.hibernate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "SuperUser")//имя сущности (HQL будет использовать его)
@Table(name = "users")//имя в бд
@DynamicInsert //hibernate создаёт запросы для insert/update при создании PU. Эта аннотация дизейблит фичу
@DynamicUpdate
@Immutable //оптимизирует и запрещает апдейт
public class User {
    @Id //обязателен для всех Entity
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_seq")
    private Long id;
    @Access(AccessType.PROPERTY) //если выставлен property, то обращение к полям будет через get/set
    @Size(min = 8) //при сохранении если есть в cp валидатор
    private String name;
    private int age;
    private int apples;
    @Transient //не сохранять
    private byte[] bigContent;

    public User(String name, int age, int apples) {
        this.name = name;
        this.age = age;
        this.apples = apples;
    }

    //обязателен, иначе исключение при чтении из БД. Справка: хибернейт кэширует в памяти то, что сохраняет в БД.
    public User() {
    }

    @NotNull //для того чтобы работали при сохранении данных нужно подключить hibernate-validator
    @Size(
            min = 9,
            max = 255,
            message = "Name is required, maximum 255 characters."
    ) //можно как над полем так и над методом get. Оба будут использованы
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", apples=" + apples +
                '}';
    }

//    @Size(
//            min = 10,
//            max = 255,
//            message = "Name is required, minimum 10 characters."
//    ) //ошибка при валидации, нельзя указывать над set
    public void setName(String name) {
        System.out.println("AAAAAAAAAAAAAAA");
        this.name = name;
    }
}
