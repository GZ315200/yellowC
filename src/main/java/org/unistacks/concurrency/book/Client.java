package org.unistacks.concurrency.book;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by Gyges on 2017/10/22
 */
public class Client implements Runnable {

    private LinkedBlockingDeque<String> requestList;

    public Client(LinkedBlockingDeque<String> requestList) {

       this.requestList = requestList;

    }

    @Override
    public void run() {
        for (int i = 0; i < requestList.size(); i++) {
            for (int j = 0; j < requestList.size(); j++) {
                StringBuilder request = new StringBuilder();
                request.append(i);
                request.append(":");
                request.append(j);
                try {
                    requestList.put(request.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Client: %s at %s.\n", request, new Date());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Client: End.\n");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingDeque<String> list = new LinkedBlockingDeque<>(3);
        Client client = new Client(list);
        Thread thread = new Thread(client);
        thread.start();

        for (int i = 0; i < list.size(); i ++) {
            String request=list.take();
            System.out.printf("Main: Request: %s at %s. Size:%d\n",request,new Date(),list.size());
            TimeUnit.MILLISECONDS.sleep(300);
        }

            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
            executorService.scheduleAtFixedRate(() -> {
                int random = ThreadLocalRandom.current().nextInt();
                try {
                    list.put(""+Math.abs(random));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },2,2,TimeUnit.SECONDS);





    }

}
