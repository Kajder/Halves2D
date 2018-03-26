package display;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

import game2d.Game;
import game2d.Handler;
import game2d.ui.support.GBC;
import gfx.ImageLoader;

public class Display {

	private Handler handler;
private JFrame frame;
private MainPanel mainPanel;
private Canvas canvas;
private String title;
private int width, height, modifiedWidth, modifiedHeight;

public Display(Handler handler, String title, int width, int height){
	this.handler=handler;
	this.title = title;
	this.width=width;
	this.height=height;
createDisplay();
}

public void createDisplay(){
	frame = new JFrame();
	frame.setTitle(title);
	frame.setSize(width, height);
	//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	//frame.setUndecorated(true);
	//frame.setBackground(Color.DARK_GRAY);
	
	try{
	InputStream imgStream = frame.getClass().getResourceAsStream("/icon_rect.png");
	BufferedImage myImg = ImageIO.read(imgStream);
	frame.setIconImage(myImg);
	}catch (IOException exception)
    {
        exception.printStackTrace();
    }
	//frame.setIconImage(ImageLoader.loadImage("/textures/icon_rect.png"));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new GridBagLayout());
	frame.getContentPane().setBackground(Color.BLACK);
	frame.setResizable(true);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	frame.addMouseMotionListener(new MouseAdapter() {
		@Override
		public void mouseDragged(MouseEvent e) {
			super.mouseDragged(e);
			System.out.println("dragged");
		}
	});
//its amazing and simple solution thank you so much i did this dispose();
// 'setUndecorated(true); setExtendedState(JFrame.MAXIMIZED_BOTH); setVisible(true);'

	canvas = new Canvas();
	canvas.setPreferredSize(new Dimension(width, height));
	canvas.setMaximumSize(new Dimension(width, height));
	canvas.setMinimumSize(new Dimension(width, height));
	canvas.setFocusable(false);

	//mainPanel = new MainPanel(width, height);
	//frame.add(mainPanel);
	frame.add(canvas, new GBC(0,0,0,0).setAnchor(GridBagConstraints.CENTER));
	frame.pack();
	modifiedWidth=frame.getWidth();
	modifiedHeight=frame.getHeight();
	}

public Canvas getCanvas(){ return canvas; }
public JFrame getFrame(){
	return frame;
}
public MainPanel getPanel(){
		return mainPanel;
	}

    public int getModifiedHeight() {
        return modifiedHeight;
    }

    public int getModifiedWidth() {
        return modifiedWidth;
    }
}