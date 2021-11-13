package com.company;

import javax.sound.sampled.LineListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.exit;

public class MiniCAD extends JFrame {
    public static final Integer height = 600;
    public static int draw_type = 0; // 0: idle, 1: Line, 2: Rect, 3: Circle, 4: Words
    public static Pair<Integer> start_point = new Pair<>(0, 0);
    public static Pair<Integer> end_point = new Pair<>(0, 0);
    public static Pair<Integer> drag_start = new Pair<>(0, 0);
    public static Pair<Integer> drag_end = new Pair<>(0, 0);
    public static Pair<Integer> p1_saved = new Pair<>(0, 0);
    public static Pair<Integer> p2_saved = new Pair<>(0, 0);

    public static ArrayList<Shape> shapes = new ArrayList<>();
    public static ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.BLACK, Color.white, Color.red, Color.green, Color.blue, Color.yellow, Color.cyan, Color.magenta, Color.gray));

    public static Shape current = null;
    public static Color current_color = Color.black;

    public static Shape selected = null;

    public static String words = null;
    public static Shape resizing = null;

    public static void main(String[] agrs){
        JFrame jf = new MiniCAD();
        jf.setTitle("MiniCAD");
        jf.setSize(800,600);
        jf.setLocationRelativeTo(null); // move to center

        Graphics g = jf.getGraphics();
        jf.setLayout(new BorderLayout());
        // Add menubar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");


        menuBar.add(fileMenu);
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showDialog(new JLabel(), "Open");
                jfc.setVisible(true);
                File file = jfc.getSelectedFile();
                if(file.isFile()){
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        // clear the original board
                        for(Shape s : shapes){
                            s.draw(jf.getGraphics(), Color.white);
                        }
                        shapes = (ArrayList<Shape>) ois.readObject();
                        ois.close();
                        for(Shape s : shapes){
                            s.draw(jf.getGraphics(), s.getColor());
                        }

                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }


                }

            }
        });

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showSaveDialog(new JLabel());
                jfc.setVisible(true);
                File file = jfc.getSelectedFile();
                System.out.println(file.getAbsolutePath());
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
//                    for(Shape s : shapes){
//                        os.writeObject(s);
//                    }
                    os.writeObject(shapes);
                    os.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit");
                System.exit(0);
            }


        });

        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(exit);
        jf.setJMenuBar(menuBar);

        // Add sidebar menu
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(5,1));
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
                    // get painting type from button name
                    if (_tmp.getText().equals("Line")) {
                        draw_type = 1;
                    } else if (_tmp.getText().equals("Rect")) {
                        draw_type = 2;
                    } else if (_tmp.getText().equals("Circle")) {
                        draw_type = 3;
                    } else if (_tmp.getText().equals("Words")) {
                        draw_type = 4;
                        words = JOptionPane.showInputDialog(null, "Input words");
                    } else {
                        draw_type = 0;
                    }
                    System.out.println(_tmp.getText());
                }
            });

            sidebutton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    System.out.println(e.getKeyCode());
                    System.out.println(resizing);
                    if(resizing != null){
                        ArrayList<Integer> _resize_codes = new ArrayList<>(Arrays.asList(61, 45, 44, 46, 82));
                        if (_resize_codes.contains(e.getKeyCode())){
                            resizing.draw(jf.getGraphics(), Color.white);
                        }
                        if(e.getKeyCode() == 61){
                            resizing.resize(1.1);
                        }
                        else if(e.getKeyCode() == 45){
                            resizing.resize(0.9);
                        }
                        else if(e.getKeyCode() == 44){
                            resizing.setThickness(resizing.getThickness() - 1);
                        }
                        else if(e.getKeyCode() == 46){
                            resizing.setThickness(resizing.getThickness() + 1);
                        }
                        else if(e.getKeyCode() == 82){
                            shapes.remove(resizing);
                            resizing = null;

                        }
                        if (_resize_codes.contains(e.getKeyCode())){
                            if(resizing != null){
                                resizing.draw(jf.getGraphics(), resizing.getColor());
                            }
                        }

                    }



                }

            });
            sidebar.add(sidebutton);
        }

        JPanel palette = new JPanel();
        palette.setLayout(new GridLayout(3, 3));
        for(Color c : colors){
            JButton b = new JButton();
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton _tmp = (JButton) e.getSource();
                    current_color = _tmp.getBackground();
                    if(resizing != null){
                        resizing.setColor(current_color);
                        resizing.draw(jf.getGraphics(), Color.white);
                        resizing.draw(jf.getGraphics(), resizing.getColor());

                        resizing = null;
                    }
                }
            });

            b.setBackground(c);
            // on macos
            b.setForeground(c);
            b.setOpaque(true);
            b.setBorderPainted(false);
            palette.add(b);
            System.out.println(c.toString());

        }


        sidebar.add(palette);

        jf.add(sidebar, BorderLayout.EAST);

        // add mouse listener
        jf.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if(selected != null){
                    selected.draw(jf.getGraphics(), Color.white);
                    selected.setP1(new Pair<>(p1_saved.getX() + e.getX() - drag_start.getX(), p1_saved.getY() + e.getY() - drag_start.getY()));
                    selected.setP2(new Pair<>(p2_saved.getX() + e.getX() - drag_start.getX(), p2_saved.getY() + e.getY() - drag_start.getY()));

//                    selected.move(new Pair<>(e.getX() - drag_start.getX(), e.getY() - drag_start.getY()));
                }

                if(draw_type != 0 && selected == null) {
                    end_point = new Pair<>(e.getX(), e.getY());
                    shapes.get(shapes.size() - 1).draw(jf.getGraphics(), Color.WHITE); // Delete previous shape
                    shapes.get(shapes.size() - 1).setP2(end_point);
                    shapes.get(shapes.size() - 1).draw(jf.getGraphics(), shapes.get(shapes.size() - 1).getColor()); // Draw new shape
                }
                // paint all again
                for(Shape s : shapes){
                    s.draw(jf.getGraphics(), s.getColor());
                }

