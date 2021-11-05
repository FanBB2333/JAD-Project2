package com.company;

import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class W7Draw extends JFrame
{   
    public static void main(String[] agrs)
    {

        W7Draw jf = new W7Draw();
        jf.setSize(800,600);
        jf.setTitle("W7");
        jf.setVisible(true);
        jf.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.printf("%d, %d\n", e.getX(), e.getX());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

                if(e.getButton() == e.BUTTON1){
                }
            }
        });

        jf.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("mousePressed");
//                if(e.getButton()==e.BUTTON1){
//                    System.out.printf("%d, %d\n", e.getX(), e.getX());
//                }
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
    
    W7Draw(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}