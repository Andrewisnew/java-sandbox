package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.philosophers;

import java.util.LinkedHashMap;
import java.util.Map;

public class Table {
    //List<Object> sectors = new ArrayList<>();
    Map<Philosopher, Sticks> philosopherSticks = new LinkedHashMap<>();

    public void placePhilosopher(Philosopher philosopher) {
        Stick leftStick;
        Stick rightStick;
        if (philosopherSticks.isEmpty()) {
            leftStick = new Stick();
            rightStick = new Stick();
        } else if (philosopherSticks.size() == 1) {
            Sticks somePhilosopherSticks = philosopherSticks.values().iterator().next();
            rightStick = somePhilosopherSticks.leftStick;
            leftStick = somePhilosopherSticks.rightStick;
        } else {
            Sticks somePhilosopherSticks = philosopherSticks.values().iterator().next();
            leftStick = new Stick();
            rightStick = somePhilosopherSticks.rightStick;
            somePhilosopherSticks.rightStick = leftStick;
        }
        philosopherSticks.put(philosopher, new Sticks(leftStick, rightStick));
    }

    public Stick getLeftStick(Philosopher philosopher) {
        return philosopherSticks.get(philosopher).leftStick;
    }

    public Stick getRightStick(Philosopher philosopher) {
        return philosopherSticks.get(philosopher).rightStick;
    }

    @Override
    public String toString() {
        return "Table{" +
                "philosopherSticks=" + philosopherSticks +
                '}';
    }

    private static class Sticks {
        Stick leftStick;
        Stick rightStick;

        public Sticks(Stick leftStick, Stick rightStick) {
            this.leftStick = leftStick;
            this.rightStick = rightStick;
        }

        public Stick getLeftStick() {
            return leftStick;
        }

        public Stick getRightStick() {
            return rightStick;
        }

        @Override
        public String toString() {
            return "Sticks{" +
                    "leftStick=" + leftStick +
                    ", rightStick=" + rightStick +
                    '}';
        }
    }

    private record Sector(int index, Object object) {
    }
}
