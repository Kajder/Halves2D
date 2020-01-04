package game2d.ui;

import game2d.Handler;

import javax.swing.*;
import java.awt.*;

public class SelectionPanel extends JPanel {
    private Handler handler;
    private Dimension dimension;
    private int width, height;

    public SelectionPanel(Handler handler) {
        this.handler = handler;
        width = handler.getGame().getWidth();
        height = handler.getGame().getHeight();
        setLayout(new GridLayout(2, 1));
        addMouseListener(handler.getMouseManager());
        addMouseMotionListener(handler.getMouseManager());
        setPreferredSize(new Dimension(width, height));

    }


}
