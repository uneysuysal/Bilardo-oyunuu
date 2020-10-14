////
////import java.awt.BorderLayout;
////import java.awt.Color;
////import java.awt.GridLayout;
////import java.awt.event.ActionEvent;
////import java.awt.event.ActionListener;
////import java.awt.event.KeyEvent;
////import java.awt.event.KeyListener;
////import java.awt.event.MouseEvent;
////import java.awt.event.MouseListener;
////import java.util.ArrayList;
////
////import javax.swing.JButton;
////import javax.swing.JColorChooser;
////import javax.swing.JFrame;
////import javax.swing.JOptionPane;
////import javax.swing.JPanel;
////import javax.swing.JLabel;
////
////public class MyPaint extends JFrame implements ActionListener, KeyListener, MouseListener{
////
////    BorderLayout bl;
////    protected static Color c;
////    public static int counter = 0 ;
////    protected static int x;
////    protected static int y;
////    protected static int x1;
////    protected static int y1;
////    protected static int x2;
////    protected static int y2;
////    //protected static int WIDTH ;
////    //protected static int HEIGHT;
////    public static int WIDTH;
////    public static int HEIGHT;
////    //protected static int type = 0;
////    //protected static int flagot = 0;
////
////
////
////    public MyPaint() {
////        bl = new BorderLayout();
////        setLayout(bl);
////
////        east();
////        center();
////    }
////
////    JPanel jpEast;
////    GridLayout gl;
////    JButton jbColorChoose;
////    JButton jbRectangle;
////    //JButton jbMRectangle;
////    //JButton jbMove;
////    JButton jbOval;
////    JButton jbLine;
////        JButton jbClear;
////    JLabel  jlcounter;
////
////    public void east() {
////        jpEast = new JPanel();
////        gl = new GridLayout(6, 1);
////        jpEast.setLayout(gl);
////        add(jpEast,BorderLayout.EAST);
////
////        jbColorChoose = new JButton("Color Chooser");
////        jbColorChoose.addActionListener(this);
////        jpEast.add(jbColorChoose);
////
////        jbRectangle = new JButton("Rectangle");
////        jbRectangle.addActionListener(this);
////        jpEast.add(jbRectangle);
////
////		*/
/////*jbMRectangle = new JButton("Multiple Rectangle");
////		jbMRectangle.addActionListener(this);
////		jpEast.add(jbMRectangle);*//*
////
////
////		*/
////jbMove = new JButton("Move Object");
////		jbMove.addActionListener(this);
////		jbMove.addKeyListener(this);
////		jbMove.setFocusable(true);
////		jpEast.add(jbMove);*//*
////
////
////        jbOval = new JButton("Oval");
////        jbOval.addActionListener(this);
////        jpEast.add(jbOval);
////
////        jbLine = new JButton("Line");
////        jbLine.addActionListener(this);
////        jpEast.add(jbLine);
////
////        jbClear = new JButton("Clear");
////        jbClear.addActionListener(this);
////        jpEast.add(jbClear);
////
////        jlcounter = new JLabel("Counter:");
////        jpEast.add(jlcounter);
////    }
////
////    JPanel jpCenter;
////    public void center() {
////        jpCenter = new PaintPanel();
////        jpCenter.addMouseListener(this);
////        jpCenter.setBackground(Color.white);
////        add(jpCenter,BorderLayout.CENTER);
////    }
////
////
////    }
////
////    @Override
////    public void keyTyped(KeyEvent e) {
////        // TODO Auto-generated method stub
////
////    }
///
////
////    @Override
////    public void keyReleased(KeyEvent e) {
////        // TODO Auto-generated method stub
////
////    }
////
////    @Override
////    public void mouseClicked(MouseEvent e) {
////        if(flag == false) {
////            Points p = new Points();
////            p.x = e.getX();
////            p.y = e.getY();
////            rectangles.add(p);
////            counter++;
////            jlcounter.setText("counter:" +counter);
////        }
////        if(flago == false) {
////            Points z = new Points();
////            z.x = e.getX();
////            z.y = e.getY();
////            ovals.add(z);
////            counter++;
////            jlcounter.setText("counter:" +counter);
////        }
////
////
////        repaint();
////    }
////
////    @Override
////    public void mousePressed(MouseEvent e) {
////        if(flagl == false) {
////            Points c = new Points();
////            x1 = c.x = e.getX();
////            y1 = c.y = e.getY();
////            lines.add(c);
////        } repaint();
////        // TODO Auto-generated method stub
////
////    }
////
////    @Override
////    public void mouseReleased(MouseEvent e) {
////        if(flagl == false) {
////            Points b = new Points();
////            x2 = b.x = e.getX();
////            y2 = b.y = e.getY();
////            lines2.add(b);
////            counter++;
////            jlcounter.setText("counter:" +counter);
////
////        }repaint();
////        // TODO Auto-generated method stub
////
////    }
////
////    @Override
////    public void mouseEntered(MouseEvent e) {
////        // TODO Auto-generated method stub
////
////    }
////
////    @Override
////    public void mouseExited(MouseEvent e) {
////        // TODO Auto-generated method stub
////
////    }
////
////
////
////}
////
////
////
////
////
////import java.awt.Graphics;
////
////import javax.swing.JPanel;
////
////public class PaintPanel extends JPanel {
////
////	@Override
////	public void paint(Graphics g) {
////		super.paint(g);
////		g.setColor(MyPaint.c);
////
////		if(MyPaint.flago == false) {
////			for (int i = 0; i < MyPaint.ovals.size(); i++) {
////				g.drawOval(MyPaint.ovals.get(i).x, MyPaint.ovals.get(i).y, 100, 50);
////			}
////			for (int i = 0; i < MyPaint.rectangles.size(); i++) {
////				g.drawRect(MyPaint.rectangles.get(i).x, MyPaint.rectangles.get(i).y, 150, 200);
////			}
////			for (int i = 0; i < MyPaint.lines.size(); i++) {
////				g.drawLine(MyPaint.lines.get(i).x, MyPaint.lines.get(i).y, MyPaint.lines2.get(i).x, MyPaint.lines2.get(i).y);
////			}}
////
////		else if (MyPaint.flagl == false) {
////			for (int i = 0; i < MyPaint.lines.size(); i++) {
////				g.drawLine(MyPaint.lines.get(i).x, MyPaint.lines.get(i).y, MyPaint.lines2.get(i).x, MyPaint.lines2.get(i).y);
////			}
////			for (int i = 0; i < MyPaint.rectangles.size(); i++) {
////				g.drawRect(MyPaint.rectangles.get(i).x, MyPaint.rectangles.get(i).y, 150, 200);
////			}
////			for (int i = 0; i < MyPaint.ovals.size(); i++) {
////				g.drawOval(MyPaint.ovals.get(i).x, MyPaint.ovals.get(i).y, 100, 50);
////			}
////			}
////
////
////		else if (MyPaint.flag == false) {
////			for (int i = 0; i < MyPaint.rectangles.size(); i++) {
////				g.drawRect(MyPaint.rectangles.get(i).x, MyPaint.rectangles.get(i).y, 150, 200);
////			}
////			for (int i = 0; i < MyPaint.lines.size(); i++) {
////				g.drawLine(MyPaint.lines.get(i).x, MyPaint.lines.get(i).y, MyPaint.lines2.get(i).x, MyPaint.lines2.get(i).y);
////			}
////			for (int i = 0; i < MyPaint.ovals.size(); i++) {
////				g.drawOval(MyPaint.ovals.get(i).x, MyPaint.ovals.get(i).y, 100, 50);
////			}
////			}
////		else {
////			for (int i = 0; i < MyPaint.rectangles.size(); i++) {
////				g.drawRect(MyPaint.rectangles.get(i).x, MyPaint.rectangles.get(i).y, 150, 200);
////			}
////		}
////	}
////            }
////
