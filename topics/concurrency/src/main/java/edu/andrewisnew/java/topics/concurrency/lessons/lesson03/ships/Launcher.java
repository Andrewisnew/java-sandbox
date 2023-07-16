package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.ships;

public class Launcher {
    public static void main(String[] args) {
        Tunnel tunnel = new Tunnel();
        Dock bananaDock = new Dock(ProductType.BANANA, tunnel);
        Dock clothesDock = new Dock(ProductType.CLOTHES, tunnel);
        Dock breadDock = new Dock(ProductType.BREAD, tunnel);
        Shipyard bananaShipyard = new Shipyard(ProductType.BANANA, tunnel);
        Shipyard clothesShipyard = new Shipyard(ProductType.CLOTHES, tunnel);
        Shipyard breadShipyard = new Shipyard(ProductType.BREAD, tunnel);

        breadDock.start();
        bananaDock.start();
        clothesDock.start();
        bananaShipyard.start();
        clothesShipyard.start();
        breadShipyard.start();
    }
}
