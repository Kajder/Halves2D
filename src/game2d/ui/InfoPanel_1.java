package game2d.ui;
import game2d.Handler;
import game2d.ui.support.ColorPanel;
import game2d.ui.support.GBC;
import gfx.Assets;

import javax.swing.*;
import java.awt.*;


public class InfoPanel_1 extends SettingsPanel {
    private Handler handler;
    private JLabel mainLabel, upperLabel, photoLabel;
    private JPanel upperPanel;
    private String mainString, upperString;
    private Font font = new Font("SansSerif", Font.BOLD, 20);

    public InfoPanel_1(Handler handler){
        super(handler);
        this.handler=handler;
        mainLabel=new JLabel();
        upperLabel=new JLabel();

        upperString="<html><p align=\"justify\">Hello there! I am Lukasz Kajder, an author of Halves 2D game <br>" +
                "and I sincerely hope you will have at least as much fun playing the game as I had creating it.<br>" +
                "It has been a long and interesting journey for me, as the Halves 2D project " +
                "was the very first one for me written using Object Oriented Programming, first one in JAVA and first game I ever wrote at all.</p</html>";

        upperLabel.setText(upperString);
        upperLabel.setFont(font);
        upperLabel.setForeground(Color.BLACK);
        upperLabel.setBackground(Color.DARK_GRAY);
        upperLabel.setPreferredSize(new Dimension(handler.getGame().getDisplay().getCanvas().getWidth(), 220));
        //upperLabel.setBorder(BorderFactory.createEtchedBorder());

        mainString="<html><p align=\"justify\">It has been created from scratch, with no game engines, using Eclipse and IntelliJ IDEA.<br>" +
                "To start up with the core of the game, I have followed a YT tutorial from CodeNMore that focused on 2D Tile Game.<br><br>"+
                "Halves 2D project is not closed though, as there is still a lot of adjustments and functionality to be maintained!<br>" +
                "Some of it I already have figured out and for some I am going to need a feedback from you.<br>" +
                "Feel free to write me on premium.mechatronics@gmail.com.</p</html>";

        mainLabel.setText(mainString);
        mainLabel.setFont(font);
        mainLabel.setForeground(Color.BLACK);
        mainLabel.setBackground(Color.DARK_GRAY);
        mainLabel.setPreferredSize(new Dimension(handler.getGame().getDisplay().getCanvas().getWidth(), handler.getGame().getDisplay().getCanvas().getHeight()*9/10));

        photoLabel=new JLabel(new ImageIcon(Assets.photoKajder.getScaledInstance(150,212, Image.SCALE_SMOOTH)));
        photoLabel.setPreferredSize(new Dimension(160,220));
        photoLabel.setBackground(Color.DARK_GRAY);
        //photoLabel.setBorder(BorderFactory.createEtchedBorder());

        upperPanel=new JPanel();
        upperPanel.setLayout(new GridBagLayout());
        upperPanel.setBackground(Color.DARK_GRAY);
        upperPanel.setPreferredSize(new Dimension(handler.getGame().getDisplay().getCanvas().getWidth(), 220));
        upperPanel.add(photoLabel, new GBC(0,0,1,1).setFill(1).setWeight(1,1));
        upperPanel.add(upperLabel, new GBC(1,0,9,1).setFill(1).setWeight(1,1));
        for (int i=0;i<10;i++){
            upperPanel.add(new ColorPanel(Color.DARK_GRAY), new GBC(i,0,1,1)
                    .setFill(1)
                    .setWeight(1,1));
        }

        //POPULATING BOTTOM BAR
        bottomBar.add(upperPanel, new GBC(0,0,2,1).setFill(1).setInsets(20,20,0,20).setWeight(1,1));
        bottomBar.add(mainLabel, new GBC(0,1,2,1).setFill(1).setInsets(0,20,20,20).setWeight(1,1));
        labelTitle.setText("PROJECT AND AUTHOR");
        addTopBottomPanels();
    }
}