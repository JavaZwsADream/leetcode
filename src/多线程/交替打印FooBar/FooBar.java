package 多线程.交替打印FooBar;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 输入: n = 2
 * 输出: "foobarfoobar"
 * 解释: "foobar" 将被输出两次。
 */
class FooBar {
    private int n;

    private volatile boolean flag = false;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            lock.lock();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            if(flag==true)
            {
                condition.await();
            }

            printFoo.run();
            flag = true;
            condition.signalAll();
            lock.unlock();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            lock.lock();
            if(flag==false)
            {
                condition.await();
            }
            printBar.run();
            flag = false;
            condition.signalAll();
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        FooBar fooBar=new FooBar(2);
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fooBar.foo(()->{
                        System.out.println("foo");
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
                    fooBar.bar(()->{
                        System.out.println("bar");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }
}
