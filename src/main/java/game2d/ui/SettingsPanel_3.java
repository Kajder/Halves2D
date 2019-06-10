package game2d.ui;

import game2d.Handler;
import game2d.ui.support.GBC;

import javax.swing.*;
import java.awt.*;


public class SettingsPanel_3 extends SettingsPanel{
    private Handler handler;
    private Font font = new Font("SansSerif", Font.BOLD, 20);

    public SettingsPanel_3(Handler handler){
        super(handler);
        this.handler=handler;
        JLabel mainLabel = new JLabel();

        String mainString = "<html><p align=\"justify\">Settings left TO DO:<br><br>" +
            "8 different balls to chose from; each with different mass, radius <br>" +
            "and plot showing drag coeff vs velocity relation <br>"+
            "(one custom ball + 7 historical balls from World Cup).<br>";

        mainLabel.setText(mainString);
        mainLabel.setFont(font);
        mainLabel.setForeground(Color.BLACK);
        mainLabel.setBackground(Color.DARK_GRAY);
        mainLabel.setPreferredSize(new Dimension(handler.getGame().getDisplay().getCanvas().getWidth(), handler.getGame().getDisplay().getCanvas().getHeight()*9/10));


        //actions
        bottomBar.add(mainLabel, new GBC(0,1,2,1).setFill(1).setInsets(0,20,20,20).setWeight(1,1));
        labelTitle.setText("BALL");
        addTopBottomPanels();
    }

}
