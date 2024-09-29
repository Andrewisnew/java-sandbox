package edu.andrewisnew.java.hibernate;


import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Subselect("select a.id as id, a.color as color from Apple a where a.color = 'green'")
@Synchronize("Apple") //для синхронизации перед выполнением запроса
public class GreenApple {
    @Id
    private long id;
    private String color;

    public GreenApple() {
    }

    public GreenApple(String color) {
        this.color = color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GreenApple that = (GreenApple) o;
        return id == that.id && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color);
    }

    @Override
    public String toString() {
        return "GreenApple{" +
                "id=" + id +
                ", color='" + color + '\'' +
                '}';
    }
}
