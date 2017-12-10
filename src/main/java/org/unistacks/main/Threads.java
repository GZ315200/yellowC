package org.unistacks.main;

/**
 * Created by Gyges on 2017/10/24
 */
public class Threads extends Thread{

    private int cupNum;
    private int personNum;


    Threads(int cupNum,int personNum) {
        this.cupNum = cupNum;
        this.personNum = personNum;
    }


    @Override
    public void run() {
        for (int i =0; i < personNum; i ++) {

            if (this.cupNum > 0) {
                System.out.println(".....state:"+sellCup(i) + " num:" + this.cupNum--);
            }

        }
    }

    private boolean sellCup(int num) {
        if (num > 0){
           return true;
        }
        return false;
    }


    public static void main(String[] args) {
        Threads threads = new Threads(10,20);
        Thread thread = new Thread(threads);
        thread.start();


        Thread thread1 = new Thread(threads);
        thread1.start();

        Thread thread2 = new Thread(threads);
        thread2.start();

    }
}
