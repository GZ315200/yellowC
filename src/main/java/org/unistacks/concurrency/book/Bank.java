package org.unistacks.concurrency.book;

/**
 * Created by Gyges on 2017/10/23
 */
public class Bank implements Runnable {

    private Account account;

    public Bank(Account account) {
        this.account = account;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            account.subAmount(1000);
        }
    }
}
