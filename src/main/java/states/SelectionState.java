package states;

import game2d.Handler;
import game2d.ui.SelectionPanel;
import game2d.ui.UIManager;

import javax.swing.*;
import java.awt.*;


public class SelectionState extends State {
    private Handler handler;
    private JPanel selectionPanel;
    private JFrame frame;
    private Canvas canvas;


    public SelectionState(Handler handler) {
        super(handler);
        this.handler = handler;
        frame = handler.getGame().getDisplay().getFrame();
        canvas = handler.getGame().getDisplay().getCanvas();
        canvas.setVisible(false);
        if (selectionPanel == null) {
            selectionPanel = new SelectionPanel(handler);
            frame.add(selectionPanel);
            frame.validate();
        } else {
            selectionPanel.setVisible(true);
            frame.validate();
        }


    }


    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void renderG2D(Graphics2D g2d) {

    }

    @Override
    public UIManager getUIManager() {
        return null;
    }
}
