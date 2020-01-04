package game2d.ui.support;

import javax.swing.*;
import java.awt.*;

public class ColorLabel extends JLabel {
    public ColorLabel(String text) {
        super(text);
    }

    public ColorLabel(String text, int width, int height) {
        super(text);
        setPreferredSize(new Dimension(width, height));
    }

    public ColorLabel(String text, int width, int height, Color color) {
        super(text);
        setPreferredSize(new Dimension(width, height));
        setBackground(color);
    }
}
