package game2d.ui;

import game2d.Handler;


public class SettingsPanel_3 extends SettingsPanel{
    private Handler handler;
    public SettingsPanel_3(Handler handler){
        super(handler);
        this.handler=handler;


        //actions

        labelTitle.setText("BALL");
        addTopBottomPanels();
    }

}
