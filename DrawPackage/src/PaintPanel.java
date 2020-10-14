import functions.panel.GeneralFunctions;

import java.awt.*;

import javax.swing.JPanel;

public class PaintPanel extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(GeneralFunctions.c);
            for (int i = 0; i < MyServer.ovals.size(); i++) {
                g.drawOval(MyServer.ovals.get(i).x, MyServer.ovals.get(i).y, 100, 50);
            }
            for (int i = 0; i < MyServer.rectangles.size(); i++) {
                g.drawRect(MyServer.rectangles.get(i).x, MyServer.rectangles.get(i).y, 150, 200);
            }
            for (int i = 0; i < MyServer.lines.size(); i++) {
                g.drawLine(MyServer.lines.get(i).x, MyServer.lines.get(i).y, MyServer.lines2.get(i).x, MyServer.lines2.get(i).y);
            }
        }
}
