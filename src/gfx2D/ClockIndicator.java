package gfx2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import contact.BallPlayerContact;
import entities.creatures.Ball;
import game2d.Handler;



public class ClockIndicator {
	private Handler handler;
	private BallPlayerContact ballVsPlayer1, ballVsPlayer2;
	private Ball ball;
	private Graphics2D g2d;
	private Path2D path = new Path2D.Float();
	private BufferedImage clock, clockHand, clockFace, whiteFace, blueFace, redFace;
	private int x, y, X, Y, width, height;
	private double handAngle, alpha, beta, R, a, ak, bl, bm, bk, timeLimit, ticksPassed, startingPoint;
	private boolean hasBallP1, hadBallP1, hasBallP2, hadBallP2, isCountedP1, isCountedP2, wasCountedP1, wasCountedP2, ballInBox1, ballInBox2;
	
	
	public ClockIndicator(Handler handler, BallPlayerContact ballVsPlayer1, BallPlayerContact ballVsPlayer2, BufferedImage clock, BufferedImage clockHand, BufferedImage clockFace, BufferedImage whiteFace, BufferedImage blueFace, BufferedImage redFace, int x, int y, int width, int height){
		this.handler = handler;
		this.ballVsPlayer1=ballVsPlayer1;
		this.ballVsPlayer2=ballVsPlayer2;
		this.clock = clock; 
		this.clockHand =  clockHand;
		this.clockFace = clockFace;
		this.whiteFace = whiteFace;
		this.blueFace = blueFace;
		this.redFace = redFace;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.ball=ballVsPlayer1.getBall();
		timeLimit=30*handler.getFPS();
		startingPoint=10;
		ticksPassed=startingPoint*handler.getFPS();
	}	
	
	
	public void tick(){
		hadBallP1 = hasBallP1;
		hasBallP1 = ballVsPlayer1.getPlayer().getHasABall();
		hadBallP2 = hasBallP2;
		hasBallP2 = ballVsPlayer2.getPlayer().getHasABall();
		if(ball.getX()+ball.getRadius()>(handler.getWidth()*350/1000)&&(ball.getX()+ball.getRadius()<(handler.getWidth()*649/1000))) {
		if(ball.getY()+ball.getRadius()>(handler.getHeight()*87/1000)&&(ball.getY()+ball.getRadius()<(handler.getHeight()*258/1000))){
			ballInBox2 = true;}}
		if(ball.getX()+ball.getRadius()<(handler.getWidth()*350/1000)||(ball.getX()+ball.getRadius()>(handler.getWidth()*649/1000))) ballInBox2 = false;
		if(ball.getY()+ball.getRadius()<(handler.getHeight()*87/1000)||(ball.getY()+ball.getRadius()>(handler.getHeight()*258/1000))) ballInBox2 = false;
		
		if(ball.getX()+ball.getRadius()>(handler.getWidth()*351/1000)&&(ball.getX()+ball.getRadius()<(handler.getWidth()*649/1000))) {
		if(ball.getY()+ball.getRadius()>(handler.getHeight()*742/1000)&&(ball.getY()+ball.getRadius()<(handler.getHeight()*912/1000))){
			ballInBox1 = true;}}
		if(ball.getX()+ball.getRadius()<(handler.getWidth()*351/1000)||(ball.getX()+ball.getRadius()>(handler.getWidth()*649/1000)))ballInBox1 = false;
		if(ball.getY()+ball.getRadius()<(handler.getHeight()*742/1000)||(ball.getY()+ball.getRadius()>(handler.getHeight()*912/1000)))ballInBox1 = false;
			
		
		
		//initialize: first player to be counted
		if ((isCountedP1==false) && (isCountedP2==false)){
			if (hasBallP1) isCountedP1=true;
			if (hasBallP2) isCountedP2=true;}
		
		//mark previously and currently counted player
		if ((isCountedP1) || (isCountedP2)){
		wasCountedP1 = isCountedP1; 
		wasCountedP2 = isCountedP2;}
		
		if ((hasBallP1)||((ball.getV()<0.25)&&(ballVsPlayer1.getBall().getY()+ball.getRadius()/2>(handler.getHeight()/2+5)))){isCountedP1=true;isCountedP2=false;}
		if ((hasBallP2)||((ball.getV()<0.25)&&ballVsPlayer2.getBall().getY()+ball.getRadius()/2<(handler.getHeight()/2-5))){isCountedP1=false;isCountedP2=true;}
		//System.out.println(isCountedP1+","+isCountedP2+","+ticksPassed+","+ball.getV());
		
		//countdown starts
		if ((wasCountedP1!=isCountedP1)||(wasCountedP2!=isCountedP2))ticksPassed=startingPoint*handler.getFPS();//if counted player switched
		
		//countdown restarts
		if (isCountedP1&&ballInBox2)ticksPassed=startingPoint*handler.getFPS();
		if (isCountedP2&&ballInBox1)ticksPassed=startingPoint*handler.getFPS();
		
		//countdown continues
		if ((isCountedP1)||(isCountedP2))ticksPassed++;
		//countdown ends
		if (ticksPassed>=timeLimit){
			if (isCountedP1) ballForPlayer2();
			if (isCountedP2) ballForPlayer1();
			ticksPassed=startingPoint*handler.getFPS();
		}
	
	}
	
