package timing;

public class QueueDemo {

    public static void main(String[] args) {
        MyQueue mq = new MyQueue();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    mq.put(i);
                    Thread.sleep((int) (Math.random()) * 10);
                } catch (InterruptedException ioe) {

                }
            }
            System.out.println("Producer completed");
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    int x;
                    if ((x = mq.take()) != i) {
                        System.out.println("!!!!!!! ERROR got " + x + " expected i");
                        Thread.sleep((int) (Math.random()) * 10);
                    }
                } catch (InterruptedException ioe) {

                }
            }
            System.out.println("Consumer completed");
        }).start();
        System.out.println("Threads kicked off");
    }
}
