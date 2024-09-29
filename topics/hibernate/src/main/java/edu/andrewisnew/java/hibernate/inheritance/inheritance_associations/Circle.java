package edu.andrewisnew.java.hibernate.inheritance.inheritance_associations;

import edu.andrewisnew.java.hibernate.inheritance.Color;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "CIRCLE_ID") //не обязательно. При желании переименовать
public class Circle extends Shape {
    private int radius;

    public Circle(Color color, int radius) {
        super(color);
        this.radius = radius;
    }

    public Circle() {
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + id +
                ", color=" + color +
                ", radius=" + radius +
                '}';
    }
}
