package org.unistacks.example;

/**
 * @author Gyges Zean
 * @date 16/01/2018
 */
public class A {

    static {
        System.out.println("父类静态方法");
    }

    A() {
        System.out.println("父类构造方法");
    }

    public void getString() {
        System.out.println("父类方法");
    }
}
