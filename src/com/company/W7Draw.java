package com.company;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class W7Draw extends JFrame
{
    public static int color = 1;
    public static int diameter = 10;
    public static ArrayList<Pair<Integer, Integer>> positions = new ArrayList<Pair<Integer, Integer>>();

    public static void main(String[] agrs)
    {

        W7Draw jf = new W7Draw();
        jf.setSize(800,600);
        jf.setTitle("W7");
        jf.setVisible(true);
        jf.setBackground(Color.white);

        Graphics g = jf.getGraphics();
        jf.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.printf("%d, %d\n", e.getX(), e.getX());
                positions.add(new Pair<>(e.getX(), e.getY()));
                g.drawOval(e.getX(), e.getY(), diameter, diameter);
                g.fillOval(e.getX(), e.getY(), diameter, diameter);
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
    class newPanel extends JPanel{
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawLine(0,0,50,50);
            g.drawString("Banner", 0, 40);
        }
    }

    public W7Draw(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


}