package org.unistacks.queue;

import org.unistacks.vo.Book;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Gyges Zean
 * @date 2017/12/10
 */
public class BlockingQueueDemo  {

    private static BlockingQueue<Book> queue = new LinkedBlockingQueue<>(100);


    public static void addMessageIntoQueue(Book book) {
        if (!Objects.isNull(book)) {
            queue.offer(book);
        }
    }

    public static class QueueThread extends Thread {
        @Override
        public void run() {
            Book book = new Book();
            book.setAuthor("mazean");
            book.setId(1);
            book.setTitle("life is beautiful!");
            addMessageIntoQueue(book);
        }
    }


    public static class TakeQueueValue extends Thread {
        @Override
        public void run() {
            try {
                if (queue.size() > 0) {
                    Book book = queue.poll(5, TimeUnit.SECONDS);
                    System.out.println(book.toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100;i ++) {
            new BlockingQueueDemo.QueueThread().start();
        }

        for (int i = 0; i < queue.size(); i ++) {
            new BlockingQueueDemo.TakeQueueValue().start();
        }
    }



}
