package game2d.ui;

import game2d.Handler;
import game2d.ui.support.ColorPanel;
import game2d.ui.support.GBC;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel{
    Handler handler;
    JLabel labelTitle;
    JPanel topBar, bottomBar;
    Font font1 = new Font("SansSerif", Font.BOLD, 20);


    public SettingsPanel(Handler handler){
        setLayout(new GridBagLayout());
        setBackground(Color.DARK_GRAY);
        //TITLE
        labelTitle=new JLabel("abc");
        labelTitle.setBackground(Color.DARK_GRAY);
        labelTitle.setPreferredSize(new Dimension(handler.getGame().getDisplay().getCanvas().getWidth()/4, handler.getGame().getDisplay().getCanvas().getHeight()/15));
        labelTitle.setHorizontalAlignment(JTextField.CENTER);
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setFont(font1);

        //split settings panel into two main parts (for navigation and for the rest)
        topBar=new JPanel();
        topBar.setLayout(new GridBagLayout());
        topBar.setBackground(Color.DARK_GRAY);
        topBar.setPreferredSize(new Dimension(handler.getGame().getDisplay().getCanvas().getWidth(), handler.getGame().getDisplay().getCanvas().getHeight()/10));

        bottomBar=new JPanel();
        bottomBar.setPreferredSize(new Dimension(handler.getGame().getDisplay().getCanvas().getWidth(), handler.getGame().getDisplay().getCanvas().getHeight()*9/10));
        bottomBar.setLayout(new GridBagLayout());
        bottomBar.setBackground(Color.DARK_GRAY);
    }

    void addTopBottomPanels(){
        add(topBar, new GBC(0,0,1,1).setFill(1));
        add(bottomBar, new GBC(0,1,1,9).setFill(1));
        for (int i=0;i<10;i++){
            add(new ColorPanel(Color.DARK_GRAY), new GBC(0,i,1,1)
                    .setFill(1)
                    .setWeight(1,1));
        }
    }

    public void addNavigationButtons(NavigationButton button1, NavigationButton button2, NavigationButton button3){
        topBar.add(new ColorPanel(Color.DARK_GRAY, 49,30), new GBC(0,0,1,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTHWEST)
                .setInsets(5,0,0,0));
        topBar.add(new ColorPanel(Color.DARK_GRAY, 49,30), new GBC(1,0,1,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTHWEST)
                .setInsets(5,0,0,0));
        topBar.add(new ColorPanel(Color.DARK_GRAY, 49,30), new GBC(2,0,1,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTHWEST)
                .setInsets(5,0,0,0));
        topBar.add(labelTitle, new GBC(3,0,4,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTH)
                .setInsets(5,0,0,0));
        topBar.add(button1, new GBC(7,0,1,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTHEAST)
                .setInsets(5,5,0,0));
        topBar.add(button2, new GBC(8,0,1,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTHEAST)
                .setInsets(5,0,0,0));
        topBar.add(button3, new GBC(9,0,1,1)
                .setFill(0)
                .setAnchor(GridBagConstraints.NORTHEAST)
                .setInsets(5,0,0,5));

        for (int i=0;i<10;i++){
            topBar.add(new ColorPanel(Color.DARK_GRAY), new GBC(i,0,1,1)
                    .setFill(1)
                    .setWeight(1,1));
        }
    }

}