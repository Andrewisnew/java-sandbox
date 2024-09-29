package edu.andrewisnew.java.hibernate.inheritance.table_per_class_mapped_superclass;

import edu.andrewisnew.java.hibernate.inheritance.Color;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AttributeOverride(
        name = "color",
        column = @Column(name = "rect_color", nullable = false))
public class Rectangle extends Shape {
    private int a;
    private int b;

    public Rectangle(Color color, int a, int b) {
        super(color);
        this.a = a;
        this.b = b;
    }

    public Rectangle() {
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "id=" + id +
                ", color=" + color +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
