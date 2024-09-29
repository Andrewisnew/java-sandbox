package edu.andrewisnew.java.hibernate.inheritance.inheritance_common_table;

import edu.andrewisnew.java.hibernate.inheritance.Color;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
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
