package org.unistacks.main;

import org.unistacks.concurrency.book.Account;
import org.unistacks.concurrency.book.AddTask;
import org.unistacks.concurrency.book.Bank;
import org.unistacks.concurrency.book.Company;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Gyges on 2017/10/22
 */
public class Main {

    public static void main(String[] args) {

        String s = "192.168.1.217:9093";
        String port = s.substring(s.indexOf(":")+1);
        String host = s.replace(s.substring(s.indexOf(":")),"");
        System.out.println(new Integer(port));
        System.out.println(host);
//        ConcurrentLinkedDeque<String> listQueue = new ConcurrentLinkedDeque<>();
//        Thread[] threads = new Thread[100];
//        for (int i = 0; i < threads.length; i++) {
//            AddTask.PollTask pollTask = new AddTask.PollTask(listQueue);
//            threads[i] = new Thread(pollTask);
//            threads[i].start();
//            System.out.println("Main:" + threads.length + "PollTask threads have been launched");
//        }
//
//        for (int i = 0; i < threads.length; i++) {
//            try {
//                threads[i].join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("Size of the list:" + listQueue.size());


//        Account account = new Account();
//        account.setBalance(1000);
//
//        Company company = new Company(account);
//        Thread companyThread = new Thread(company);
//
//        Bank bank = new Bank(account);
//        Thread bankThread = new Thread(bank);
//
//        System.out.printf("Account : Initial Balance: %d\n",account.getBalance());
//
//        companyThread.start();
//        bankThread.start();
//
//        try {
//            companyThread.join();
//            bankThread.join();
//            System.out.printf("Account : Final Balance: %d\n",account. getBalance());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
    }
}
