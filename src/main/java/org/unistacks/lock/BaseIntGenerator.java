package org.unistacks.lock;

/**
 * @author Gyges Zean
 * @date 2017/12/10
 */
public abstract class BaseIntGenerator {

    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
