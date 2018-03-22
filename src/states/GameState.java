package states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import contact.BallFlatContact;
import contact.BallOrbContact;
import contact.BallPlayerContact;
import contact.PlayerFlatContact;
import contact.PlayerVsOrb;
import entities.Picture;
import entities.creatures.Ball;
import entities.creatures.Player;
import game2d.Handler;
import game2d.ui.ClickListener;
import game2d.ui.UIImageButton;
import game2d.ui.UIManager;
import gfx.Animation;
import gfx.Assets;
import gfx2D.ClockIndicator;
import gfx2D.ShotIndicator;
import gfx2D.staminaIndicator;
import mathToolBoxKajder.MyMath;


public class GameState extends State {


	private Player player1, player2, player;
	private Ball ball;
	private Picture goal1, goal2, pitch, centerLine, centerPoint, postBL, postBR, postUL, postUR, horWall_U, horWall_B, verWall_L, verWall_R, horNet_U, horNet_B, verNet_UL, verNet_UR, verNet_BL,verNet_BR;
	private BallOrbContact ballVsPostBL,ballVsPostBR,ballVsPostUL,ballVsPostUR;
	private BallPlayerContact ballVsPlayer1,ballVsPlayer2;
	private PlayerVsOrb player1VsPostBL,player1VsPostBR,player2VsPostUL,player2VsPostUR;
	private BallFlatContact ballVsHorWall_U, ballVsHorWall_B, ballVsVerWall_L, ballVsVerWall_R, ballVsHorNet_U, ballVsHorNet_B, ballVsVerNet_UL, ballVsVerNet_UR, ballVsVerNet_BL, ballVsVerNet_BR;
	private PlayerFlatContact player1VsHorWall_B, player1VsVerWall_L, player1VsVerWall_R, player1VsHorNet_B, player1VsVerNet_BL, player1VsVerNet_BR;
	private PlayerFlatContact player2VsHorWall_U, player2VsVerWall_L, player2VsVerWall_R, player2VsHorNet_U, player2VsVerNet_UL, player2VsVerNet_UR;
	private ShotIndicator shotIndicatorP1, shotIndicatorP2;
	private ClockIndicator clockIndicator;
	private staminaIndicator staminaIndicator1, staminaIndicator2;
	private Animation start;
	private UIManager uiManager;
	private boolean[] logicArray = new boolean[14];	
	private double Px, Py, pauseEnd=3.5*handler.getFPS();
	private int i,j,k,tick;
	