	public void render(Graphics2D g2d){
		//whiteFace
		if ((isCountedP1==false)&&(isCountedP2==false))g2d.drawImage(whiteFace, x, y+20, width, height*747/1000, null);
		//blueFace
		if (isCountedP1) g2d.drawImage(blueFace, x, y+20, width, height*747/1000, null);
		//redFace
		if (isCountedP2) g2d.drawImage(redFace, x, y+20, width, height*747/1000, null);
		//clockFace
		g2d.setClip(calculatePath());
		g2d.drawImage(clockFace, x+width/2, y+height/5, width/2, height*747/1000, null);
		g2d.setClip(null);
		path.reset();
		//clock
		g2d.drawImage(clock, x, y, width, height, null);
		//clockHand
		handAngle = ticksPassed*(Math.PI)/timeLimit;
		g2d.rotate(-handAngle, x+width*500/1000, y+height*594/1000);
		g2d.drawImage(clockHand, x+width*427/1000, y+height*541/1000, width*159/1000, height*335/1000, null);
		g2d.rotate(handAngle, x+width*500/1000, y+height*594/1000);	
	}
	
		
	public Path2D calculatePath(){
		R = height*747/1000/2;
		X = (x+width/2);
		Y = (y+height*594/1000);
		alpha = handAngle;//+Math.PI/2;
		if (alpha==0)alpha=0.5*Math.PI/180;
		
		beta = Math.PI-alpha;
		a=Math.tan(-Math.PI/2-beta/2);
		ak=-(1/a);
		bl=Y+R*Math.cos(alpha)-a*(X+R*Math.sin(alpha));
		bm=Y-R-a*X;
		bk=Y+R*Math.cos(alpha+beta/2)-ak*(X+R*Math.sin(alpha+beta/2));
		
		//System.out.println("alpha="+(alpha*180/Math.PI)+", a="+a+", ak="+ak+", bl="+bl+", bm="+bm+", bk="+bk);
		path.moveTo(X, Y); //point A, center of the clock face
		//System.out.println("A=("+X+","+Y+")"); 
		path.lineTo(X,Y-R); //point C
		//System.out.println("C=("+(X)+","+(Y-R)+")");
		path.lineTo(((bm-bk)/(ak-a)+100),(ak*((bm-bk)/(ak-a))+bk)); //point C2
		//System.out.println("C2=("+((bm-bk)/(ak-a))+","+(ak*((bm-bk)/(ak-a))+bk)+")");
		path.lineTo(((bl-bk)/(ak-a)+100),(ak*((bl-bk)/(ak-a))+bk)); //point B2
		//System.out.println("B2=("+(((bl-bk)/(ak-a)))+","+(ak*((bl-bk)/(ak-a))+bk)+")");
		path.lineTo(X+R*Math.sin(alpha),Y+R*Math.cos(alpha));; //point B
		//System.out.println("B=("+(X+R*Math.sin(alpha))+","+(Y+R*Math.cos(alpha))+")");
		path.closePath();
		return path;	
	}
	
	public void ballForPlayer1(){
		ball.setX(handler.getWidth()/2-(Ball.DefBallWidth/2));
		ball.setY(handler.getHeight()*4/5-(Ball.DefBallHeight/2));
		ball.setTX(ball.getX()/ball.getRatio());
		ball.setTY(ball.getY()/ball.getRatio());
		ball.setXPrev2(ball.getX()/ball.getRatio());
		ball.setYPrev2(ball.getY()/ball.getRatio());
	}
	public void ballForPlayer2(){
		ball.setX(handler.getWidth()/2-(Ball.DefBallWidth/2));
		ball.setY(handler.getHeight()*1/5-(Ball.DefBallHeight/2));
		ball.setTX(ball.getX()/ball.getRatio());
		ball.setTY(ball.getY()/ball.getRatio());
		ball.setXPrev2(ball.getX()/ball.getRatio());
		ball.setYPrev2(ball.getY()/ball.getRatio());
	}
	
}
