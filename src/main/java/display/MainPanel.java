package display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel {
    private int width;
    private int height;
    private BufferedImage bufferedImage;


    public MainPanel(int width, int height){
        this.width=width;
        this.height=height;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        setBackground(Color.BLUE);
        setFocusable(false);
        //setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

    }

protected void paintComponent(Graphics g){
    super.paintComponent(g);
    if (bufferedImage != null) {
        g.drawImage(bufferedImage, 0, 0, null);
    }
}

    public BufferedImage getImage() {
        return bufferedImage;
    }
    public void setImage(BufferedImage image){
    bufferedImage=image;
    }
}
