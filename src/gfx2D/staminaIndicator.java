package gfx2D;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import entities.creatures.Ball;
import entities.creatures.Player;
import game2d.Handler;


public class staminaIndicator {
	private Handler handler;
	private Ball ball;
	private Graphics2D g2d;
	private Path2D path = new Path2D.Float();
	private BufferedImage whiteBar, colorBar;
	private Player player;
	private int x, y, width, height;
	private double stamina, staminaInitial, coef;

	public staminaIndicator(Player player, Handler handler, BufferedImage whiteBar, BufferedImage colorBar, int x, int y, int width, int height){
		this.handler = handler;
		this.player = player; 
		this.whiteBar =  whiteBar;
		this.colorBar = colorBar;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		staminaInitial = player.getStaminaInitial();
	}	
	
	public void tick(){
		stamina = player.getStamina();
		coef = width*(stamina/staminaInitial);
	}
	public void render(Graphics2D g2d){
		//white bar
		g2d.drawImage(whiteBar, x, y, width, height, null);
		//clockFace
		g2d.setClip(calculatePath());
		g2d.drawImage(colorBar, x, y, width, height, null);
		g2d.setClip(null);
		path.reset();
	}
	
		
	public Path2D calculatePath(){
		path.moveTo(x, y); 
		path.lineTo(x, y+height);
		path.lineTo(x+coef, y+height);
		path.lineTo(x+coef, y);
		path.closePath();
		return path;	
	}	
	
	
	
	
	
}
