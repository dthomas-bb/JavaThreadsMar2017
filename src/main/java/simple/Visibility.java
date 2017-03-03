package simple;

class MyJob2 implements Runnable {
    private boolean stop = false;
    
    @Override
    public void run() {
        System.out.println("MyJob2 starting...");
        while (!stop)
            ;
        System.out.println("Stopped!!!!");
    }
    
    public void stop() {
        this.stop = true;
    }
}

public class Visibility {
    public static void main(String[] args) throws Throwable {
        MyJob2 j = new MyJob2();
        new Thread(j).start();
        Thread.sleep(2000);
        j.stop();
        System.out.println("Shutting down!");
    }
}
