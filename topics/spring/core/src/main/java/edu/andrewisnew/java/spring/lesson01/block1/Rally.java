package edu.andrewisnew.java.spring.lesson01.block1;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

public class Rally {
    private final TraceFactory traceFactory;
    private final CarFactory carFactory;
    private final RandomGenerator randomGenerator;
    public Rally(TraceFactory traceFactory, CarFactory carFactory, RandomGenerator randomGenerator) {
        this.traceFactory = traceFactory;
        this.carFactory = carFactory;
        this.randomGenerator = randomGenerator;
    }

    public void start() {
        Trace trace = traceFactory.createTrace();
        int carsNumber = randomGenerator.nextInt(5, 10);
        Map<Car, SpeedAndPosition> carToSpeed = new LinkedHashMap<>(carsNumber);
        for (int i = 0; i < carsNumber; i++) {
            Car car = carFactory.createCar();
            carToSpeed.put(car, new SpeedAndPosition(0, 0));
        }
        System.out.println("Trace length: " + trace.length());
        System.out.println(carsNumber + " cars on start: " + carToSpeed.keySet());
        System.out.println("GO!!!");
        boolean[] allFinished = {false};
        int[] finishNumber = {1};
        int second = 1;
        while (!allFinished[0]) {
            System.out.println(second + " second:");
            allFinished[0] = true;
            carToSpeed.forEach((car, speedAndPosition) -> {
                if (speedAndPosition.position != trace.length()) {
                    speedAndPosition.speed = calculateSpeed(car, speedAndPosition);
                    speedAndPosition.position = Math.min(trace.length(), speedAndPosition.position + speedAndPosition.speed);
                    if (speedAndPosition.position != trace.length()) {
                        allFinished[0] = false;
                    } else {
                        speedAndPosition.finishNumber = finishNumber[0]++;
                    }
                }
                System.out.println("-".repeat(speedAndPosition.position) + car.name()
                        + (speedAndPosition.finishNumber != 0 ? " " + speedAndPosition.finishNumber + " Place" : ""));
            });
            second++;
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int calculateSpeed(Car car, SpeedAndPosition speedAndPosition) {
        int calculatedSpeed = speedAndPosition.speed + (randomGenerator.nextInt(1, 10) > 3 ? 1 : -1) * randomGenerator.nextInt(car.maxAcceleration());
        int negativeToZeroSpeed = Math.max(0, calculatedSpeed);
        return Math.min(car.maxSpeed(), negativeToZeroSpeed);
    }

    private static class SpeedAndPosition {
        private int speed;
        private int position;
        private int finishNumber;

        public SpeedAndPosition(int speed, int position) {
            this.speed = speed;
            this.position = position;
        }

        public int speed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int position() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int finishNumber() {
            return finishNumber;
        }

        public void setFinishNumber(int finishNumber) {
            this.finishNumber = finishNumber;
        }
    }
}
