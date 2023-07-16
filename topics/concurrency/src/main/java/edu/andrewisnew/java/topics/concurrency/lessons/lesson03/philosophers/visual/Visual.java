package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.philosophers.visual;

import edu.andrewisnew.java.topics.concurrency.lessons.lesson03.philosophers.Philosopher;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Visual extends JFrame {
    private final List<Circle> circles;
    private static final int CIRCLES_COUNT = 5;
    private volatile int vacantCircleNum = 0;
    private static final Color FREE_COLOR = Color.CYAN;
    private static final Color WAIT_COLOR = Color.YELLOW;
    private static final Color BUSY_COLOR = Color.GREEN;
    private final Map<Philosopher, Circle> ownerToCircle = new HashMap<>();

    public Visual() {
        setTitle("Drawing a Circle");
        setSize(800, 800);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        circles = createCircles();
    }

    private List<Circle> createCircles() {
        List<Circle> circles = new ArrayList<>();
        int width = 100;
        int height = 100;
        circles.add(new Circle(new Ellipse2D.Double(getRightUpper(400, width), getRightUpper(200, height), width, height), FREE_COLOR));
        circles.add(new Circle(new Ellipse2D.Double(getRightUpper(200, width), getRightUpper(400, height), width, height), FREE_COLOR));
        circles.add(new Circle(new Ellipse2D.Double(getRightUpper(300, width), getRightUpper(600, height), width, height), FREE_COLOR));
        circles.add(new Circle(new Ellipse2D.Double(getRightUpper(500, width), getRightUpper(600, height), width, height), FREE_COLOR));
        circles.add(new Circle(new Ellipse2D.Double(getRightUpper(600, width), getRightUpper(400, height), width, height), FREE_COLOR));
        return circles;
    }

    private static double getRightUpper(int aix, int side) {
        return aix - side / 2.;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (Circle circle : circles) {
            g2d.setColor(circle.getColor());
            g2d.fill(circle.getShape());
        }
    }

    public void addPhilosopher(Philosopher philosopher) {
        synchronized (circles) {
            Circle circle = circles.get(vacantCircleNum++);
            ownerToCircle.put(philosopher, circle);
        }
    }

    public void setBusy(Philosopher philosopher) {
        synchronized (circles) {
            Circle circle = ownerToCircle.get(philosopher);
            circle.setColor(BUSY_COLOR);
            repaint();
        }
    }

    public void setWaiting(Philosopher philosopher) {
        synchronized (circles) {
            Circle circle = ownerToCircle.get(philosopher);
            circle.setColor(WAIT_COLOR);
            repaint();
        }
    }

    public void setFree(Philosopher philosopher) {
        synchronized (circles) {
            Circle circle = ownerToCircle.get(philosopher);
            circle.setColor(FREE_COLOR);
            repaint();
        }
    }
}
