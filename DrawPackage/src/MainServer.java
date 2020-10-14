import javax.swing.*;

public class MainServer {
    public static void main(String[] args) {
        MyServer myServer = new MyServer();
        myServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myServer.setVisible(true);
        myServer.setSize(1200,1200);
        myServer.runServer();
        myServer.pack();
        myServer.setLocationRelativeTo(null);
    }
}
