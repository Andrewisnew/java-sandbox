package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.ships;

import java.util.concurrent.TimeUnit;

public class Dock extends Thread {
    private final ProductType productType;
    private final Tunnel tunnel;

    public Dock(ProductType productType, Tunnel tunnel) {
        this.productType = productType;
        this.tunnel = tunnel;
        setName("Dock_" + productType);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("[DOCK] " + productType + " wait for ship");
            Ship ship = tunnel.getShip(productType);
            System.out.println("[DOCK] " + productType + " ship arrived");
            int capacity = ship.capacity();
            for (int i = 0; i < capacity; i += 10) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("[DOCK] " + productType + " " + (i + 10) + " / " + capacity);
            }
            System.out.println("[DOCK] " + productType + " ship gone");
        }
    }
}
