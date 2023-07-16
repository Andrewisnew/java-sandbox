package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.ships.visual;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

public class Circle {
    final int id;
    Ellipse2D.Double shape;
    Color color;

    public Circle(int id, Ellipse2D.Double shape, Color color) {
        this.id = id;
        this.shape = shape;
        this.color = color;
    }

    public Ellipse2D.Double getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return id == circle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
