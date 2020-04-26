package 多线程.交替打印FooBar.按序打印;

import java.util.concurrent.CountDownLatch;


public class Foo {

    private volatile  int mark=1;

    CountDownLatch secondCount;
    CountDownLatch thirdCount;

    public Foo() {
        secondCount=new CountDownLatch(1);
        thirdCount=new CountDownLatch(1);
    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        secondCount.countDown();//线程2计数器减一
    }

    public void second(Runnable printSecond) throws InterruptedException {

        secondCount.await();//如果线程2计数器不是0 则在此等待
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        thirdCount.countDown();


    }

    public void third(Runnable printThird) throws InterruptedException {
        thirdCount.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();

    }

    public static void main(String[] args) {
        Foo foo=new Foo();
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    foo.first(()->{
                        System.out.println("one");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    foo.second(()->{
                        System.out.println("two");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    foo.third(()->{
                        System.out.println("three");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
         t3.start();
         t2.start();
         t1.start();

    }
}
