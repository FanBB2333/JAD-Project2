package com.company;

import javax.swing.JFrame;


public class W7Draw extends JFrame
{   
    public static void main(String[] agrs)
    {
//        JFrame jf=new JFrame("W7");

//        jf.setVisible(true);
        W7Draw jf = new W7Draw();
        jf.setSize(600,800);
        jf.setVisible(true);
    }
    W7Draw(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}