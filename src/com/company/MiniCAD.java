package com.company;

import javax.swing.*;
import java.awt.*;

public class MiniCAD extends JFrame {
    public static void main(String[] agrs){
        Frame jf = new W7Draw();
        jf.setTitle("W7");
        jf.setSize(800,600);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null); // move to center

        Graphics g = jf.getGraphics();

    }

    public MiniCAD(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
    }
}




class Pair<T>{
    private T x;
    private T y;
    Pair(T _x, T _y){
        x = _x;
        y = _y;
    }

    public T getX(){
        return x;
    }

    public T getY() {
        return y;
    }
}


class Shape {
    public Pair<Integer> p1 = new Pair<Integer>(0, 0);
    public Pair<Integer> p2 = new Pair<Integer>(0, 0);



}
