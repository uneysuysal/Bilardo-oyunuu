import functions.panel.GeneralFunctions;

import javax.swing.*;
import java.awt.*;

public class ClientPaintPanel extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);
            g.setColor(GeneralFunctions.c);
                for (int i = 0; i < MyClient.ovals.size(); i++) {
                    g.drawOval(MyClient.ovals.get(i).x, MyClient.ovals.get(i).y, 100, 50);
                }
                for (int i = 0; i < MyClient.rectangles.size(); i++) {
                    g.drawRect(MyClient.rectangles.get(i).x,MyClient.rectangles.get(i).y, 150, 200);
                }
                for (int i = 0; i < MyClient.lines.size(); i++) {
                    g.drawLine(MyClient.lines.get(i).x, MyClient.lines.get(i).y, 200, 100);
                }
        }
    }