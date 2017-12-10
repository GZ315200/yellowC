package org.unistacks.lock;

/**
 * @author Gyges Zean
 * @date 2017/12/10
 */
public class EventGenerator extends BaseIntGenerator {

    private int currentEvent = 0;

    @Override
    public int next() {
        ++ currentEvent;
        ++ currentEvent;
        return currentEvent;
    }

    public static void main(String[] args) {
        EventChecker.test(new EventGenerator());
    }
}
