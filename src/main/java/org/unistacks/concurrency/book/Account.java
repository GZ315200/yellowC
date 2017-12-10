package org.unistacks.concurrency.book;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Gyges on 2017/10/23
 */
public class Account {

    private AtomicLong balance;

    public Account() {
        balance = new AtomicLong();
    }

    public long getBalance() {
        return balance.get();
    }

    public void setBalance(long balance) {
        this.balance.set(balance);
    }

    public void addAmount(long amount) {
        this.balance.getAndAdd(amount);
    }

    public void subAmount(long amount) {
        this.balance.getAndAdd(-amount);
    }


}
