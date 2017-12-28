package org.unistacks.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Gyges Zean
 * @date 2017/12/10
 */
public class MutexEventGenerator extends BaseIntGenerator {

    private int currentValue = 0;

    private Lock lock = new ReentrantLock();

    @Override
    public int next() {
        lock.lock();
        try {
            ++ currentValue;
            Thread.yield();
            ++ currentValue;
            return currentValue;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EventChecker.test(new MutexEventGenerator());
    }
}
