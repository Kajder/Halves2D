package game2d.ui;
import game2d.Handler;
import game2d.ui.support.ColorPanel;
import game2d.ui.support.GBC;
import gfx.Assets;

import javax.swing.*;
import java.awt.*;


public class InfoPanel_3 extends SettingsPanel {
    private Handler handler;
    private JLabel mainLabel;
    private Font font = new Font("SansSerif", Font.BOLD, 20);

    public InfoPanel_3(Handler handler){
        super(handler);
        this.handler=handler;


        //POPULATING BOTTOM BAR
        //bottomBar.add(mainLabel, new GBC(0,0,1,1).setFill(1).setInsets(20,20,20,20).setWeight(1,1));
        labelTitle.setText("GAMEPAD CONTROL");
        addTopBottomPanels();
    }
}