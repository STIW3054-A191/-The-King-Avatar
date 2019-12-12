/*package com.STIW3054.A191;

import java.util.concurrent.locks.ReentrantLock;

class task {

    private int count;
    private static ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        count++;
        lock.unlock();
    }

    public int getCount() {
        return count;
    }
}

public class extra5 {

    public static void main(String[] args) throws InterruptedException {
        task t = new task();

        Thread t1 = new Thread(new Runnable() {
            public void run() {

                for (int i = 1; i <= 1000; i++) {
                    t.increment();

                }
                System.out.println("- " + Thread.currentThread().getClass());
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= 1000; i++) {
                    t.increment();

                }
                System.out.println("- " + Thread.currentThread().getClass());
            }
        });
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= 1000; i++) {
                    t.increment();
                }
                System.out.println("- " + Thread.currentThread().getClass());
            }
        });
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(t.getCount());
    }
}*/