	public GameState(Handler handler){
		super(handler);
		uiManager=new UIManager(handler);
		goal1 = new Picture(handler, Assets.goal1,
				handler.getWidth()/2-Picture.goalWidth/2-1,handler.getHeight()*9/10+3,Picture.goalWidth,Picture.goalHeight);
		goal2 = new Picture(handler, Assets.goal2,
				handler.getWidth()/2-Picture.goalWidth/2-1,handler.getHeight()*1/10-49,Picture.goalWidth,Picture.goalHeight);
		postBL = new Picture(handler, Assets.postBL,
				handler.getWidth()/2-Picture.goalWidth*40/100,handler.getHeight()*904/1000,2*Picture.postRadius,2*Picture.postRadius);
		postBR = new Picture(handler, Assets.postBR,
				handler.getWidth()/2+Picture.goalWidth*355/1000,handler.getHeight()*904/1000,2*Picture.postRadius,2*Picture.postRadius);
		postUL = new Picture(handler, Assets.postUL,
				handler.getWidth()/2-Picture.goalWidth*40/100,handler.getHeight()*85/1000,2*Picture.postRadius,2*Picture.postRadius);
		postUR = new Picture(handler, Assets.postUR,
				handler.getWidth()/2+Picture.goalWidth*355/1000,handler.getHeight()*85/1000,2*Picture.postRadius,2*Picture.postRadius);		
		player1 = new Player(handler, Assets.player1,
				handler.getWidth()/2 - Player.DefPlayerWidth/2,handler.getHeight()*87/100-Player.DefPlayerHeight/2,1,goal1);
		player2 = new Player(handler, Assets.player2, 
				handler.getWidth()/2- Player.DefPlayerWidth/2,handler.getHeight()*13/100-Player.DefPlayerHeight/2,2,goal2);
		ball = new Ball(handler, Assets.ball,handler.getWidth()/2+handler.getPitchWidth()/2-handler.getWidth()*1238/10000-Ball.DefBallWidth/2,handler.getHeight()*1/2-Ball.DefBallHeight/2); //+355
		pitch = new Picture(handler, Assets.playground,0,0,handler.getWidth(),handler.getHeight());

		centerLine = new Picture(handler, Assets.centerLine, (handler.getWidth()/2-handler.getPitchWidth()*3/8), handler.getHeight()/2-handler.getWidth()*62/20000, handler.getPitchWidth()*3/4,handler.getWidth()*62/10000);
		centerPoint = new Picture(handler, Assets.centerPoint, (handler.getWidth()/2-handler.getWidth()*62/20000), (handler.getHeight()/2-handler.getWidth()*62/20000),handler.getWidth()*62/10000,handler.getWidth()*62/10000);
		horWall_U = new Picture(handler, Assets.horWall,0,5,handler.getWidth(),20);
		horWall_B = new Picture(handler, Assets.horWall,0,handler.getHeight()-25, handler.getWidth(),20);
		verWall_L = new Picture(handler, Assets.verWall_L, handler.getWidth()/2-handler.getPitchWidth()/2+handler.getWidth()*1031/10000, 0, 20, handler.getHeight()); //100
		verWall_R = new Picture(handler, Assets.verWall_R, handler.getWidth()/2+handler.getPitchWidth()/2-handler.getWidth()*1238/10000, 0, 20, handler.getHeight());
		
		horNet_U = new Picture(handler, Assets.horNet, handler.getWidth()/2-Picture.goalWidth*38/100,handler.getHeight()*48/1000,Picture.goalWidth*4/5-10,4);
		horNet_B = new Picture(handler, Assets.horNet, handler.getWidth()/2-Picture.goalWidth*38/100,handler.getHeight()*94/100+2,Picture.goalWidth*4/5-10,4);
		verNet_UL = new Picture(handler, Assets.verNet, handler.getWidth()/2-Picture.goalWidth*4/10+1,10,4,41);
		verNet_UR = new Picture(handler, Assets.verNet, handler.getWidth()/2+Picture.goalWidth*4/10-7,10,4,41);
		verNet_BL = new Picture(handler, Assets.verNet, handler.getWidth()/2-Picture.goalWidth*4/10+1,handler.getHeight()*90/100+10,4,41);
		verNet_BR = new Picture(handler, Assets.verNet, handler.getWidth()/2+Picture.goalWidth*4/10-7,handler.getHeight()*90/100+10,4,41);
		

		//contacts
		ballVsPlayer1 = new BallPlayerContact(ball,player1,0.6, handler);
		ballVsPlayer2 = new BallPlayerContact(ball,player2,0.6, handler);
		
		
		shotIndicatorP1 = new ShotIndicator(handler, ballVsPlayer1, Assets.ballIndicatorBig, Assets.blueShoeTrans, Assets.blueHalo, Assets.blueShoe, Assets.gradientBlue, 50, 520);
		shotIndicatorP2 = new ShotIndicator(handler, ballVsPlayer2, Assets.ballIndicatorBig, Assets.redShoeTrans, Assets.redHalo, Assets.redShoe, Assets.gradientRed, 50, 80);
		clockIndicator = new ClockIndicator(handler, ballVsPlayer1, ballVsPlayer2, Assets.clock, Assets.clockHand, Assets.clockFace, Assets.whiteFace, Assets.blueFace, Assets.redFace, 885, handler.getHeight()/2-50,69,85);
		staminaIndicator1 = new staminaIndicator(player1, handler, Assets.whiteBar, Assets.blueBar, handler.getWidth()/100, handler.getHeight()*560/1000,80,15);
		staminaIndicator2 = new staminaIndicator(player2, handler, Assets.whiteBar, Assets.redBar, handler.getWidth()/100, handler.getHeight()*410/1000,80,15);
		
		
		ballVsPostBL = new BallOrbContact(ball,postBL,0.4);
		ballVsPostBR = new BallOrbContact(ball,postBR,0.4);
		ballVsPostUL = new BallOrbContact(ball,postUL,0.4);
		ballVsPostUR = new BallOrbContact(ball,postUR,0.4);
		
		ballVsHorWall_U = new BallFlatContact(ball,horWall_U, 0.15, true);
		ballVsHorWall_B = new BallFlatContact(ball,horWall_B, 0.15, true);
		ballVsVerWall_L = new BallFlatContact(ball,verWall_L, 0.15, false);
		ballVsVerWall_R = new BallFlatContact(ball,verWall_R, 0.15, false);
		
		ballVsHorNet_U = new BallFlatContact(ball,horNet_U, 0.85, true);
		ballVsHorNet_B = new BallFlatContact(ball,horNet_B, 0.85, true);
		ballVsVerNet_UL = new BallFlatContact(ball,verNet_UL, 0.85, false);
		ballVsVerNet_UR = new BallFlatContact(ball,verNet_UR, 0.85, false);
		ballVsVerNet_BL = new BallFlatContact(ball,verNet_BL, 0.85, false);
		ballVsVerNet_BR = new BallFlatContact(ball,verNet_BR, 0.85, false);
		
		player1VsPostBL = new PlayerVsOrb(player1,postBL);
		player1VsPostBR = new PlayerVsOrb(player1,postBR);
		player2VsPostUL = new PlayerVsOrb(player2,postUL);
		player2VsPostUR = new PlayerVsOrb(player2,postUR);
		
		player1VsHorWall_B = new PlayerFlatContact(player1,horWall_B,true);
		player1VsVerWall_L = new PlayerFlatContact(player1,verWall_L,false);
		player1VsVerWall_R= new PlayerFlatContact(player1,verWall_R,false);
		player1VsHorNet_B= new PlayerFlatContact(player1,horNet_B,true);
		player1VsVerNet_BL= new PlayerFlatContact(player1,verNet_BL,false);
		player1VsVerNet_BR= new PlayerFlatContact(player1,verNet_BR,false);
		
		player2VsHorWall_U= new PlayerFlatContact(player2,horWall_U,true);
		player2VsVerWall_L= new PlayerFlatContact(player2,verWall_L,false);
		player2VsVerWall_R= new PlayerFlatContact(player2,verWall_R,false);
		player2VsHorNet_U= new PlayerFlatContact(player2,horNet_U,true);
		player2VsVerNet_UL= new PlayerFlatContact(player2,verNet_UL,false);
		player2VsVerNet_UR= new PlayerFlatContact(player2,verNet_UR,false);
		
		// back arrow button
		uiManager.addObject(new UIImageButton(945, 5, 20, 20, Assets.btn_arrowUP, new ClickListener(){
			public void onClick(){
				endMatch();
				handler.getGame().getDisplay().getFrame().validate();
			}
		}));
	}

