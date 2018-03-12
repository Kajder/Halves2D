package entities;

import java.awt.Graphics;

import game2d.Handler;

public abstract class Entity {

	protected Handler handler;
	protected double x,y;
	protected int width, height;
	protected float radius;
	protected boolean hasAball, mayCatch=false;
	protected int mode=0;
	
	public Entity(Handler handler, double x, double y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		
	}
	
	public double getCenterX(){
		return x+width/2;
	}
	
	public double getCenterY(){
		return y+height/2;
	}
	
	public float getRadius(){
		//radius = (float) (0.5*Math.sqrt(width*width+height*height));
		radius = (float) (0.25*(width+height));
		return radius;
		
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public void setHasABall(boolean flag){hasAball = flag;}
	public boolean getHasABall(){return hasAball;}
	public int getMode(){return mode;}
	public boolean getMayCatch(){return mayCatch;}
	public void setMayCatch(boolean flag){mayCatch=flag;}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	
}
