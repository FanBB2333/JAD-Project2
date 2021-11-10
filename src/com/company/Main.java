package com.company;

import java.util.ArrayList;
import java.util.Scanner;

/*这里放置你的答案，即PrintTask类的代码*/

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Repo repo = new Repo(sc.nextLine());
        Thread t1 = new Thread(new Worker1(repo));
        Thread t2 = new Thread(new Worker2(repo));
        t1.start();
        Thread.yield();
        t2.start();
        sc.close();
    }
}


class Repo{
    public ArrayList<Integer> tasks = new ArrayList<>();
    public Repo(String items){
        String[] _tasks = items.split(" ");
        for (String task : _tasks) {
            tasks.add(Integer.parseInt(task));
        }
    }
//    public int getSize(int id){
//        synchronized (this){
//            try{
//                int task = tasks.remove(0);
//                System.out.printf("Thread-%d finish %d\n", id, task);
//
//            }
//
//
//            return tasks.size();
//        }
//    }

    public synchronized int p1(){
        int task = tasks.remove(0);
        System.out.printf("Thread-%d finish %d\n", 0, task);
        this.notify();
        try {
            this.wait();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return tasks.size();
    }

    public synchronized int p2(){
        int task = tasks.remove(0);
        System.out.printf("Thread-%d finish %d\n", 1, task);
        this.notify();
        try {
            this.wait();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        if(task == 0){

        }
        return tasks.size();
    }


}
class Worker1 implements Runnable{

    public Repo r = null;

    @Override
    public void run() {
        while (r.p1() > 0){
            System.out.println(" 1: " + r.tasks.size());
        }

    }

    public Worker1(Repo repo){
        r = repo;
    }

}

class Worker2 implements Runnable{
    public Repo r = null;

    @Override
    public void run() {
        while (r.p2() > 0){
            System.out.println(" 2: " + r.tasks.size());

        }

    }

    public Worker2(Repo repo){
        r = repo;
    }
}