package edu.andrewisnew.java.hibernate.inheritance.inheritance_joined;

import edu.andrewisnew.java.hibernate.inheritance.Color;

import javax.persistence.Entity;

@Entity
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