//                drag_start = new Pair<>(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        jf.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked");
//                String s = JOptionPane.showInputDialog(null, "Please enter your words", "Words", JOptionPane.PLAIN_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed");
                start_point = new Pair<>(e.getX(), e.getY());

                for(Shape s : shapes){
                    if(start_point != null && s.isInside(start_point) && selected == null){
                        selected = s;
                        resizing = s;
                        System.out.println("Selected: " + selected);
                        drag_start = new Pair<>(e.getX(), e.getY());
                        p1_saved = new Pair<>(selected.p1.getX(), selected.p1.getY());
                        p2_saved = new Pair<>(selected.p2.getX(), selected.p2.getY());

                        break;
                    }
                }

                // If do not selected any shape
                if(selected == null){
                    System.out.println("drawing");
                    if(draw_type == 1) {
                        shapes.add(new Line(start_point, end_point));
                    } else if(draw_type == 2) {
                        shapes.add(new Rect(start_point, end_point));
                    } else if(draw_type == 3) {
                        shapes.add(new Circle(start_point, end_point));
                    } else if(draw_type == 4) {
                        Words _t = new Words(start_point, end_point);
                        _t.setWord(words);
                        shapes.add(_t);

                    }
                    current = shapes.get(shapes.size() - 1);
                    current.setColor(current_color);
                }


            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse released");
                selected = null;
                drag_start = null;
                p1_saved = null;
                p2_saved = null;
                start_point = null;

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

    public int random(int min, int max) {
        return  (int) (Math.random()*(max-min)+min);
    }
}




class Pair<T> implements Serializable{
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

    public void setNew(T _x, T _y){
        x = _x;
        y = _y;
    }

    double distance(Pair<T> p){
        return Math.sqrt(Math.pow((int)p.getX() - (int)x, 2) + Math.pow((int)p.getY() - (int)y, 2));
    }
}


abstract class Shape implements Serializable{
    public Pair<Integer> p1 = new Pair<>(0, 0);
    public Pair<Integer> p2 = new Pair<>(0, 0);
    public Color color = Color.black;
    public double thickness = 1;

    public abstract void draw(Graphics g, Color c);

    public abstract boolean isInside(Pair<Integer> p);

    public abstract void move(Pair<Integer> p);

    public void resize(double ratio){
        Pair<Integer> center = new Pair<>((int)((p1.getX() + p2.getX()) / 2), (int)((p1.getY() + p2.getY()) / 2));
        Pair<Integer> new_p1 = new Pair<>((int)(center.getX() - (center.getX() - p1.getX()) * ratio), (int)(center.getY() - (center.getY() - p1.getY()) * ratio));
        Pair<Integer> new_p2 = new Pair<>((int)(center.getX() - (center.getX() - p2.getX()) * ratio), (int)(center.getY() - (center.getY() - p2.getY()) * ratio));
        p1 = new_p1;
        p2 = new_p2;
    }

    public void setP1(Pair<Integer> p1) {
        this.p1 = p1;
    }

    public void setP2(Pair<Integer> p2) {
        this.p2 = p2;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public Color getColor() {
        return color;
    }

    public double getThickness(){
        return thickness;
    }

    public Pair<Integer> getTopLeftPoint(){
        return new Pair<>(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));
    }

    public Pair<Integer> getBottomRightPoint(){
        return new Pair<>(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()));
    }

}

class Line extends Shape{

    public Line(Pair<Integer> start_point, Pair<Integer> end_point) {
        this.p1 = start_point;
        this.p2 = end_point;
    }

    @Override
    public void draw(Graphics g, Color c) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((float)thickness));
        g.setColor(c);
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @Override
    public boolean isInside(Pair<Integer> p) {
        Pair<Integer> P1_P = new Pair<>(p.getX() - p1.getX(), p.getY() - p1.getY());
        Pair<Integer> P1_P2 = new Pair<>(p2.getX() - p1.getX(), p2.getY() - p1.getY());
        double _dist = Math.abs(P1_P.getX() * P1_P2.getY() - P1_P.getY() * P1_P2.getX()) / p1.distance(p2);
        return (_dist < 5.0) && p.getX() < Math.max(p1.getX(), p2.getX()) && p.getX() > Math.min(p1.getX(), p2.getX());
    }

    @Override
    public void move(Pair<Integer> p) {
        p1.setNew(p1.getX() + p.getX(), p1.getY() + p.getY());
        p2.setNew(p2.getX() + p.getX(), p2.getY() + p.getY());
    }

}

