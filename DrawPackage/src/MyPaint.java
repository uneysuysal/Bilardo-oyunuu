import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class MyPaint extends JFrame implements ActionListener, KeyListener, MouseListener{

    BorderLayout bl;
    protected static Color c;
    protected static int x;
    protected static int y;
    protected static int type = 0;
    protected static boolean flag = false;
    protected static boolean move_flag = false;

    protected static ArrayList<Points> rectangles = new ArrayList<Points>();

    public MyPaint() {
        bl = new BorderLayout();
        setLayout(bl);

        //east();
        menu();
        center();
    }

    JMenuBar jmb;
    JMenu colorM,shape,move,exit;
    JMenuItem colorSbm,rectangleSbm,mrectangleSbm,moveSbm,exitSbm;

    public void menu(){
        jmb = new JMenuBar();
        colorM = new JMenu("Color");
        shape = new JMenu("Shape");
        move = new JMenu("Move");
        exit = new JMenu("Exit");

        colorSbm = new JMenuItem("Color Chooser");
        colorSbm.addActionListener(this);

        rectangleSbm = new JMenuItem("Rectangle");
        rectangleSbm.addActionListener(this);

        mrectangleSbm = new JMenuItem("Multiple Rectangle");
        mrectangleSbm.addActionListener(this);

        moveSbm = new JMenuItem("Move Object");
        moveSbm.addActionListener(this);

        exitSbm = new JMenuItem("Close App");
        exitSbm.addActionListener(this);

        addKeyListener(this);

        colorM.add(colorSbm);
        shape.add(rectangleSbm);
        shape.add(mrectangleSbm);
        move.add(moveSbm);
        exit.add(exitSbm);

        jmb.add(colorM);
        jmb.add(shape);
        jmb.add(move);
        jmb.add(exit);

        add(jmb);
        setJMenuBar(jmb);

    }

    /*JPanel jpEast;
    GridLayout gl;
    JButton jbColorChoose;
    JButton jbRectangle;
    JButton jbMRectangle;
    JButton jbMove;

    public void east() {
        jpEast = new JPanel();
        gl = new GridLayout(4, 1);
        jpEast.setLayout(gl);
        add(jpEast,BorderLayout.EAST);

        jbColorChoose = new JButton("Color Chooser");
        jbColorChoose.addActionListener(this);
        jpEast.add(jbColorChoose);

        jbRectangle = new JButton("Rectangle");
        jbRectangle.addActionListener(this);
        jpEast.add(jbRectangle);

        jbMRectangle = new JButton("Multiple Rectangle");
        jbMRectangle.addActionListener(this);
        jpEast.add(jbMRectangle);

        jbMove = new JButton("Move Object");
        jbMove.addActionListener(this);
        jbMove.addKeyListener(this);
        jbMove.setFocusable(true);
        jpEast.add(jbMove);
    }*/

    JPanel jpCenter;
    public void center() {
        jpCenter = new PaintPanel();
        jpCenter.addMouseListener(this);
        jpCenter.setBackground(Color.white);
        add(jpCenter,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == colorSbm) {
            move_flag =false;
            c = JColorChooser.showDialog(this, "Color Chooser", Color.red);
        }
        else if(e.getSource() == rectangleSbm) {
            move_flag =false;
            flag = false;
            int choice = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter\n1- Rectangle\n2- Fill Rectangle"));
            type = choice;
            JOptionPane.showMessageDialog(this, "Now click center panel for creating a rectangle");
        }
        else if(e.getSource() == mrectangleSbm) {
            move_flag =false;
            flag = true;
        }
        else if(e.getSource() == moveSbm) {
            move_flag =true;
            JOptionPane.showMessageDialog(this, "Only works on one rectangle");
        }else if(e.getSource() == exitSbm){
            move_flag =false;
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (move_flag == true) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                y--;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                x++;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                y++;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                x--;
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(flag == false) {
            x = e.getX();
            y = e.getY();
        }
        else {
            Points p = new Points();
            p.x = e.getX();
            p.y = e.getY();
            rectangles.add(p);
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }



}
