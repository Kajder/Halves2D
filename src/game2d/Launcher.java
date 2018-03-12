package game2d;

import java.awt.Toolkit;

import entities.Picture;

public class Launcher {
	
	public static void main (String args[]){
		Game game = new Game("Halves2D", 970, 600);
	game.start();		
	}
}
