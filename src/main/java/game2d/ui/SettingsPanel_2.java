package game2d.ui;

import game2d.Handler;
import game2d.ui.support.GBC;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel_2 extends SettingsPanel {
    private Handler handler;
    private Font font = new Font("SansSerif", Font.BOLD, 20);

    public SettingsPanel_2(Handler handler) {
        super(handler);
        this.handler = handler;
        JLabel mainLabel = new JLabel();

        String mainString = "<html><p align=\"justify\">Settings left TO DO:<br><br>" +
                "1) time of acceleration from chosen speed of walk to the speed of sprint (now fixed to 3 sec)<br>" +
                "2) time to perform first-touch shot (now fixed to 1 sec)<br>" +
                "3) time of loading maximum shot parameters: force, rotation, direction<br>" +
                "(now fixed to 2, 0.5, 1.5 sec).<br>" +
                "The longer the loading time, the higher the resolution of given parameter.<br>" +
                "4) limit value of the Delay Game Clock (now fixed to 20 sec)</p</html>";

        mainLabel.setText(mainString);
        mainLabel.setFont(font);
        mainLabel.setForeground(Color.BLACK);
        mainLabel.setBackground(Color.DARK_GRAY);
        mainLabel.setPreferredSize(new Dimension(handler.getGame().getDisplay().getCanvas().getWidth(), handler.getGame().getDisplay().getCanvas().getHeight() * 9 / 10));

        //actions
        bottomBar.add(mainLabel, new GBC(0, 1, 2, 1).setFill(1).setInsets(0, 20, 20, 20).setWeight(1, 1));
        labelTitle.setText("TIME SETTINGS");
        addTopBottomPanels();
    }
}