import javax.swing.*;

public class MainClient {
    public static void main(String[] args) {
        MyClient myClient = new MyClient("127.0.0.1");
        myClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myClient.setVisible(true);
        myClient.setSize(1200,1200);
        myClient.runClient();
    }
}
