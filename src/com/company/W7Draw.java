package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class W7Draw extends JFrame
{
    public static int color = 1;
    public static int initialized = 0;
    public static int x_prev = -1, y_prev = -1;
    public static int break_line = 0;
    public static ArrayList<Integer> colors = new ArrayList<Integer>();
    public static ArrayList<Integer> breaks = new ArrayList<Integer>();

    public static void main(String[] agrs)
    {

        W7Draw jf = new W7Draw();
        jf.setSize(800,600);
        jf.setTitle("W7");
        jf.setVisible(true);
        jf.setBackground(Color.white);

        Graphics g = jf.getGraphics();
        int i = 1;
        jf.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.printf("%d, %d\n", e.getX(), e.getX());
                if(break_line == 1){
                    return;
                }
                if(color == 1){
                    g.setColor(Color.BLACK);
                }
                else {
                    g.setColor(Color.white);
                }
                Graphics2D g2 = (Graphics2D)g;
                int x = e.getX();
                int y = e.getY();
                float _distance = (float) Math.sqrt(Math.pow(x-x_prev, 2) + Math.pow(y-y_prev, 2));
//                g2.setStroke(new BasicStroke(3.0f / _distance));
                if(initialized == 0){
                    x_prev = x;
                    y_prev = y;
                    initialized = 1;
                    return;
                }
                g.drawLine(x_prev, y_prev, x, y);
                x_prev = x;
                y_prev = y;

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        jf.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("mousePressed");
                if(e.getButton() == MouseEvent.BUTTON1){
                    color = 1; // black
                    g.setColor(Color.BLACK);
                }
                if(e.getButton() == MouseEvent.BUTTON3){
                    color = 0; // white
                    g.setColor(Color.white);

                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouseClicked");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouseReleased");


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("mouseEntered");

            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("mouseExited");

            }


        });
    }


    public W7Draw(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


}