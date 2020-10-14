import functions.panel.GeneralFunctions;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyServer extends JFrame implements ActionListener,MouseListener, KeyListener {

    BorderLayout bl;
    BorderLayout toMenuBar;
    GridLayout gl;
    JTextArea jta;
    JTextField jtf;
    JButton jbSend;
    JPanel jpSouth;
    JPanel jpCenter;
    JSplitPane splitPane;
    JButton jbSquare;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServerSocket server;
    private Socket conn;
    protected static int x;
    protected static int y;
    protected boolean flagToColorChooser;
    protected static int value;
    protected static int value2;
    protected static boolean flag = false;
    JPanel gridBagLayout;
    JButton jbCircle;
    JButton jbLine;
    JButton colorChooser;
    JColorChooser pickColor;
    GeneralFunctions panelFunctions;
    JScrollPane scrollPane;
    JPanel inputPanel;
    JLabel jlcounter;
    JButton jbRectangle;
    public static int counter = 0 ;
    protected static boolean flagToShapes = false;
    protected static boolean flago = false;
    protected static boolean flagl = false;
    protected static boolean flagc = false;
    protected static boolean flagAttendace = false;
    protected static boolean flagToStartTime = false;
    protected static int x1;
    protected static int y1;
    protected static int x2;
    protected static int y2;

    protected static ArrayList<Points> rectangles = new ArrayList<Points>();
    protected static ArrayList<Points> ovals = new ArrayList<Points>();
    protected static ArrayList<Points> lines = new ArrayList<Points>();
    protected static ArrayList<Points> lines2 = new ArrayList<Points>();
    JButton jbClear;
    JButton jbAttendance;
    FileWriter fileWriter;
    JLabel label;
    int count;
    JButton jbStartTime;
    private Timer timer;
    private long startTime = -1;
    private long duration = 5000;




    public MyServer() {
        super("My Server Application");

        initGui();
    }

    public void initGui() {
        panelFunctions = new GeneralFunctions();
        splitPane = new JSplitPane();
        gridBagLayout = new JPanel(new GridBagLayout());
        gridBagLayout.setLayout(new GridBagLayout());
        gridBagLayout.setBackground(Color.WHITE);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets= new Insets(3,3,3,3);

        //Square Button
        jbRectangle = new JButton("Rectangle");
        jbRectangle.addActionListener(this);
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagLayout.add(jbRectangle,gridBagConstraints);

        //Circle Button
        jbCircle = new JButton("Circle");
        jbCircle.addActionListener(this);
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagLayout.add(jbCircle,gridBagConstraints);

        //Line Button
        jbLine = new JButton("Line");
        jbLine.addActionListener(this);
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagLayout.add(jbLine,gridBagConstraints);

        jbClear = new JButton("Clear");
        jbClear.addActionListener(this);
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagLayout.add(jbClear,gridBagConstraints);

        jlcounter = new JLabel("Counter:");
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagLayout.add(jlcounter,gridBagConstraints);

        label = new JLabel("...");
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagLayout.add(label,gridBagConstraints);

        jbAttendance = new JButton("Attendance");
        jbAttendance.addActionListener(this);
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagLayout.add(jbAttendance,gridBagConstraints);

        jbStartTime = new JButton("Start Time");
        jbStartTime.addActionListener(this);
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagLayout.add(jbStartTime,gridBagConstraints);

        //South Panel
        jpSouth = new JPanel();

        //Top Panel
        jpCenter = new PaintPanel();
        jpCenter.setBackground(Color.WHITE);
        jpCenter.addMouseListener(this);
        jpCenter.add(gridBagLayout);
        add(jpCenter);

        colorChooser = new JButton("Color Chooser");
        colorChooser.addMouseListener(this);
        colorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelFunctions.openColorBox(jpCenter,pickColor,splitPane,flagToColorChooser);
                flagToColorChooser = !flagToColorChooser;
            }
        });
        jpCenter.add(colorChooser);

        pickColor = new JColorChooser();
        pickColor.setBorder(null);
        pickColor.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                panelFunctions.newValueOfColor(colorChooser,pickColor);
            }
        });

        scrollPane = new JScrollPane();
        jta = new JTextArea();
        jta.setEditable(false);

        inputPanel = new JPanel();
        jtf = new JTextField();
        inputPanel.add(jtf);


        jbSend = new JButton("Send");
        jbSend.setEnabled(true);
        inputPanel.add(jbSend);
        jbSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send(jtf.getText());
                jtf.setText("");
            }
        });


        getContentPane().setLayout(new GridLayout());
        getContentPane().add(splitPane);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(500);
        splitPane.setTopComponent(jpCenter);
        splitPane.setBottomComponent(jpSouth);

        jpSouth.setLayout(new BoxLayout(jpSouth,BoxLayout.Y_AXIS));

        jpSouth.add(scrollPane);
        scrollPane.setViewportView(jta);
        jpSouth.add(inputPanel);


        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.X_AXIS));

        pack();


    }

    public void runServer() {
        try {
            server = new ServerSocket(12345, 100);

            while(true) {
                try {
                    waitConn();
                    streams();
                    processConn();
                    drawAgain();
                }
                catch (EOFException e) {
                    dispMessage("\nServer Terminated Conn\n");
                }
                finally {
                    closeConn();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitConn() throws IOException{
        dispMessage("Please Wait...\n");
        conn = server.accept();
        dispMessage("Connection Received\n");
    }

    private void streams() throws IOException{
        oos = new ObjectOutputStream(conn.getOutputStream());
        oos.flush();

        ois = new ObjectInputStream(conn.getInputStream());
        dispMessage("\nStreams\n");
    }

    private void processConn() throws IOException{
        send("Successful");
        setButtonEnabled(true);
        Object msg = null;
        do {
            try {
                msg = ois.readObject();
                if (msg instanceof String) {
                    dispMessage("\n" + msg);
                }else if (msg instanceof Integer){
                    drawAgain();
                }else if (msg instanceof Boolean && Boolean.TRUE.equals(msg)){
                    dispMessage("\nC:A student wants to ask a question");
                }
            }
            catch (ClassNotFoundException e) {
                dispMessage("Unknown");
            }
        }while(!msg.equals("S:ExitTheSystem"));
    }

    private void closeConn() {
        dispMessage("\nTerminating Conn\n");
        setButtonEnabled(false);

        try {
            oos.close();
            ois.close();
            conn.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawAgain() throws IOException{
        Object incomingObject = null;
        Object incomingObjectTwo = null;
        do {
            try {
                incomingObject = ois.readObject();
                incomingObjectTwo = ois.readObject();
                if (incomingObject instanceof Integer){
                    x = (int) incomingObject;
                    y = (int) incomingObjectTwo;
                    jpCenter.repaint();
                }else if (incomingObject instanceof String){
                    processConn();
                }
            } catch (ClassNotFoundException e) {
                dispMessage("Unknown");
            }
        }while (incomingObject instanceof Integer && incomingObjectTwo instanceof Integer);
    }



    private void send(Object text) {
        try {
            oos.writeObject("S:"+text);
            oos.flush();
            dispMessage("\nS:"+text);
        }
        catch (IOException e) {
            jta.append("\nError");
        }
    }

    private void writeShapeFlag(Object text){
        try{
            System.out.println(text);
            oos.writeObject(text);
            oos.flush();
        }catch (IOException e){
            jta.append("\nError");

        }
    }

    private void sendBounds(){
        try {
            oos.writeObject(value);
            oos.writeObject(value2);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void raiseYourHandWriter(Object text){
        try {
            oos.writeObject(text);
            oos.flush();
            if (MyClient.flagToRaiseYourHand == true) {
                dispMessage("\nC:A student wants to ask q question");
            }
        }catch (IOException e){
            jta.append("\nError");
        }
    }
    private void dispMessage(final Object string) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                jta.append(string.toString());

            }
        });
    }

    private void setButtonEnabled(final boolean b) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                jbSend.setEnabled(b);

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbAttendance) {
            flagAttendace = false;
            flago = true;
            flag = true;
            flagl = true;
            flagc = true;
            flagToStartTime = true;
        }
        else if (e.getSource() == jbStartTime) {
            flagToStartTime = false;
            flag = true;
            flago = true;
            flagl = true;
            flagc = true;
            flagAttendace = true;

            if (!flagToStartTime) {
                if (startTime < 0) {
                    startTime = System.currentTimeMillis();
                }
                long now = System.currentTimeMillis();
                long clockTime = now - startTime;
                if (clockTime >= duration) {
                    clockTime = duration;
                    timer.stop();
                }
                SimpleDateFormat df = new SimpleDateFormat("mm:ss:SSS");
                label.setText(df.format(duration - clockTime));
            }
            timer.setInitialDelay(0);
        }
        else if(e.getSource() == jbCircle) {
            flago  = false ;
            flag = true;
            flagl =true;
            flagc = true;
            flagAttendace = true;
            flagToStartTime = true;
            writeShapeFlag("flagToOval");
        }
        else if(e.getSource() == jbRectangle) {
            flag = false;
            flago = true;
            flagl = true;
            flagc = true;
            flagAttendace = true;
            flagToStartTime = true;
            writeShapeFlag("flagToRect");
        }
        else if(e.getSource() == jbLine) {
            flagl = false;
            flag = true;
            flago = true;
            flagc= true;
            flagAttendace = true;
            flagToStartTime = true;
            writeShapeFlag("flogToLine");
        }
        else if(e.getSource() == jbClear) {
            flagc = false;
            flag = true;
            flago = true;
            flagl = true;
            flagAttendace = true;
            flagToStartTime = true;

            rectangles.clear();
            ovals.clear();
            lines.clear();
            lines2.clear();
            writeShapeFlag("flagToClear");

            counter = 0;
            jlcounter.setText("counter:" +counter);
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            y--;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x++;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            y++;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            x--;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e){
        if (flagToStartTime == false){
            if (!timer.isRunning()) {
                startTime = -1;
                timer.start();
            }
        }
        else if (flagAttendace == false) {
            try {
                System.out.println("Here");
                String myValue = JOptionPane.showInputDialog("Attendace");
                JOptionPane.showMessageDialog(jpCenter, "Student name:" + myValue, "Student name", JOptionPane.PLAIN_MESSAGE);
                System.out.println(myValue);
                fileWriter = new FileWriter(new File("attendance.txt"), true);
                fileWriter.write(myValue);
                fileWriter.write(System.lineSeparator());
                fileWriter.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if (flag == false) {
                Points p = new Points();
                p.x = e.getX();
                p.y = e.getY();
                rectangles.add(p);
                value = e.getX();
                value2 = e.getY();
                sendBounds();
                counter++;
                jlcounter.setText("counter:" + counter);
            }
           else if (flago == false) {
                Points z = new Points();
                z.x = e.getX();
                z.y = e.getY();
                ovals.add(z);
                value = e.getX();
                value2 = e.getY();
                sendBounds();
                counter++;
                jlcounter.setText("counter:" + counter);
            }
            repaint();
    }
    @Override
    public void mousePressed(MouseEvent e) {
            if (flagl == false) {
                Points c = new Points();
                x1 = c.x = e.getX();
                y1 = c.y = e.getY();
                lines.add(c);
                value = e.getX();
                value2 = e.getY();
                System.out.println(value + "-" + value2);
            }
            repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
            if (flagl == false) {
                Points b = new Points();
                x2 = b.x = e.getX();
                y2 = b.y = e.getY();
                lines2.add(b);
                value = e.getX();
                value2 = e.getY();
                System.out.println(value + "-" + value2);
                counter++;
                jlcounter.setText("counter:" + counter);
            }
            repaint();
        }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}













