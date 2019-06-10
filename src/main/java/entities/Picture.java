package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game2d.Handler;

public class Picture extends Entity{

	
	private BufferedImage icon;
	private int width;
	public static int goalWidth = 170, goalHeight = 45, postRadius = 3;
	
	
	public Picture(Handler handler, BufferedImage icon, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		this.icon = icon;
		this.width = width;
	}

	public int getGoalWidth(){
		return goalWidth;
	}
	
	public int getGoalHeight(){
		return goalHeight;
	}
	
	public float getRadius(){
		return postRadius;
	}
	
	public void tick(){
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(icon, (int) (x), (int) (y),  width, height, null);
		
	}
	
}