	public void endMatch(){
		State.setState(handler.getGame().menuState);
		handler.getMouseManager().setUIManager(State.getState().getUIManager());
	}

	@Override
	public void tick() {
		uiManager.tick();
		if (tick<=pauseEnd)tick++;
		if ((tick>=pauseEnd)||(tick<=1)){
		ballVsPlayer1.tick();
		ballVsPlayer2.tick();
		
		ballVsPostBL.tick();
		ballVsPostBR.tick();
		ballVsPostUL.tick();
		ballVsPostUR.tick();
		
		// ball-wall tylko je�eli pi�ka znajduje si� poza pewnymi granicami( �eby oszcz�dzi� moc obliczeniow�)
		//do dopisania po ustaleniu po�o�enia �cian
		ballVsHorWall_U.tick();
		ballVsHorWall_B.tick();
		ballVsVerWall_L.tick();
		ballVsVerWall_R.tick();

		ballVsHorNet_U.tick();
		ballVsHorNet_B.tick();
		ballVsVerNet_UL.tick();
		ballVsVerNet_UR.tick();
		ballVsVerNet_BL.tick();
		ballVsVerNet_BR.tick();
		
		player1VsPostBL.tick();
		player1VsPostBR.tick();
		player2VsPostUR.tick();
		player2VsPostUL.tick();
		
		player1VsHorWall_B.tick();
		player1VsVerWall_L.tick();
		player1VsVerWall_R.tick();
		player1VsHorNet_B.tick();
		player1VsVerNet_BL.tick();
		player1VsVerNet_BR.tick();
	
		player2VsHorWall_U.tick();
		player2VsVerWall_L.tick();
		player2VsVerWall_R.tick();
		player2VsHorNet_U.tick();
		player2VsVerNet_UL.tick();
		player2VsVerNet_UR.tick();
		
		player1.tick();
		player2.tick();
		clockIndicator.tick();
		staminaIndicator1.tick();
		staminaIndicator2.tick();
		isDribbleAllowed();
		//System.out.println("is dribble allowed? answer: "+ball.getDribbleAllowed());
		ball.tick();
		ballHedge();
		manageScore();
		
		player1.setXMoveAllowedFlat(player1VsHorWall_B.getMoveXAllowedFlat()&&player1VsVerWall_L.getMoveXAllowedFlat()&&player1VsVerWall_R.getMoveXAllowedFlat()&&player1VsHorNet_B.getMoveXAllowedFlat()&&player1VsVerNet_BL.getMoveXAllowedFlat()&&player1VsVerNet_BR.getMoveXAllowedFlat());
		player1.setYMoveAllowedFlat(player1VsHorWall_B.getMoveYAllowedFlat()&&player1VsVerWall_L.getMoveYAllowedFlat()&&player1VsVerWall_R.getMoveYAllowedFlat()&&player1VsHorNet_B.getMoveYAllowedFlat()&&player1VsVerNet_BL.getMoveYAllowedFlat()&&player1VsVerNet_BR.getMoveYAllowedFlat());
		player2.setXMoveAllowedFlat(player2VsHorWall_U.getMoveXAllowedFlat()&&player2VsVerWall_L.getMoveXAllowedFlat()&&player2VsVerWall_R.getMoveXAllowedFlat()&&player2VsHorNet_U.getMoveXAllowedFlat()&&player2VsVerNet_UL.getMoveXAllowedFlat()&&player2VsVerNet_UR.getMoveXAllowedFlat());
		player2.setYMoveAllowedFlat(player2VsHorWall_U.getMoveYAllowedFlat()&&player2VsVerWall_L.getMoveYAllowedFlat()&&player2VsVerWall_R.getMoveYAllowedFlat()&&player2VsHorNet_U.getMoveYAllowedFlat()&&player2VsVerNet_UL.getMoveYAllowedFlat()&&player2VsVerNet_UR.getMoveYAllowedFlat());
	/*
		ball.setDribbleAllowed(ballVsPostBL.getContactAllowed()&&ballVsPostBR.getContactAllowed()&&ballVsPostUL.getContactAllowed()&&ballVsPostUR.getContactAllowed()
		&&ballVsHorNet_U.getContactAllowed()&&ballVsHorNet_B.getContactAllowed()&&ballVsVerNet_UL.getContactAllowed()&&ballVsVerNet_UR.getContactAllowed()&&ballVsVerNet_BL.getContactAllowed()&&ballVsVerNet_BR.getContactAllowed()
		&&ballVsHorWall_U.getContactAllowed()&&ballVsHorWall_B.getContactAllowed()&&ballVsVerWall_L.getContactAllowed()&&ballVsVerWall_R.getContactAllowed());
	*/
		}}

