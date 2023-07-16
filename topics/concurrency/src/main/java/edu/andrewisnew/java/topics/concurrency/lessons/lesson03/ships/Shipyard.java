package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.ships;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.lang.Math.abs;

public class Shipyard extends Thread {
    private static final List<Integer> CAPACITIES = List.of(10, 50, 100);
    private static final Random RANDOMIZER = new Random();
    private final ProductType productType;
    private final Tunnel tunnel;

    public Shipyard(ProductType productType, Tunnel tunnel) {
        this.productType = productType;
        this.tunnel = tunnel;
        setName("Shipyard_" + productType);
    }

    public Ship createShip() {
        Ship ship = new Ship(productType, CAPACITIES.get(abs(RANDOMIZER.nextInt()) % 3));
        System.out.println("[SHIPYARD] " + productType + " ship created");
        return ship;
    }

    @Override
    public void run() {
        while (true) {
            Ship ship = createShip();
            tunnel.addShip(ship);
        }
    }
}
