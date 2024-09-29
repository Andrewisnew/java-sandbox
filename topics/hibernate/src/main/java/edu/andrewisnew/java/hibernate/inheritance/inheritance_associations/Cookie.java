package edu.andrewisnew.java.hibernate.inheritance.inheritance_associations;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Cookie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne //в таблице Cookie появится внешний ключ
    private Shape shape;

    public Cookie(Shape shape) {
        this.shape = shape;
    }

    public Cookie() {
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "id=" + id +
                ", shape=" + shape +
                '}';
    }
}
