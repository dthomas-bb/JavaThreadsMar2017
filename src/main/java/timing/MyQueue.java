package timing;

public class MyQueue {

    private int[] data = new int[10];
    private int count;

    public int take() throws InterruptedException {
        int rv;
        synchronized (this) {
            while (count == 0) {
                this.wait();
            }

            rv = data[0];
            System.arraycopy(data, 1, data, 0, --count);
            this.notify(); // preferably avoid notifyAll, but this doesn't work reliably!!!!
        }
        return rv;
    }

    public void put(int v) throws InterruptedException {
        synchronized (this) {
            while (count >= data.length) {
                this.wait();
            }
            data[count++] = v;
            this.notify();
        }
    }
}
