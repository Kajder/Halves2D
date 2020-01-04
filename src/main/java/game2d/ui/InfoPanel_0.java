package game2d.ui;

import game2d.Handler;
import game2d.ui.support.GBC;

import javax.swing.*;
import java.awt.*;


public class InfoPanel_0 extends SettingsPanel {
    private Handler handler;
    private JLabel mainLabel;
    private String mainString;
    private Font font = new Font("SansSerif", Font.BOLD, 20);

    public InfoPanel_0(Handler handler) {
        super(handler);
        this.handler = handler;
        mainLabel = new JLabel();
        mainString = "<html>1) Score 5 goals to win the match.<br>" +
                "2) Players cannot step into opponent's half of the pitch.<br>" +
                "3) Match starts when the referee kicks the ball into the pitch with random force <br>" +
                "(the ball moves along the center line and it is available for both players).<br>" +
                "4) The ball will be given to your opponent after your Delay Game Clock countdown is finished.<br>" +
                "5) You can reset your Delay Game countdown by passing the ball through opponent's penalty box.<br><br>" +
                "Hint 1: make a first-touch shot to get an advantage from Player's and ball's masses and velocities.<br>" +
                "The higher is the Player-ball relative velocity, the more momentum Player may transfer to the ball.<br>" +
                "Hint 2: Player can drop the ball to make a run-up before the shot <br>" +
                "(that will increase initial ball velocity vs kicking the ball that moves together with running Player).<br>" +
                "Hint 3: Use the walls to bend the ball (towards opponent's goal or towards you <br>" +
                "to make stronger first-time shot).<br>" +
                "Hint 4: Adjust physics, Players, ball and time parameters available in Settings to get more fun from the game.</html>";

        mainLabel.setText(mainString);
        mainLabel.setFont(font);
        mainLabel.setForeground(Color.BLACK);
        mainLabel.setBackground(Color.DARK_GRAY);
        mainLabel.setPreferredSize(new Dimension(handler.getGame().getDisplay().getCanvas().getWidth(), handler.getGame().getDisplay().getCanvas().getHeight() * 9 / 10));

        //POPULATING BOTTOM BAR
        bottomBar.add(mainLabel, new GBC(0, 0, 1, 1).setFill(1).setInsets(20, 20, 20, 20).setWeight(1, 1));
        labelTitle.setText("RULES AND HINTS");
        addTopBottomPanels();
    }
}