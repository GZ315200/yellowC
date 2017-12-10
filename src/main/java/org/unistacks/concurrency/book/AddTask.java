package org.unistacks.concurrency.book;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Gyges on 2017/10/22
 * @author mazean
 */
public class AddTask implements Runnable {

    private ConcurrentLinkedDeque<String> linkedDeque;

    public AddTask(ConcurrentLinkedDeque<String> linkedDeque) {
        this.linkedDeque = linkedDeque;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();

        for (int i = 0; i < 10000; i++) {
            linkedDeque.add(name + i);
        }
    }


    public static class PollTask implements Runnable {

        private ConcurrentLinkedDeque<String> list;

        public PollTask(ConcurrentLinkedDeque<String> linkedDeque) {
            this.list = linkedDeque;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5000; i++) {
                list.pollFirst();//delete a first element
                list.pollLast();//delete a last element
            }
        }
    }


    public static void main(String[] args) {
        ConcurrentLinkedDeque<String> listQueue = new ConcurrentLinkedDeque<>();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            AddTask addTask = new AddTask(listQueue);
            threads[i] = new Thread(addTask);
            threads[i].start();
//            System.out.println("Main:" + i + "AddTask threads have been launched");
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("================================================");
        System.out.println("addTask : Size of the list:" + listQueue.size());
        System.out.println("================================================");

        for (int i = 0; i < threads.length; i++) {
            PollTask pollTask = new PollTask(listQueue);
            threads[i] = new Thread(pollTask);
            threads[i].start();
//            System.out.println("Main:" + i + "PollTask threads have been launched");
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("**************************************");
        System.out.println("pollTask : Size of the list:" + listQueue.size());
        System.out.println("**************************************");

    }


}
