package org.unistacks.listener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Gyges on 2017/10/11
 *
 * 建议监听器开发顺序：
 先编写事件源，事件源中有监听器集合Vector listeners和增加监听器方法addListener
 在触发事件源的方法上如setValue，产生事件、并调用监听器方法，将事件以参数传入监听器方法
 创建事件类和监听器类
 测试，创建事件源——添加监听器——触发事件
 *
 *
 *
 */
public class Main {

    public static void main(String[] args) {
        MySource mySource = new MySource();
        mySource.addListener(e -> System.out.println("value has changed:" + e.getValue()));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mySource.setValue(Math.abs(new Random().nextInt()));
            }
        },1000,10*1000);
    }
}
