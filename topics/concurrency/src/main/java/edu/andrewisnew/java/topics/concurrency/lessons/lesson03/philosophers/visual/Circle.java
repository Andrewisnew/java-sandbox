package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.philosophers.visual;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class Circle {
    Ellipse2D.Double shape;
    Color color;

    public Circle(Ellipse2D.Double shape, Color color) {
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
}
