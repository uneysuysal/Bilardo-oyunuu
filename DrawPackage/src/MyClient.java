import functions.panel.GeneralFunctions;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class MyClient extends JFrame implements ActionListener,KeyListener ,MouseListener, Serializable {

    BorderLayout bl;
    GridLayout gl;
    JTextArea jta;
    JTextField jtf;
    JButton jbSend;
    JPanel jpSouth;
    JSplitPane splitPane;
    JPanel gridBagLayout;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String srv;
    private Socket myClient;
    protected static int x;
    protected static int y;
    protected static  boolean flagToClient = false;
    protected static  boolean flagToRectangle = false;
    protected static  boolean flagToLine = false;
    protected static  boolean flagToOval = false;
    protected static  boolean flagToClear = false;
    GeneralFunctions panelFunctions;
    JPanel jpCenter;
    JButton raiseYourHandButton;
    protected static boolean flagToRaiseYourHand;
    JScrollPane scrollPane;
    JPanel inputPanel;
    protected static ArrayList<Points> rectangles = new ArrayList<Points>();
    protected static ArrayList<Points> ovals = new ArrayList<Points>();
    protected static ArrayList<Points> lines = new ArrayList<Points>();

    public MyClient(String info) {
        super("My Client Application");

        srv = info;

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


        jpSouth = new JPanel();

        //Top Panel
        jpCenter = new ClientPaintPanel();
        jpCenter.setBackground(Color.WHITE);
        jpCenter.add(gridBagLayout);
        add(jpCenter);


        raiseYourHandButton = new JButton("Raise Your Hand");
        raiseYourHandButton.setIcon(new ImageIcon("C:\\DrawPackage\\src\\images\\hand2.png"));
        raiseYourHandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelFunctions.raiseYourHandFunctions(raiseYourHandButton, flagToRaiseYourHand);
                flagToRaiseYourHand = !flagToRaiseYourHand;
                raiseYourHandWriter(flagToRaiseYourHand);
            }
        });
        jpCenter.add(raiseYourHandButton);


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


   /*     bl = new BorderLayout();
        setLayout(bl);

        jta = new JTextArea();
        jta.setEditable(false);
        add(new JScrollPane(jta),BorderLayout.CENTER);

        gl = new GridLayout(2, 1);
        jpSouth = new JPanel();
        jpSouth.setLayout(gl);
        add(jpSouth,BorderLayout.SOUTH);

        jtf = new JTextField();
        jpSouth.add(jtf);

        jbSend = new JButton("Send");
        jbSend.setEnabled(false);
        jpSouth.add(jbSend);
        jbSend.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                send(jtf.getText());
                jtf.setText("");
            }
        });*/
     pack();
    }

    public void runClient() {
        try {
            connToS();
            streams();
            processConn();
            drawAgain();

        }
        catch (EOFException e) {
            dispMessage("\nClient Terminated Conn\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeConn();
        }

    }

    private void connToS() throws IOException{
        dispMessage("Attempting\n");
        myClient = new Socket(InetAddress.getByName(srv), 12345);
    }

    private void streams() throws IOException{
        oos = new ObjectOutputStream(myClient.getOutputStream());
        oos.flush();

        ois = new ObjectInputStream(myClient.getInputStream());
        dispMessage("\nStreams\n");
    }

    private void processConn() throws IOException{
        send("Successful");
        setButtonEnabled(true);
        Object msg = null;
        do {
            try {
                msg = ois.readObject();
                System.out.println(msg);
                if (msg.toString().contains("flagToOval")){
                    flagToOval = true;
                    flagToLine = false;
                    flagToRectangle = false;
                    flagToClear = false;
                    drawAgain();
                }else if (msg.toString().contains(("flagToRect"))){
                    flagToRectangle = true;
                    flagToLine = false;
                    flagToOval = false;
                    flagToClear = false;
                    drawAgain();
                }else if (msg.toString().contains("flagToLine")){
                    flagToLine = true;
                    flagToOval = false;
                    flagToRectangle = false;
                    flagToClear = false;
                    drawAgain();
                }else if (msg.toString().contains("flagToClear")){
                    flagToLine = false;
                    flagToOval = false;
                    flagToRectangle = false;
                    flagToClear = true;
                    drawAgain();
                }
                else if (msg instanceof String){
                    dispMessage("\n"+msg);
                }else if (msg instanceof Integer){
                    drawAgain();
                }
            }
            catch (ClassNotFoundException e) {
                dispMessage("Unknown");
            }
        }while(!msg.equals("S:ExitTheSystem"));
    }


    private void drawAgain() throws IOException{
        Object incomingObject = null;
        Object incomingObjectTwo = null;
        do {
            try {
                incomingObject = ois.readObject();
                incomingObjectTwo = ois.readObject();
                if (incomingObject instanceof Integer && incomingObjectTwo instanceof Integer){
                    x = (int) incomingObject;
                    y = (int) incomingObjectTwo;
                    Points p = new Points();
                    p.x = (int) incomingObject;
                    p.y = (int) incomingObjectTwo;
                    if (flagToRectangle){
                        rectangles.add(p);
                        processConn();
                    }else if (flagToOval){
                        ovals.add(p);
                        processConn();
                    }else if(flagToLine){
                        lines.add(p);
                        processConn();
                    }
                    jpCenter.repaint();
                }else if (incomingObject instanceof String){
                    if (flagToClear){
                        rectangles.clear();
                        ovals.clear();
                        lines.clear();
                        processConn();
                    }
                    processConn();
                }
            } catch (ClassNotFoundException e) {
                dispMessage("Unknown");
            }
        }while (incomingObject instanceof Integer && incomingObjectTwo instanceof Integer);
    }
    private void closeConn() {
        dispMessage("\nTerminating Conn\n");
        setButtonEnabled(false);

        try {
            oos.close();
            ois.close();
            myClient.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(Object text) {
        try {
            oos.writeObject("C:"+text);
            oos.flush();
            dispMessage("\nC:"+text);
        }
        catch (IOException e) {
            jta.append("\nError");
        }
    }

    private void raiseYourHandWriter(Object text){
        try {
            oos.writeObject(text);
            oos.flush();
            if (flagToRaiseYourHand == true) {
                dispMessage("\nC:A student wants to ask a question");
            }
        }catch (IOException e){
            jta.append("\nError");
        }
    }

    private void dispMessage(Object string) {
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

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}













