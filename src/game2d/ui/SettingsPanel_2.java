package game2d.ui;

import game2d.Handler;

public class SettingsPanel_2 extends SettingsPanel {
    private Handler handler;

    public SettingsPanel_2(Handler handler){
        super(handler);
        this.handler=handler;


        //actions

        labelTitle.setText("TIME SETTINGS");
        addTopBottomPanels();
    }
}