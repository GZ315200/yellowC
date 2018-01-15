package org.unistacks.example;

/**
 * @author Gyges Zean
 * @date 16/01/2018
 */
public class B extends A {

    static {
        System.out.println("子类静态方法");
    }

    private B() {
        System.out.println("子类构造方法");
    }


    @Override
    public void getString() {
        super.getString();
        System.out.println("子类方法");
    }

    public static void main(String[] args) {
        A a = new A();
        a.getString();
        B b = new B();
        b.getString();
    }
}