class Rect extends Shape{

    public Rect(Pair<Integer> start_point, Pair<Integer> end_point) {
        this.p1 = start_point;
        this.p2 = end_point;
    }

    @Override
    public void draw(Graphics g, Color c) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((float)thickness));
        g.setColor(c);
        g.drawRect(getTopLeftPoint().getX(), getTopLeftPoint().getY(), Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
    }

    @Override
    public boolean isInside(Pair<Integer> p) {
        int _x = p.getX();
        int _y = p.getY();
        return _x > getTopLeftPoint().getX() && _x < getBottomRightPoint().getX() && _y > getTopLeftPoint().getY() && _y < getBottomRightPoint().getY();
    }

    @Override
    public void move(Pair<Integer> p) {
        p1.setNew(p1.getX() + p.getX(), p1.getY() + p.getY());
        p2.setNew(p2.getX() + p.getX(), p2.getY() + p.getY());
    }

}

class Circle extends Shape{

    public Circle(Pair<Integer> start_point, Pair<Integer> end_point) {
        this.p1 = start_point;
        this.p2 = end_point;
    }

    @Override
    public void draw(Graphics g, Color c) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((float)thickness));
        g.setColor(c);
        g.drawOval(getTopLeftPoint().getX(), getTopLeftPoint().getY(), Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY()));
    }

    @Override
    public boolean isInside(Pair<Integer> p) {
        Pair<Integer> center = new Pair<>(getTopLeftPoint().getX() + Math.abs(p2.getX() - p1.getX())/2, getTopLeftPoint().getY() + Math.abs(p2.getY() - p1.getY())/2);
        Pair<Integer> focus1, focus2;
        double a, b, c;

        if(getBottomRightPoint().getY() - getTopLeftPoint().getY() > getBottomRightPoint().getX() - getTopLeftPoint().getX()){
            a = (double) (getBottomRightPoint().getY() - getTopLeftPoint().getY())/2;
            b = (double) (getBottomRightPoint().getX() - getTopLeftPoint().getX())/2;
            c = Math.sqrt(a*a - b*b);
            focus1 = new Pair<>((int) center.getX(), (int) (center.getY() + c));
            focus2 = new Pair<>((int) center.getX(), (int) (center.getY() - c));


        }
        else{
            a = (double) (getBottomRightPoint().getX() - getTopLeftPoint().getX())/2;
            b = (double) (getBottomRightPoint().getY() - getTopLeftPoint().getY())/2;
            c = Math.sqrt(a*a - b*b);
            focus1 = new Pair<>((int) (center.getX() + c), (int) center.getY());
            focus2 = new Pair<>((int) (center.getX() - c), (int) center.getY());

        }

        return p.distance(focus1) + p.distance(focus2) < 2*a;
    }

    @Override
    public void move(Pair<Integer> p) {
        p1.setNew(p1.getX() + p.getX(), p1.getY() + p.getY());
        p2.setNew(p2.getX() + p.getX(), p2.getY() + p.getY());
    }
}

class Words extends Shape{
    public String _word = "Hello";
    public int _fontSize = 20;
    public int height;
    public int width;

    public Graphics _g = null;

    public Words(Pair<Integer> start_point, Pair<Integer> end_point) {
        this.p1 = start_point;
        this.p2 = end_point;
    }

    @Override
    public void draw(Graphics g, Color c) {
        Font font = new Font("Times New Roman", Font.BOLD, _fontSize);
        g.setFont(font);
        g.setColor(c);
        g.drawString(_word, p1.getX(), p1.getY());
        _g = g;
        FontMetrics fm = _g.getFontMetrics(font);
        height = fm.getHeight();
        width = fm.stringWidth(_word);
    }

    @Override
    public boolean isInside(Pair<Integer> p) {
        int _x = p.getX();
        int _y = p.getY();
        System.out.printf("%d, %d\n", width, height);
        System.out.printf("P1: %d, %d\n", p1.getX(), p1.getY());
        return _x > p1.getX()  && _x <p1.getX() + width && _y > p1.getY() - 0.2 * height && _y < p1.getY() + height;

    }

    @Override
    public void move(Pair<Integer> p) {
        p1.setNew(p1.getX() + p.getX(), p1.getY() + p.getY());
        p2.setNew(p2.getX() + p.getX(), p2.getY() + p.getY());
    }

    public void setWord (String _word){
        this._word = _word;
    }

    @Override
    public void resize(double ratio){
        _fontSize = (int)(_fontSize * ratio);
        if(_fontSize <= 0){
            _fontSize = 1; // minimum font size
        }
    }
}
