package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.ships;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tunnel {
    private static final int CAPACITY = 5;
    private final Map<ProductType, List<Ship>> shipsPerType = new HashMap<>();
    private volatile int shipsInside;

    public Tunnel() {
        for (ProductType value : ProductType.values()) {
            shipsPerType.put(value, new ArrayList<>());
        }
    }

    public Ship getShip(ProductType productType) {
        synchronized (shipsPerType) {
            while (!consistsShipsOfType(productType)) {
                try {
                    shipsPerType.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            List<Ship> ships = shipsPerType.get(productType);
            shipsInside--;
            Ship ship = ships.remove(ships.size() - 1);
            shipsPerType.notifyAll();
            return ship;
        }
    }

    private boolean consistsShipsOfType(ProductType productType) {
        List<Ship> ships = shipsPerType.get(productType);
        return ships.size() != 0;

    }

    public void addShip(Ship ship) {
        synchronized (shipsPerType) {
            while (shipsInside == CAPACITY) {
                try {
                    shipsPerType.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            shipsPerType.get(ship.productType()).add(ship);
            shipsInside++;
            shipsPerType.notifyAll();
        }
    }
}
