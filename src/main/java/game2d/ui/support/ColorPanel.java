package game2d.ui.support;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {

    public ColorPanel(Color color) {
        super.setBackground(color);
    }

    public ColorPanel(Color color, int width, int height) {
        super.setBackground(color);
        super.setPreferredSize(new Dimension(width, height));

    }


}
