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
        jf.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.printf("%d, %d\n", e.getX(), e.getX());
                positions.add(new Pair<>(e.getX(), e.getY()));
                colors.add(color);
                breaks.add(0);
                for(int i = 0; i < positions.size() - 1; i++){
                    if(breaks.get(i) == 1){
                        continue;
                    }
                    if(colors.get(i) == 1){
                        g.setColor(Color.BLACK);
                    }
                    else {
                        g.setColor(Color.white);
                    }
                    g.drawLine(positions.get(i).getKey(), positions.get(i).getValue(), positions.get(i+1).getKey(), positions.get(i+1).getValue());
                    System.out.println(positions.get(i));
                }
//                g.drawOval(e.getX(), e.getY(), diameter, diameter);
//                g.fillOval(e.getX(), e.getY(), diameter, diameter);
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
                breaks.remove(breaks.size() - 1);
                breaks.add(1);

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