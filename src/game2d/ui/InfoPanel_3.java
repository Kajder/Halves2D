package game2d.ui;

import game2d.Handler;
import game2d.ui.support.ColorPanel;
import game2d.ui.support.GBC;
import gfx.Assets;

import javax.swing.*;
import java.awt.*;


public class InfoPanel_3 extends SettingsPanel {
  private Handler handler;
  private JLabel photoLabel;

  public InfoPanel_3(Handler handler) {
    super(handler);
    this.handler = handler;

    photoLabel = new JLabel(new ImageIcon(Assets.gameController.getScaledInstance(900, 500, Image.SCALE_SMOOTH)));
    photoLabel.setPreferredSize(new Dimension(160, 220));
    photoLabel.setBackground(Color.DARK_GRAY);

    //POPULATING BOTTOM BAR
    bottomBar.add(photoLabel, new GBC(0, 0, 1, 1).setFill(1).setInsets(20, 20, 20, 20).setWeight(1, 1));
    labelTitle.setText("GAMEPAD CONTROL");
    addTopBottomPanels();
  }
}