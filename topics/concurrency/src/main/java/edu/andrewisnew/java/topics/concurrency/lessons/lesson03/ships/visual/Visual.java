package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.ships.visual;

import edu.andrewisnew.java.topics.concurrency.lessons.lesson03.ships.Ship;

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
    private final List<Circle> mainCircles;
    private volatile int vacantCircleNum = 0;
    private static final Color BREAD_COLOR = new Color(128, 64, 48);
    private static final Color BANANA_COLOR = Color.YELLOW;
    private static final Color CLOTHES_COLOR = Color.CYAN;
    private final Map<Integer, Circle> circleById = new HashMap<>();
    private volatile int idSequence;

    public Visual() {
        setTitle("Drawing a Circle");
        setSize(800, 800);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainCircles = createCircles();
    }

    @SuppressWarnings("NonAtomicOperationOnVolatileField")
    private List<Circle> createCircles() {
        List<Circle> circles = new ArrayList<>();
        int width = 100;
        int height = 100;
        circles.add(new Circle(idSequence++, new Ellipse2D.Double(getRightUpper(200, width), getRightUpper(200, height), width, height), BREAD_COLOR.brighter()));
        circles.add(new Circle(idSequence++, new Ellipse2D.Double(getRightUpper(400, width), getRightUpper(200, height), width, height), BANANA_COLOR.brighter()));
        circles.add(new Circle(idSequence++, new Ellipse2D.Double(getRightUpper(600, width), getRightUpper(200, height), width, height), CLOTHES_COLOR.brighter()));
        circles.add(new Circle(idSequence++, new Ellipse2D.Double(getRightUpper(200, width), getRightUpper(600, height), width, height), BREAD_COLOR.brighter()));
        circles.add(new Circle(idSequence++, new Ellipse2D.Double(getRightUpper(400, width), getRightUpper(600, height), width, height), BANANA_COLOR.brighter()));
        circles.add(new Circle(idSequence++, new Ellipse2D.Double(getRightUpper(600, width), getRightUpper(600, height), width, height), CLOTHES_COLOR.brighter()));
        return circles;
    }

    private static double getRightUpper(int aix, int side) {
        return aix - side / 2.;
    }

    @Override
    public void paint(Graphics g) {
        synchronized (circleById) {
            Graphics2D g2d = (Graphics2D) g;
            for (Circle circle : mainCircles) {
                g2d.setColor(circle.getColor());
                g2d.fill(circle.getShape());
            }
            for (Circle circle : circleById.values()) {
                g2d.setColor(circle.getColor());
                g2d.fill(circle.getShape());
            }
        }
    }

    public void addShip(Ship ship) {
        int width = 50;
        int height = 50;
        synchronized (circleById) {
            int key = idSequence++;
            switch (ship.productType()) {
                case BREAD:
                    circleById.put(key, new Circle(key, new Ellipse2D.Double(getRightUpper(200, width), getRightUpper(200, height), width, height), BREAD_COLOR));
                    break;
                case BANANA:
                    circleById.put(key, new Circle(key, new Ellipse2D.Double(getRightUpper(400, width), getRightUpper(200, height), width, height), BANANA_COLOR));
                    break;
                case CLOTHES:
                    circleById.put(key, new Circle(key, new Ellipse2D.Double(getRightUpper(600, width), getRightUpper(200, height), width, height), CLOTHES_COLOR));
                    break;
            }
            repaint();
        }
    }

    public void removeShip(Ship ship) {
        int width = 50;
        int height = 50;
        synchronized (circleById) {
            int key = idSequence++;
            switch (ship.productType()) {
                case BREAD:
                    circleById.put(key, new Circle(key, new Ellipse2D.Double(getRightUpper(200, width), getRightUpper(200, height), width, height), BREAD_COLOR));
                    break;
                case BANANA:
                    circleById.put(key, new Circle(key, new Ellipse2D.Double(getRightUpper(400, width), getRightUpper(200, height), width, height), BANANA_COLOR));
                    break;
                case CLOTHES:
                    circleById.put(key, new Circle(key, new Ellipse2D.Double(getRightUpper(600, width), getRightUpper(200, height), width, height), CLOTHES_COLOR));
                    break;
            }
            repaint();
        }
    }
}
