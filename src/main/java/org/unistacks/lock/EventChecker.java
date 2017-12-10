package org.unistacks.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Gyges Zean
 * @date 2017/12/10
 */
public class EventChecker implements Runnable {

    private BaseIntGenerator generator;

    private final int id;

    public EventChecker(BaseIntGenerator baseIntGenerator,int ident) {
        this.generator = baseIntGenerator;
        this.id = ident;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            if (val % 2 != 0) {
                System.out.println(val + " not event!");
                generator.cancel();
            }
        }
    }

    public static void test(BaseIntGenerator gp, int count) {
        System.out.println("please Control-C to exit!");
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i ++) {
            exec.execute(new EventChecker(gp,i));
        }
        exec.shutdown();
    }

    public static void test(BaseIntGenerator gp) {
        test(gp,10);
    }

}
