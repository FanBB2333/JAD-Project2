package com.company;

import javax.swing.*;
import java.awt.*;
public class MiniCAD extends JFrame {
    public static final Integer height = 600;

    public static void main(String[] agrs){
        Frame jf = new MiniCAD();
        jf.setTitle("MiniCAD");
        jf.setSize(800,600);
        jf.setLocationRelativeTo(null); // move to center

        Graphics g = jf.getGraphics();
        jf.setLayout(new BorderLayout());

        // Add sidebar menu
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(4,1));
        JButton line = new JButton("Line");
        JButton rect = new JButton("Rect");
        JButton circle = new JButton("Circle");
        JButton words = new JButton("Words");
        sidebar.add(line);
        sidebar.add(rect);
        sidebar.add(circle);
        sidebar.add(words);
        jf.add(sidebar, BorderLayout.EAST);






        jf.setVisible(true);

    }


    public JPanel addSidebar(){
        JPanel sidebar = new JPanel();
        this.setSize(100, 600);
//        Button line = new Button("Line");
//        Button rect = new Button("Rect");
//        Button circle = new Button("Circle");
//        Button words = new Button("Words");
//        sidebar.add(line);
//        sidebar.add(rect);
//        sidebar.add(circle);
//        sidebar.add(words);


        return sidebar;
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

class Line extends Shape{

}
