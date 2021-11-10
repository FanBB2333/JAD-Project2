package com.company;

class Century implements Runnable {
    public void run() {
        for ( int year = 1900; year < 2000; year++ ) {
            System.out.println(year);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Happy new millennium!");
    }
    public void a() throws InterruptedException {
        Thread t = new Thread() {
            public void run() {
                for (;;) System.out.println(
                        "I'm a thread inside a thread"
                );
            }
        };
        t.start();
        t.sleep(1000);
    }
}
public class CountUp {
    public static void main(String[] args) throws InterruptedException {
        Century ourCentury = new Century();
        //    put your line here
        ourCentury.a();
    }
}