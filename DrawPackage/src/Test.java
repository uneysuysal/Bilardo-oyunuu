import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Test extends JFrame {

    BorderLayout bl;
    GridLayout gl;
    JTextArea jta;
    JTextField jtf;
    JButton jbSend;
    JPanel jpSouth;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServerSocket server;
    private Socket conn;

    public Test() {
        super("My Server Application");

        initGui();
    }

    public void initGui() {

        bl = new BorderLayout();
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
        });
    }

    public void runServer() {
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
        String msg = "";

        do {
            try {
                msg = (String) ois.readObject();
                dispMessage("\n"+msg);
            }
            catch (ClassNotFoundException e) {
                dispMessage("Unknown");
            }
        }while(!msg.equals("C:ExitTheSystem"));
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

    private void send(String text) {
        try {
            oos.writeObject("S:"+text);
            oos.flush();
            dispMessage("\nS:"+text);
        }
        catch (IOException e) {
            jta.append("\nError");
        }
    }

    private void dispMessage(final String string) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                jta.append(string);

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
}













