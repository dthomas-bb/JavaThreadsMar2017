package simple;

class MyJob implements Runnable {
    private int i = 0;
    @Override
    public void run() {
        for (; i < 1_000; i++) {
            System.out.println(Thread.currentThread().getName() + " i is " + i);
        }
    }
}

public class SimpleThread {
    public static void main(String[] args) {
        Runnable r = new MyJob();
        
        Thread t = new Thread(r);
//        t.setDaemon(true);
        t.start();
        Thread t1 = new Thread(r);
//        t1.setDaemon(true);
        t1.start();
        System.out.println("Finished");
    }
}
