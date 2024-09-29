package edu.andrewisnew.java.hibernate;


import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Immutable //em.merge будет проигнорирован
public class Apple {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String color;

    public Apple() {
    }

    public Apple(String color) {
        this.color = color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apple that = (Apple) o;
        return id == that.id && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color);
    }

    @Override
    public String toString() {
        return "ImmutableApple{" +
                "id=" + id +
                ", color='" + color + '\'' +
                '}';
    }
}
