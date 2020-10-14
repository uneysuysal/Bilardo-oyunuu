package functions;

import javax.swing.*;
import java.awt.*;

public interface PanelFunctions {
    void raiseYourHandFunctions(JButton jButton,boolean flag);
    void newValueOfColor(JButton jButton, JColorChooser jColorChooser, Color c);
    void openColorBox(JPanel jPanel,JColorChooser jColorChooser,JSplitPane jSplitPane,Boolean flagToColorChooser,int x,int y);



}
