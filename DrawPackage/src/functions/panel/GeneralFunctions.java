package functions.panel;

import functions.PanelFunctions;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GeneralFunctions  implements Serializable {
    public static Color c;

    public void raiseYourHandFunctions(JButton jButton,boolean flag) {
        if(flag){
            jButton.setIcon(new ImageIcon("C:\\DrawPackage\\src\\images\\hand2.png"));
            System.out.println("Hey");
        }else {
            jButton.setIcon(new ImageIcon("C:\\DrawPackage\\src\\images\\hand1.png"));
            System.out.println("Hey1");
        }
        jButton.validate();
    }

    public void newValueOfColor(JButton jButton, JColorChooser jColorChooser) {
        jButton.setBackground(jColorChooser.getSelectionModel().getSelectedColor());
        c  = jColorChooser.getSelectionModel().getSelectedColor();

    }

    public void openColorBox(JPanel jPanel,JColorChooser jColorChooser,JSplitPane jSplitPane,boolean flag) {
        if (flag){
            jPanel.remove(jColorChooser);
        }else {
            jColorChooser.setBounds(jColorChooser.getX(),jColorChooser.getY()+20,600,300);
            jColorChooser.setVisible(true);
            jPanel.add(jColorChooser);
        }
        jPanel.validate();
        jSplitPane.validate();
        jSplitPane.setDividerLocation(800);
        jPanel.repaint();

    }
}
