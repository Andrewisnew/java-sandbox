package edu.andrewisnew.java.topics.concurrency.lessons.lesson07;

import edu.andrewisnew.java.topics.concurrency.utils.ConcurrencyUtils;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/*
Рассмотрим следующий пример. Есть два грузовика: один едет из пункта A в пункт D, другой из пункта B в пункт С.
Дороги AD и BC пересекаются в пункте E. Из пунктов A и B нужно доставить посылки в пункты C и D.
Для этого грузовики в пункте E должны встретиться и обменяться соответствующими посылками.
 */
public class Block4Exchanger {
    public static void main(String[] args) {
        //точка синхронизации где поток ждет пока придет другой, что-то своё отдаёт, и что-то получает взамен
        //два метода один с таймаутом другой без. По таймауту эксепшен
        Exchanger<Package> exchanger = new Exchanger<>();
        Location a = new Location("A");
        Location b = new Location("B");
        Location c = new Location("C");
        Location d = new Location("D");
        Location e = new Location("E");
        Truck fromA = new Truck(a, d, e, new Package("From A"), 10);
        Truck fromB = new Truck(b, c, e, new Package("From B"), 20);
        Function<Truck, Runnable> deliveryTruck = truck -> () -> {
            int avgSpeed = truck.avgSpeed();
            System.out.println(truck + " выехал из " + truck.from());
            ConcurrencyUtils.sleep(50 / avgSpeed, TimeUnit.SECONDS);
            System.out.println(truck + " привез " + truck.p() + " в " + truck.transit());
            Package newP;
            try {
                newP = exchanger.exchange(truck.p());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            truck.setP(newP);
            System.out.println(truck + " получил " + newP);
            ConcurrencyUtils.sleep(30 / avgSpeed, TimeUnit.SECONDS);
            System.out.println(truck + " привез " + truck.p() + " в " + truck.to());

        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(deliveryTruck.apply(fromA));
        executorService.submit(deliveryTruck.apply(fromB));
        executorService.shutdown();
    }

    static class Truck{
        Location from;
        Location to;
        Location transit;
        Package p;
        int avgSpeed;

        public Truck(Location from, Location to, Location transit, Package p, int avgSpeed) {
            this.from = from;
            this.to = to;
            this.transit = transit;
            this.p = p;
            this.avgSpeed = avgSpeed;
        }

        public Location from() {
            return from;
        }

        public Location to() {
            return to;
        }

        public Location transit() {
            return transit;
        }

        public Package p() {
            return p;
        }

        public int avgSpeed() {
            return avgSpeed;
        }

        public void setP(Package p) {
            this.p = p;
        }

        @Override
        public String toString() {
            return "Truck{" +
                    "from=" + from +
                    ", to=" + to +
                    '}';
        }
    }

    record Location(String name) {}

    record Package(String name) {}
}
