package reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyQueue {

    private int[] data = new int[10];
    private int count;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public int take() throws InterruptedException {
        int rv;
        lock.lock();
        try {
//        synchronized (this) {
            while (count == 0) {
//                this.wait();
                notEmpty.await();
            }

            rv = data[0];
            System.arraycopy(data, 1, data, 0, --count);
//            this.notify(); // preferably avoid notifyAll, but this doesn't work reliably!!!!
            notFull.signal();
        } finally {
            lock.unlock();
        }
        return rv;
    }

    public void put(int v) throws InterruptedException {
        lock.lock();
        try {
//        synchronized (this) {
            while (count >= data.length) {
//                this.wait();
                notFull.await();
            }
            data[count++] = v;
//            this.notify();
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}
