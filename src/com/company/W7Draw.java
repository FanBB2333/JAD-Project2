package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class W7Draw extends JFrame
{
    public static int color = 1; // 1 means black while 0 means white
    public static int initialized = 0; // if it is the first point
    public static int x_prev = -1, y_prev = -1; // previous location of x and y
    public static int break_line = 0; // whether the button is uplifted


    public static void main(String[] agrs)
    {

        Frame jf = new W7Draw();
        jf.setTitle("W7");
        jf.setSize(800,600);
        jf.setVisible(true);

        Graphics g = jf.getGraphics();
        jf.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // set the pen color according to the static variable
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
                g2.setStroke(new BasicStroke(5.0f / _distance)); // the faster it moves, the thinner stroke will be
                // if we need to paint the first point
                // or we need to uplift the button and press again, the line should not be continuous
                if(initialized == 0 || break_line == 1){
                    x_prev = x;
                    y_prev = y;
                    initialized = 1;
                    break_line = 0;
                    return;
                }
                // draw a line from the previous to the current location
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
                if(e.getButton() == MouseEvent.BUTTON1){
                    color = 1; // black
                }
                if(e.getButton() == MouseEvent.BUTTON3){
                    color = 0; // white
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                break_line = 1; // the button is uplifted
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });
    }


    public W7Draw(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);

    }


}