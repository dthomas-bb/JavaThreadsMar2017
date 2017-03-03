package interrupts;

class MyJob implements Runnable {

    public void run() {
        try {
            while (true) {
                // do complicated and multi-step, transactional, processing
                int x = 99;
                x = x * 2;

                synchronized (this) {
                    this.wait(); // throws interrupted, so do all blocking operations
                }
                int y = x;
            }
        } catch (InterruptedException ie) {
            System.out.println("Shutting down by request");
        }
    }
}

public class ShutMeDown {

    public static void main(String[] args) throws Throwable {
        Thread t1 = new Thread(new MyJob());
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
        System.out.println("Interrupt sent...");

    }
}
