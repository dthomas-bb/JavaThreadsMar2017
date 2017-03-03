package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ParallelMean {

    public static void main(String[] args) throws Throwable {
        final ExecutorService es = Executors.newFixedThreadPool(8);
        final int X = 100_000;
        final int Y = 1_000;
        double[] da = new double[X * Y];
        Future[] setters = new Future[Y];
        for (int i = 0; i < Y; i++) {
            final int myI = i;
            setters[i] = es.submit(() -> {
                for (int col = 0; col < X; col++) {
                    da[X * myI + col] = ThreadLocalRandom.current().nextDouble(0, Math.PI*2);
                }
            });
        }
        for (int i = 0; i < Y; i++) {
            setters[i].get();
        }
        // now we know our data is initialized
        Future<Double>[] averagers = new Future[Y];
        for (int i = 0; i < Y; i++) {
            final int myI = i;
            averagers[i] = es.submit(()->{
                double res = 0;
                for (int col = 0; col < X; col++) {
                    res+=Math.sin(da[X * myI + col]);
                }
                return res / X;
            });
        }
        double total = 0;
        for (int i = 0; i < Y; i++) {
            total += averagers[i].get();
        }
        System.out.println("Average is " + total / Y);
        es.shutdown();
        es.awaitTermination(10, TimeUnit.DAYS);
    }
}