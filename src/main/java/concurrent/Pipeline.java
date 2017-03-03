package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Pipeline {

    public static void main(String[] args) throws Throwable {
        BlockingQueue<Double> trigger = new ArrayBlockingQueue<>(10);
        BlockingQueue<Double> random = new ArrayBlockingQueue<>(10);
        BlockingQueue<Double> sins = new ArrayBlockingQueue<>(10);
        final double POISON = Double.NaN;

        Runnable randomizer = () -> {
            try {
                double val;
                while (true) {
                    val = trigger.take();
                    random.put(ThreadLocalRandom.current().nextDouble(Math.PI * 2));
                }
            } catch (InterruptedException ie) {
                System.out.println("Shutdown requested...");
            }
        };
        new Thread(randomizer).start();
        new Thread(randomizer).start();
        new Thread(randomizer).start();
        new Thread(randomizer).start();

        Runnable compute = () -> {
            try {
                double val;
                while (true) {
                    val = random.take();
                    sins.put(Math.sin(val));
                }
            } catch (InterruptedException ie) {
                System.out.println("Shutdown requested...");
            }
        };

        new Thread(compute).start();
        new Thread(compute).start();
        new Thread(compute).start();
        new Thread(compute).start();
        new Thread(compute).start();
        new Thread(compute).start();

        Runnable reduce = () -> {
            double total = 0;
            long count = 0;
            double value = 0;
            try {
                while (true) {
                    total += sins.poll(1, TimeUnit.SECONDS);
                    count++;
                }
            } catch (Exception e) {
                System.out.println("Exception!!!!");
            }
            System.out.println("Average is " + (total / count));
            System.exit(0);
        };
        new Thread(reduce).start();

        for (int i = 0; i < 5_000_000; i++) {
            trigger.put(0.0);
        }
        System.out.println("All triggers put...");
    }
}
