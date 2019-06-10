package states;

import java.awt.Graphics;
import java.awt.Graphics2D;

import game2d.Handler;
import game2d.ui.UIManager;

public abstract class State {

	
	//States Manager (may be located in separate class)
	
	public static State currentState = null;
	public static void setState(State state){
		currentState = state;
	}
	
	public static State getState(){
		return currentState;
	}
	
	//CLASS
	protected Handler handler;
	public State (Handler handler){
		this.handler = handler;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void renderG2D(Graphics2D g2d);
	public abstract UIManager getUIManager();
}
