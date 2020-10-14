import functions.PanelFunctions;
import functions.panel.GeneralFunctions;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public  class FrameClass extends JFrame implements ActionListener, MouseListener,Serializable {

    private final JSplitPane splitPane;  // split the window in top and bottom
    private final JPanel bottomPanel;   // container panel for the bottom
    private final JScrollPane scrollPane; // makes the text scrollable
    private final JTextArea textArea;     // the text
    private final JPanel inputPanel;      // under the text a container for all the input elements
    JTextField textField;   // a textField for the text the user inputs
    private final JButton button;
    private final JPanel eastPanel;
    GridBagLayout gl;
    JButton jbSquare;
    JButton jbLine;
    JButton jbCircle;
    private final JPanel topPanel;
    private final JButton colorChooser;
    private final JColorChooser pickColor;
    private boolean flagToColorChooser;
    private boolean flagToRaiseYourHand;
    protected static int x;
    protected static int y;
    private final JButton raiseYourHandButton;
    private GeneralFunctions panelFunctions;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket conn;
    private ServerSocket server;


    public FrameClass() {
        panelFunctions = new GeneralFunctions();
        splitPane = new JSplitPane();

        eastPanel = new JPanel(new GridBagLayout());
        eastPanel.setLayout(new GridBagLayout());
        eastPanel.setBackground(Color.WHITE.darker());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3,3,3,3);

        jbSquare = new JButton("Square");
        jbSquare.addActionListener(this);
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;

        eastPanel.add(jbSquare,gbc);

        jbCircle = new JButton("Circle");
        jbCircle.addActionListener(this);
        gbc.weightx = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 0;
        eastPanel.add(jbCircle,gbc);

        jbLine = new JButton("Line");
        jbLine.addActionListener(this);
        gbc.weightx = 0.5;
        gbc.gridx = 2;
        gbc.gridy = 0;
        eastPanel.add(jbLine,gbc);

        bottomPanel = new JPanel();// our bottom component

        topPanel = new PaintPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.addMouseListener(this);
        topPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                repaint();
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
        });
        topPanel.add(eastPanel);
        add(topPanel);


        colorChooser = new JButton("Color Chooser");
        colorChooser.addMouseListener(this);
        colorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelFunctions.openColorBox(topPanel,pickColor,splitPane,flagToColorChooser);
                flagToColorChooser = !flagToColorChooser;
            }
        });
        topPanel.add(colorChooser);

        pickColor = new JColorChooser();
        pickColor.setBorder(null);
        pickColor.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                panelFunctions.newValueOfColor(colorChooser,pickColor);
            }
        });

        raiseYourHandButton = new JButton("Raise Your Hand");
        raiseYourHandButton.setIcon(new ImageIcon("C:\\DrawPackage\\src\\images\\hand2.png"));
        raiseYourHandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelFunctions.raiseYourHandFunctions(raiseYourHandButton, flagToRaiseYourHand);
                flagToRaiseYourHand = !flagToRaiseYourHand;
            }
        });
        topPanel.add(raiseYourHandButton);



        // in our bottom panel we want the text area and the input components
        scrollPane = new JScrollPane();  // this scrollPane is used to make the text area scrollable
        textArea = new JTextArea();// this text area will be put inside the scrollPane
        textArea.setEditable(false);

        // the input components will be put in a separate panel
        inputPanel = new JPanel();
        textField = new JTextField();
        textField.setEnabled(false);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        button = new JButton("send");
        button.addMouseListener(this);
        button.addMouseListener(new MouseListener() {
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
        });

        // now lets define the default size of our window and its layout:
        setPreferredSize(new Dimension(1200, 1200));     // let's open the window with a default size of 400x400 pixels
        // the contentPane is the container that holds all our components
        getContentPane().setLayout(new GridLayout());  // the default GridLayout is like a grid with 1 column and 1 row,
        // we only add one element to the window itself
        getContentPane().add(splitPane);               // due to the GridLayout, our splitPane will now fill the whole window

        // let's configure our splitPane:
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  // we want it to split the window verticaly
        splitPane.setDividerLocation(800);                    // the initial position of the divider is 200 (our window is 400 pixels high)
        splitPane.setTopComponent(topPanel);                  // at the top we want our "topPanel"
        splitPane.setBottomComponent(bottomPanel);            // and at the bottom we want our "bottomPanel"

        // our topPanel doesn't need anymore for this example. Whatever you want it to contain, you can add it here
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // BoxLayout.Y_AXIS will arrange the content vertically

        bottomPanel.add(scrollPane);                // first we add the scrollPane to the bottomPanel, so it is at the top
        scrollPane.setViewportView(textArea);       // the scrollPane should make the textArea scrollable, so we define the viewport
        bottomPanel.add(inputPanel);                // then we add the inputPanel to the bottomPanel, so it under the scrollPane / textArea

        // let's set the maximum size of the inputPanel, so it doesn't get too big when the user resizes the window
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));     // we set the max height to 75 and the max width to (almost) unlimited
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));   // X_Axis will arrange the content horizontally

        inputPanel.add(textField);        // left will be the textField
        inputPanel.add(button);           // and right the "send" button

        pack();
    }


    private void send(String text){
        try {
            oos.writeObject("S:"+text);
            oos.flush();
            dispMessage("\nS:"+text);
        }catch (IOException e){
            textArea.append("\nError");
        }
    }
    private void dispMessage(final String string) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                textArea.append(string);

            }
        });
    }

    public void runServer(){
        try {
            server = new ServerSocket(12345, 100);

            while(true) {
                try {
                    waitConn();
                    streams();
                    processConn();
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

    private void processConn() throws IOException{
        String msg = "";

        do {
            try {
                msg = (String) ois.readObject();
                dispMessage("\nserver"+msg);
            }
            catch (ClassNotFoundException e) {
                dispMessage("Unknown");
            }
        }while(!msg.equals("C:ExitTheSystem"));
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

    private void closeConn() {
        dispMessage("\nTerminating Conn\n");

        try {
            oos.close();
            ois.close();
            conn.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }







    public static void main (String args[]){
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new FrameClass().setVisible(true);
                }
            });
        }

    @Override
    public void actionPerformed(ActionEvent e) {


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