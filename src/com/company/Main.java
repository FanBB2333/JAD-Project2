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
    private ArrayList<Integer> tasks = new ArrayList<>();
    private int cnt = 0;
    public Repo(String items){
        String[] _tasks = items.split(" ");
        for (String task : _tasks) {
            tasks.add(Integer.parseInt(task));
        }
        cnt = _tasks.length;
    }
    public int getSize(){
        return cnt;
    }


}