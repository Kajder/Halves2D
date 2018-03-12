package entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Entity;
import game2d.Handler;

public abstract class Creature extends Entity{


	protected float speed;
	protected float xMove, yMove;
	protected BufferedImage icon;
	protected int leftBorder, rightBorder, upBorder, downBorder;

	private static final float DEFAULT_SPEED = 1.5f;

	
	public Creature(Handler handler, BufferedImage icon, double x, double y, int width, int height){
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		this.icon = icon;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(icon, (int) (x), (int) (y),  width, height, null);	
	}
	
	

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	
}
