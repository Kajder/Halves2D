package game2d.ui;

import game2d.Handler;
import game2d.ui.support.ColorPanel;
import game2d.ui.support.GBC;

import java.awt.*;

public class SettingsPanel_1 extends SettingsPanel {

    private Handler handler;

    public SettingsPanel_1(Handler handler){
    this.handler=handler;
    setLayout(new GridBagLayout());
    setBackground(Color.BLUE);

    }




    public void addNavigationButtons(NavigationButton button1, NavigationButton button2, NavigationButton button3){
        add(button1, new GBC(18,0,1,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTHEAST)
                .setInsets(5,0,0,10));
        add(button2, new GBC(19,0,1,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTHEAST)
                .setInsets(5,0,0,10));
        add(button3, new GBC(20,0,1,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTHEAST)
                .setInsets(5,0,0,10));
        for (int i=0;i<=20;i++){
            add(new ColorPanel(Color.darkGray), new GBC(i,0,1,1)
                    .setFill(1)
                    .setWeight(1,1));
        }
    }
}
