package game2d.ui;

import javax.swing.*;

public abstract class SettingsPanel extends JPanel{

    public int panelID;
    public abstract void addNavigationButtons(NavigationButton button1, NavigationButton button2, NavigationButton button3);
}
