package org.unistacks.lock;

/**
 * @author Gyges Zean
 * @date 2017/12/10
 */
public class SyncEventGenerator extends BaseIntGenerator {

    private int currentEventValue = 0;


    @Override
    public synchronized int next() {
        ++ currentEventValue;
        Thread.yield();
        ++ currentEventValue;
        return currentEventValue;
    }

    public static void main(String[] args) {
        EventChecker.test(new SyncEventGenerator());
    }
}
