package game2d.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class NavigationButton extends JButton {

    private Image newImage1, newImage2;
    private ImageIcon icon1, icon2;

    public NavigationButton(BufferedImage FirstImage, BufferedImage SecondImage, int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusPainted(false);
        newImage1 = FirstImage.getScaledInstance((int) (width * 0.8), (int) (height * 0.8), Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(newImage1);
        newImage2 = SecondImage.getScaledInstance((int) (width * 0.8), (int) (height * 0.8), Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(newImage2);
        setIcon(icon1);
        setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
        setBackground(Color.DARK_GRAY);

        setModel(new DefaultButtonModel() {
            @Override
            public boolean isPressed() {
                return false;
            }

            @Override
            public boolean isRollover() {
                return true;
            }

            @Override
            public void setRollover(boolean b) {
            }
        });
        getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (getModel().isRollover()) setIcon(icon2);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setIcon(icon2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseEntered(e);
                setIcon(icon1);
            }
        });
    }

    public void changeBackground() {
        if (getIcon() == icon1) {
            setIcon(icon2);
        } else {
            setIcon(icon1);
        }
    }
}