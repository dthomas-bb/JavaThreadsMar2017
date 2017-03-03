package timing;

public class MyQueue {

    private int[] data = new int[10];
    private volatile int count;

    public int take() {
        synchronized (this) {
            while (count == 0) {

            }

            int rv = data[0];
            System.arraycopy(data, 1, data, 0, --count);
        }
        return rv;
    }

    public void put(int v) {
        synchronized (this) {
            while (count >= data.length) {

            }
            data[count++] = v;
        }
    }
}