		public void ballHedge(){
		if ((ball.getCenterY()-ball.getRadius())>(horWall_B.getCenterY()+horWall_B.getHeight()/2)) ballForPlayer1();//ball behind bottom wall
		if ((horWall_U.getCenterY()-horWall_U.getHeight()/2)>(ball.getCenterY()+ball.getRadius())) ballForPlayer2();//ball behind upper wall
		
		if ((ball.getCenterX()-ball.getRadius())>=(verWall_R.getCenterX()+verWall_R.getWidth()/2)){//ball behind right wall
			if ((ball.getCenterY())>=(handler.getHeight()/2)) {ballForPlayer1();}else{ballForPlayer2();}	
		}
		if ((ball.getCenterX()+ball.getRadius())<=(verWall_L.getCenterX()-verWall_L.getWidth()/2)){//ball behind left wall
			if ((ball.getCenterY())>=(handler.getHeight()/2)) {ballForPlayer1();}else{ballForPlayer2();}	
		}
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
	
	public void isDribbleAllowed(){
		if (player1.getHasABall()) player=player1;
		if (player2.getHasABall()) player=player2;
		if ((player1.getHasABall()==false)&&(player2.getHasABall()==false)) return ;
		Px = (player.getCenterX()+player.getRadius()*Math.sin(player.getPlayerAngle()));
		Py = (player.getCenterY()+player.getRadius()*Math.cos(player.getPlayerAngle()));
		for (j=0;j<14;j++){logicArray[j]=true;}
		ball.setDribbleAllowed(true);
		//posts
		if (MyMath.distanceTwoPoints(Px,Py,postBL.getCenterX(),postBL.getCenterY())<(ball.getRadius()+postBL.getRadius())) logicArray[0]=false;
		if (MyMath.distanceTwoPoints(Px,Py,postBR.getCenterX(),postBR.getCenterY())<(ball.getRadius()+postBR.getRadius())) logicArray[1]=false;
		if (MyMath.distanceTwoPoints(Px,Py,postUL.getCenterX(),postUL.getCenterY())<(ball.getRadius()+postUL.getRadius())) logicArray[2]=false;
		if (MyMath.distanceTwoPoints(Px,Py,postUR.getCenterX(),postUR.getCenterY())<(ball.getRadius()+postUR.getRadius())) logicArray[3]=false;
		//walls
		if (((Math.abs(Px-horWall_U.getCenterX())<(ball.getRadius()+horWall_U.getWidth()/2)))&&((Math.abs(Py-horWall_U.getCenterY())<(ball.getRadius()+horWall_U.getHeight()/2)))) logicArray[4]=false;
		if (((Math.abs(Px-horWall_B.getCenterX())<(ball.getRadius()+horWall_B.getWidth()/2)))&&((Math.abs(Py-horWall_B.getCenterY())<(ball.getRadius()+horWall_B.getHeight()/2)))) logicArray[5]=false;
		if (((Math.abs(Px-verWall_L.getCenterX())<(ball.getRadius()+verWall_L.getWidth()/2)))&&((Math.abs(Py-verWall_L.getCenterY())<(ball.getRadius()+verWall_L.getHeight()/2)))) logicArray[6]=false;
		if (((Math.abs(Px-verWall_R.getCenterX())<(ball.getRadius()+verWall_R.getWidth()/2)))&&((Math.abs(Py-verWall_R.getCenterY())<(ball.getRadius()+verWall_R.getHeight()/2)))) logicArray[7]=false;
		//nets
		if (((Math.abs(Px-horNet_U.getCenterX())<(ball.getRadius()+horNet_U.getWidth()/2)))&&((Math.abs(Py-horNet_U.getCenterY())<(ball.getRadius()+horNet_U.getHeight()/2)))) logicArray[8]=false;
		if (((Math.abs(Px-horNet_B.getCenterX())<(ball.getRadius()+horNet_B.getWidth()/2)))&&((Math.abs(Py-horNet_B.getCenterY())<(ball.getRadius()+horNet_B.getHeight()/2)))) logicArray[9]=false;
		if (((Math.abs(Px-verNet_UL.getCenterX())<(ball.getRadius()+verNet_UL.getWidth()/2)))&&((Math.abs(Py-verNet_UL.getCenterY())<(ball.getRadius()+verNet_UL.getHeight()/2)))) logicArray[10]=false;
		if (((Math.abs(Px-verNet_UR.getCenterX())<(ball.getRadius()+verNet_UR.getWidth()/2)))&&((Math.abs(Py-verNet_UR.getCenterY())<(ball.getRadius()+verNet_UR.getHeight()/2)))) logicArray[11]=false;
		if (((Math.abs(Px-verNet_BL.getCenterX())<(ball.getRadius()+verNet_BL.getWidth()/2)))&&((Math.abs(Py-verNet_BL.getCenterY())<(ball.getRadius()+verNet_BL.getHeight()/2)))) logicArray[12]=false;
		if (((Math.abs(Px-verNet_BR.getCenterX())<(ball.getRadius()+verNet_BR.getWidth()/2)))&&((Math.abs(Py-verNet_BR.getCenterY())<(ball.getRadius()+verNet_BR.getHeight()/2)))) logicArray[13]=false;
		//check if all true
		for (boolean b: logicArray) {if(b==false) ball.setDribbleAllowed(false);}
		//for (j=0;j<14;j++){System.out.println(logicArray[j]+" - "+j);}
	}

	public void manageScore(){
		if (((ball.getCenterY()<(postUL.getCenterY()-ball.getHeight()/2-2)) && (ball.getCenterX()>(postUL.getCenterX()+3)) 
				&& (ball.getCenterX()<(postUR.getCenterX()-3)) ) || (ball.getScored2()==true))
		{ //player1 has scored a goal
			player2.setMayCatch(false);
			if (i==0) {
				ball.setScored2(true);
				Toolkit.getDefaultToolkit().beep();
			}
			i++;
			if ((i>=1.5*handler.getFPS())&&(ball.getScored2()==true)) //pause for 1.5 second
			{
			player1.setScore(player1.getScore()+1);
			player2.setMayCatch(true);
			ballForPlayer2();	
			System.out.println("goal for Player1!");
			i=0;
			ball.setScored2(false);
			System.out.println("ball x: "+ball.getX()+", ball y: "+ball.getY());
			System.out.println("ball center x: "+(ball.getTX()*ball.getRatio()+ball.getRadius())+", ball center y: "+(ball.getTY()*ball.getRatio()+ball.getRadius()));
			}
		}
		if (((ball.getCenterY()>(postBL.getCenterY()+ball.getHeight()/2+1)) && (ball.getCenterX()>(postBL.getCenterX()+3)) 
				&& (ball.getCenterX()<(postBR.getCenterX()-3)))||(ball.getScored1()==true))
		{ //player2 has scored a goal
			player1.setMayCatch(false);
			if (i==0){
				ball.setScored1(true);
				Toolkit.getDefaultToolkit().beep();
			}
			i++;
			if ((i>=1.5*handler.getFPS())&&(ball.getScored1()==true)) //pause for 1 second
			{
			player2.setScore(player2.getScore()+1);
			player1.setMayCatch(true);
			ballForPlayer1();
			System.out.println("goal for Player2!");
			i=0;
			ball.setScored1(false);
			System.out.println("ball x: "+ball.getX()+", ball y: "+ball.getY());
			System.out.println("ball center x: "+(ball.getTX()*ball.getRatio()+ball.getRadius())+", ball center y: "+(ball.getTY()*ball.getRatio()+ball.getRadius()));

			}
		}
	}
	@Override
	public void render(Graphics g) {
		if ((tick>0)&&(tick<=handler.getFPS())) g.drawImage(Assets.startBoard[0], handler.getWidth()/2-210/2, handler.getHeight()/2-144/2, 210, 144, null);
		if ((tick>(handler.getFPS()))&&(tick<=2*(handler.getFPS())))  g.drawImage(Assets.startBoard[1], handler.getWidth()/2-210/2, handler.getHeight()/2-144/2, 210, 144, null);
		if ((tick>2*(handler.getFPS()))&&(tick<=3*(handler.getFPS())))  g.drawImage(Assets.startBoard[2], handler.getWidth()/2-210/2, handler.getHeight()/2-144/2, 210, 144, null);
		if ((tick>3*(handler.getFPS()))&&(tick<=3.5*(handler.getFPS())))  g.drawImage(Assets.startBoard[3], handler.getWidth()/2-210/2, handler.getHeight()/2-144/2, 210, 144, null);
		
		if ((tick>=pauseEnd)||(tick<=1)){
		pitch.render(g);
		centerLine.render(g);
		centerPoint.render(g);
		ball.render(g);

		verWall_L.render(g); 
		verWall_R.render(g);
		horWall_U.render(g);
		horWall_B.render(g);
		
		player1.render(g);
		player2.render(g);

		goal1.render(g);
		goal2.render(g);
		postBL.render(g);
		postBR.render(g);
		postUL.render(g);
		postUR.render(g);
		
		horNet_U.render(g);
		horNet_B.render(g);
		verNet_UL.render(g);
		verNet_UR.render(g);
		verNet_BL.render(g);
		verNet_BR.render(g);

		ballVsPlayer1.render(g);
		ballVsPlayer2.render(g);
		
//score indicators
		//player1
		if (player1.getScore()==0) g.drawImage(Assets.balls0, handler.getWidth()/2-80/2, handler.getHeight()*96/100, 80, 16, null);
		if (player1.getScore()==1) g.drawImage(Assets.balls1, handler.getWidth()/2-80/2, handler.getHeight()*96/100, 80, 16, null);
		if (player1.getScore()==2) g.drawImage(Assets.balls2, handler.getWidth()/2-80/2, handler.getHeight()*96/100, 80, 16, null);
		if (player1.getScore()==3) g.drawImage(Assets.balls3, handler.getWidth()/2-80/2, handler.getHeight()*96/100, 80, 16, null);
		if (player1.getScore()==4) g.drawImage(Assets.balls4, handler.getWidth()/2-80/2, handler.getHeight()*96/100, 80, 16, null);
		if (player1.getScore()>=5) g.drawImage(Assets.balls5, handler.getWidth()/2-80/2, handler.getHeight()*96/100, 80, 16, null);
		if ((player1.getScore()>=5)&&(player2.getScore()<5)) g.drawImage(Assets.cup, handler.getWidth()/2-50/2, handler.getHeight()*745/1000, 50, 55, null);
		if (player1.getScore()>=5) {
			k++;
			if (k>=(3*handler.getFPS())) endMatch();
		}

		//player2
		if (player2.getScore()==0) g.drawImage(Assets.balls0, handler.getWidth()/2-80/2, handler.getHeight()*12/1000, 80, 16, null);
		if (player2.getScore()==1) g.drawImage(Assets.balls1, handler.getWidth()/2-80/2, handler.getHeight()*12/1000, 80, 16, null);
		if (player2.getScore()==2) g.drawImage(Assets.balls2, handler.getWidth()/2-80/2, handler.getHeight()*12/1000, 80, 16, null);
		if (player2.getScore()==3) g.drawImage(Assets.balls3, handler.getWidth()/2-80/2, handler.getHeight()*12/1000, 80, 16, null);
		if (player2.getScore()==4) g.drawImage(Assets.balls4, handler.getWidth()/2-80/2, handler.getHeight()*12/1000, 80, 16, null);
		if (player2.getScore()>=5) g.drawImage(Assets.balls5, handler.getWidth()/2-80/2, handler.getHeight()*12/1000, 80, 16, null);
		if ((player2.getScore()>=5)&&(player1.getScore()<5)) g.drawImage(Assets.cup, handler.getWidth()/2-50/2, handler.getHeight()*16/100, 50, 55, null);
		if (player2.getScore()>=5) {
				k++;
				if (k>=(3*handler.getFPS())) endMatch();
		}
		uiManager.render(g);
	}}
	public void renderG2D(Graphics2D g2d){
		if ((tick>=pauseEnd)||(tick<=1)){
		shotIndicatorP1.tickNrender(g2d);
		shotIndicatorP2.tickNrender(g2d);	
		clockIndicator.render(g2d);
		staminaIndicator1.render(g2d);
		staminaIndicator2.render(g2d);
	}}
	public UIManager getUIManager(){
		return uiManager;
	}
}