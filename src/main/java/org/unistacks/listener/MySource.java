package org.unistacks.listener;

import java.util.Vector;

/**
 * Created by Gyges on 2017/10/11
 */
public class MySource {

    private int value;
    private Vector<Listener> listeners = new Vector<>();

    /**
     * add a listener
     *
     * @param listener
     */
    public synchronized void addListener(Listener listener) {
        listeners.add(listener);
    }

    public  void setValue(int value) {
        this.value = value;
        //sent message
        MyEvent event = new MyEvent();
        event.setValue(value);
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).valueChanged(event);
        }
    }

}
