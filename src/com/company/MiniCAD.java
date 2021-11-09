package com.company;

import javax.sound.sampled.LineListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MiniCAD extends JFrame {
    public static final Integer height = 600;
    public static int draw_type = 0; // 0: idle, 1: Line, 2: Rect, 3: Circle, 4: Words
    public static Pair<Integer> start_point = new Pair<>(0, 0);
    public static Pair<Integer> end_point = new Pair<>(0, 0);
    public static ArrayList<Shape> shapes = new ArrayList<>();

    public static Shape current = new Shape() {
        @Override
        public void draw(Graphics g, Color color) {

        }
    };

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
        ArrayList<JButton> sidebuttons = new ArrayList<>();
        sidebuttons.add(new JButton("Line"));
        sidebuttons.add(new JButton("Rect"));
        sidebuttons.add(new JButton("Circle"));
        sidebuttons.add(new JButton("Words"));
        for (JButton sidebutton : sidebuttons) {
            sidebutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton _tmp = (JButton) e.getSource();
                    if (_tmp.getText().equals("Line")) {
                        draw_type = 1;
                    } else if (_tmp.getText().equals("Rect")) {
                        draw_type = 2;
                    } else if (_tmp.getText().equals("Circle")) {
                        draw_type = 3;
                    } else if (_tmp.getText().equals("Words")) {
                        draw_type = 4;
                    } else {
                        draw_type = 0;
                    }
                    System.out.println(_tmp.getText());
                }
            });
            sidebar.add(sidebutton);
        }

        jf.add(sidebar, BorderLayout.EAST);

        // add mouse listener
        jf.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.printf("type: %d\n", draw_type);
                if(draw_type != 0) {
                    end_point = new Pair<>(e.getX(), e.getY());
                    System.out.println(shapes);
                    shapes.get(shapes.size() - 1).draw(jf.getGraphics(), Color.WHITE); // Delete previous shape
                    shapes.get(shapes.size() - 1).setP2(end_point);
                    // paint all again
                    for(Shape s : shapes){
                        s.draw(jf.getGraphics(), Color.BLACK);
                    }
                }


            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        jf.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed");
                start_point = new Pair<>(e.getX(), e.getY());
                current.setP1(start_point);
                end_point = start_point; // Initialize end point
                if(draw_type == 1) {
                    shapes.add(new Line(start_point, end_point));
                } else if(draw_type == 2) {
                    shapes.add(new Rect(start_point, end_point));
                } else if(draw_type == 3) {
                    shapes.add(new Circle(start_point, end_point));
                } else if(draw_type == 4) {
                    shapes.add(new Words(start_point, end_point));
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse released");

                current.setP1(new Pair<>(0,0));
                current.setP2(new Pair<>(0,0));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });






        jf.setVisible(true);

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

    public void setX(T _x){
        x = _x;
    }

    public void setY(T _y){
        y = _y;
    }

    public void setNew(T _x, T _y){
        x = _x;
        y = _y;
    }
}


abstract class Shape {
    public Pair<Integer> p1 = new Pair<>(0, 0);
    public Pair<Integer> p2 = new Pair<>(0, 0);
    public Color color = Color.black;
    public abstract void draw(Graphics g, Color c);

    public void setP1(Pair<Integer> p1) {
        this.p1 = p1;
    }

    public void setP2(Pair<Integer> p2) {
        this.p2 = p2;
    }

    public void setP12(Pair<Integer> p1, Pair<Integer> p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}

class Line extends Shape{

    public Line(Pair<Integer> start_point, Pair<Integer> end_point) {
        this.p1 = start_point;
        this.p2 = end_point;
    }

    @Override
    public void draw(Graphics g, Color c) {
        g.setColor(c);
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
}

class Rect extends Shape{

    public Rect(Pair<Integer> start_point, Pair<Integer> end_point) {
        this.p1 = start_point;
        this.p2 = end_point;
    }

    @Override
    public void draw(Graphics g, Color c) {
        g.setColor(c);
        g.drawRect(p1.getX(), p1.getY(), p2.getX() - p1.getX(), p2.getY() - p1.getY());
    }
}

class Circle extends Shape{

    public Circle(Pair<Integer> start_point, Pair<Integer> end_point) {
        this.p1 = start_point;
        this.p2 = end_point;
    }

    @Override
    public void draw(Graphics g, Color c) {
        g.setColor(c);
        g.drawOval(p1.getX(), p1.getY(), p2.getX() - p1.getX(), p2.getY() - p1.getY());
    }
}

class Words extends Shape{
    public String _word = "Hello";

    public Words(Pair<Integer> start_point, Pair<Integer> end_point) {
        this.p1 = start_point;
        this.p2 = end_point;
    }

    @Override
    public void draw(Graphics g, Color c) {
        g.setColor(c);
        g.drawString(_word, p1.getX(), p1.getY());
    }

    public void setWord (String _word){
        this._word = _word;
    }
}
