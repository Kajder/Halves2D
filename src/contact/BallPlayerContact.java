package contact;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import entities.creatures.Ball;
import entities.creatures.Player;
import game2d.Handler;
import gfx.Assets;
import mathToolBoxKajder.MyMath;
import mathToolBoxKajder.doublePair;
import states.TrainingState;

public class BallPlayerContact extends BallOrbContact{
private Ball ball;
private Player player;
private boolean contactAllowed=true, contactAllowed2=true, force, rotLeft, rotRight, dirLeft, dirRight, hasBallPrevious=true, firstTouch=false, showVelocity, updateVelocity;
Handler handler;
private double forceInd, directionInd, rotationInd, speed;
private boolean shotExecution;
private boolean shotCancelation, dropBallFlag;
private boolean forcePreviousState, thunderVisible;
private double newAngle, newXP2, newYP2, newW;
private double contactDur, ballMass, ballAngle, footMass, Vp, Vpx, Vpy, Vf, V, Vx, Vy, Vangle, Vb1, Vb2, Vb2x, Vb2y, Vb2NewAngle, playerAngle;
private double coefE;// e - ball-foot coefficient of restitution, to be defined by user (0-1, but actually 0.46-0.68)
private double firstTouchPeriod=1, thunderPeriod=2;
private int forceVal, firstTouchCounter, dropBallCounter, thunderCounter, ballIndicatorSize, ballIndicatorCorrection;
protected doublePair playerCenter = new doublePair(0,0);
protected doublePair ballCenter = new doublePair(0,0);
protected String velocity;
protected DecimalFormat formatter;

	public BallPlayerContact(Ball ball, Player player, double dumping, Handler handler) {
		super(ball, player, dumping);
		// TODO Auto-generated constructor stub
		
		this.ball = ball;
		this.player = player;
		this.handler = handler;
		contactDur = ball.getTk();
		ballMass = ball.getMass();
		coefE = handler.getParameters().getCustomParameters().get("restitutionCoef")/100;
		footMass = player.getFootMass();
		formatter = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
		formatter.setRoundingMode( RoundingMode.DOWN );
		velocity = formatter.format(0);
	}
	
	public void getInput(){
		adaptMode();

		if (player.getHasABall()==true && (forceInd<=(handler.getFPS()*ball.getforceMaxTime())) && (force)){ //
			forceInd++;

			if ((dirLeft)&&(directionInd>(-ball.getAngleMaxTime()*handler.getFPS())))directionInd--;
			if ((dirRight)&&(directionInd<(ball.getAngleMaxTime()*handler.getFPS())))directionInd++;
			if ((rotLeft)&&(rotationInd<(ball.getwMaxTime()*handler.getFPS())))rotationInd++;
			if ((rotRight)&&(rotationInd>(-ball.getwMaxTime()*handler.getFPS())))rotationInd--;
			if (shotCancelation)
				{
				forceInd = 0;
				directionInd = 0;
				rotationInd = 0;
				}
	}
		//preloaded shot
		if (player.getHasABall()==false && (forceInd<=(handler.getFPS()*ball.getforceMaxTime())) && (forcePreviousState)){ //
			forceInd++;
			if ((dirLeft)&&(directionInd>(-ball.getAngleMaxTime()*handler.getFPS())))directionInd--;
			if ((dirRight)&&(directionInd<(ball.getAngleMaxTime()*handler.getFPS())))directionInd++;
			if ((rotLeft)&&(rotationInd<(ball.getwMaxTime()*handler.getFPS())))rotationInd++;
			if ((rotRight)&&(rotationInd>(-ball.getwMaxTime()*handler.getFPS())))rotationInd--;

			if ((shotCancelation)||(forceInd==(handler.getFPS()*ball.getforceMaxTime()))||(force==false))
				{
				forceInd = 0;
				directionInd = 0;
				rotationInd = 0;
				}
	}


if (!force) {
	if ((dirLeft) && (directionInd > (-ball.getAngleMaxTime() * handler.getFPS()))) directionInd--;
	if ((dirRight) && (directionInd < (ball.getAngleMaxTime() * handler.getFPS()))) directionInd++;
	if ((rotLeft) && (rotationInd < (ball.getwMaxTime() * handler.getFPS()))) rotationInd++;
	if ((rotRight) && (rotationInd > (-ball.getwMaxTime() * handler.getFPS()))) rotationInd--;
}
		if (shotCancelation)	
		{
			forceInd = 0;
			directionInd = 0;
			rotationInd = 0;
			}
	}
	private void adaptMode(){
		if (player.getMode()==1){
			force = handler.getKeyManager().P1_force;
			dirLeft = handler.getKeyManager().P1_dirLeft;
			dirRight = handler.getKeyManager().P1_dirRight;
			rotLeft = handler.getKeyManager().P1_rotLeft;
			rotRight = handler.getKeyManager().P1_rotRight;
			shotCancelation = handler.getKeyManager().P1_cancelation;
			}
			
		if (player.getMode()==2){
			force = handler.getKeyManager().P2_force;
			dirLeft = handler.getKeyManager().P2_dirLeft;
			dirRight = handler.getKeyManager().P2_dirRight;
			rotLeft = handler.getKeyManager().P2_rotLeft;
			rotRight = handler.getKeyManager().P2_rotRight;
			shotCancelation = handler.getKeyManager().P2_cancelation;
			}
		player.setShotCancelation(shotCancelation);
	}

	public void storeParameters(){
		Vb1 = ball.getV();
		ballAngle = ball.getTempAngle();
	}
	
	public void shoot(){
		ball.setFlagMovement(true);
		player.dropBall(200);
		speed = player.getSpeed();
		if (!player.getIsRunning())speed=0;
			//rotation, direction and force taken from user (refer to player's foot)
			newW = ball.getwMax()*(rotationInd/(ball.getwMaxTime()*handler.getFPS()));
			newAngle = -(directionInd)/(ball.getAngleMaxTime()*handler.getFPS())*Math.PI/3; //-60 to 60deg, 60deg to be set in angleMaxTime seconds
		if (player.getMode()==2){
			newW=-newW;
			newAngle=-newAngle;
		}
		forceVal = (int) (ball.getforceMax()/423*(60*Math.log(360*(forceInd/(ball.getforceMaxTime()*handler.getFPS()))+0.3)+70)*player.getStaminaForceFactor()); //adjust equation parameters to simplify calculation (without scaling)
			//Vp - player velocity (value: speed, angle: playerAngle),
			//Vf - foot velocity (value:((forceVal*ball.getTk()/footMass), angle: newAngle)
			//summing up Vp and Vf vectors receiving V (value, angle) to get net velocity of the foot just before the shot
			Vpx = speed*(handler.getFPS()/ball.getRatio())*Math.sin(player.getPlayerAngle());
			Vpy = speed*(handler.getFPS()/ball.getRatio())*Math.cos(player.getPlayerAngle());

			Vx=(forceVal*contactDur/footMass)*Math.sin(newAngle+player.getPlayerAngle())+Vpx;
			Vy=(forceVal*contactDur/footMass)*Math.cos(newAngle+player.getPlayerAngle())+Vpy;

			V = Math.sqrt(Vx*Vx+Vy*Vy);
			Vangle = MyMath.angleFromComponents(Vx,Vy);
			//2)calculate ball velocity (assuming foot stops completely) after the shot
			//if it's first touch shot, take stored ball parameters. If not, use current (0)
			if (firstTouch){
				thunderVisible = true;
				System.out.println("First Touch Shot Done");
				System.out.println("Vb1 : "+Vb1+", ballAngle: "+Math.toDegrees(ballAngle));
			Vb2x = ((Vx*footMass*(1+coefE))+(Vb1*Math.sin(ballAngle)*(ballMass-coefE*footMass)))/(footMass+ballMass);
			Vb2y = ((Vy*footMass*(1+coefE))+(Vb1*Math.cos(ballAngle)*(ballMass-coefE*footMass)))/(footMass+ballMass);
			}else{//ball was received, so it's velocity and angle is same as player's (Vpx, Vpy)
				Vb2x = ((Vx*footMass*(1+coefE))+(Vpx*(ballMass-coefE*footMass)))/(footMass+ballMass);
				Vb2y = ((Vy*footMass*(1+coefE))+(Vpy*(ballMass-coefE*footMass)))/(footMass+ballMass);
				System.out.println("no First Touch Shot Done");
			}
			Vb2 = Math.sqrt(Vb2x*Vb2x+Vb2y*Vb2y);
			Vb2NewAngle = MyMath.angleFromComponents(Vb2x, Vb2y);
			//3)using ball velocity to get newXP2 and newYP2
			newXP2 = ball.getX() + Vb2 * Math.sin(Vb2NewAngle)*((Math.round((ball.getRatio()/handler.getFPS())*100000d))/100000d);
			newYP2 = ball.getY() - Vb2 * Math.cos(Vb2NewAngle)*((Math.round((ball.getRatio()/handler.getFPS())*100000d))/100000d);
			System.out.println("Vb2: "+Vb2+", ballAngle new: "+Math.toDegrees(Vb2NewAngle));
			player.setSetForShot(true);
			firstTouch=false;
			updateVelocity = true;
		
	//passing parameters to the ball
		ball.setW(newW);
		ball.setTX(ball.getX()/ball.getRatio());
		ball.setTY(ball.getY()/ball.getRatio());
		ball.setXPrev2(newXP2/ball.getRatio());
		ball.setYPrev2(newYP2/ball.getRatio());
	//once shot is done all flags and parameters have to be reset
		player.setHasABall(false);
		ball.setCaughtBy(0);
		newAngle = 0;
		newW = 0;
		newXP2 = 0;
		newYP2 = 0;
		forceInd = 0;
		directionInd = 0;
		rotationInd = 0;
		forcePreviousState=false;
		force=false;
		
	}

	
	public boolean checkIfcaught(){

		if ((player.getMayCatch()==true)&&((ball.getCaughtBy()==0)||(ball.getCaughtBy()==player.getMode()))){
		
		if (	(checkDistance()<(2+ball.getRadius()+player.getRadius())) && ((ball.getV()<15)&&(ball.getV()>2))
				&& 
				(((player.getMode()==2) && ((beta > (-15*Math.PI/180)) || (beta < (15*Math.PI/180)))) 
					||
					((player.getMode()==1) && ((beta > (165*Math.PI/180)) || (beta < (195*Math.PI/180))) ))	)
		
		{
			player.setHasABall(true);
			ball.setFlagMovement(false);
			ball.setCaughtBy(player.getMode());
			return true;
		}
		
		if ((checkDistance()<(ball.getRadius()+player.getRadius())) && ball.getV()<2){
			player.setHasABall(true);
			ball.setFlagMovement(false);
			ball.setCaughtBy(player.getMode());
			//System.out.println(player.getMode() + "return2 (true)");
			//gubienie pi?ki - tu nie wchodzi,a powinien
			//przyczyn? jest zle policzona odleglosc miedzy pilka i zawodnikiem...
			//if (player.getMode()==1)System.out.println("return2 distance: "+checkDistance());
			return true;
		}
		
		if ((checkDistance()>(2+ball.getRadius()+player.getRadius()))){
			player.setHasABall(false);
			ball.setCaughtBy(0);
			//if (player.getMode()==1)System.out.println("return3 distance: "+checkDistance());
			//System.out.println("return2 distance: "+checkDistance() + "player: "+player.getCenterX()+","+player.getCenterY());
			return false;
		}
		 return false;
		 }
		return false;
	}
	
	public void translateBall(){
		ball.setTX((player.getCenterX()-5+player.getRadius()*Math.sin(player.getPlayerAngle()))/ratio);
		ball.setTY((player.getCenterY()-6+player.getRadius()*Math.cos(player.getPlayerAngle()))/ratio);
		ball.setXPrev2(ball.getTX());
		ball.setYPrev2(ball.getTY());
		
		ball.setX((player.getCenterX()-5+player.getRadius()*Math.sin(player.getPlayerAngle())));
		ball.setY((player.getCenterY()-6+player.getRadius()*Math.cos(player.getPlayerAngle())));
	}
	
	public void dribble(){
		if ((checkIfcaught()==true)){
			if ((ball.getDribbleAllowed()==true)){
				ball.setTX((player.getCenterX()-5+player.getRadius()*Math.sin(player.getPlayerAngle()))/ratio);
				ball.setTY((player.getCenterY()-6+player.getRadius()*Math.cos(player.getPlayerAngle()))/ratio);
				ball.setXPrev2(ball.getTX());
				ball.setYPrev2(ball.getTY());
				
				ball.setX((player.getCenterX()-5+player.getRadius()*Math.sin(player.getPlayerAngle())));
				ball.setY((player.getCenterY()-6+player.getRadius()*Math.cos(player.getPlayerAngle())));
			}else{
				ball.setTX((player.getCenterX()-5)/ratio);
				ball.setTY((player.getCenterY()-6)/ratio);
				ball.setXPrev2(ball.getTX());
				ball.setYPrev2(ball.getTY());
				
				ball.setX((player.getCenterX()-5));
				ball.setY((player.getCenterY()-6));
			}
		}
	}
	
	public void deflectBall(){
		alpha = ball.getTempAngle();

		xDistance = playerCenter.first-ballCenter.first;
		yDistance = playerCenter.second-ballCenter.second;
		beta=MyMath.angleFromComponents(xDistance,yDistance);
		gamma =  (2*alpha - 2*beta - Math.PI);

		xPrev2 = ball.getXPrev2();
		yPrev2 = ball.getYPrev2();

		//start of rotation of Prev2 point around TX,TY point
		xPrev2 -= ball.getTX();
		yPrev2 -= ball.getTY();
		
		newXPrev2 = (xPrev2*Math.cos(gamma)-yPrev2*Math.sin(gamma));	
		newYPrev2 = (xPrev2*Math.sin(gamma)+yPrev2*Math.cos(gamma));
		
		xPrev2 = newXPrev2+ball.getTX();
		yPrev2 = newYPrev2+ball.getTY();
		//end of rotation of Prev2 point around TX,TY point
		
		//velocity dumping
		xPrev2 += (ball.getTX()-xPrev2)*dumping;
		yPrev2 += (ball.getTY()-yPrev2)*dumping;
		
		ball.setXPrev2(xPrev2);
		ball.setYPrev2(yPrev2);
		updateNumbers();
	}
protected void adjustBall(){ //adjusting ball's TX,TY,xPrev2,yPrev2 for proper deflection
	
	if (ballCenter.first!=ballPrevCenter.first){ //centersLine is not vertical
	centersLine = MyMath.lineOnTwoPoints(ballCenter,ballPrevCenter);
	rootPoints = MyMath.interOfLineAndCircle(centersLine, playerCenter, ball.getRadius()+player.getRadius());
	x1=rootPoints[0].first;
	y1=rootPoints[0].second;
	x2=rootPoints[1].first;
	y2=rootPoints[1].second;
	}else{ //centersLine is vertical
	rootPoints = MyMath.interOfVerticalLineAndCircle(ballCenter.first, playerCenter, ball.getRadius()+player.getRadius());
	x1=rootPoints[0].first;
	y1=rootPoints[0].second;
	x2=rootPoints[1].first;
	y2=rootPoints[1].second;
	}
	//find the point located closer to non-ball body and move TX,TY, xPrev2, yPrev2 by ball radius (rdx,rdy) towards the line
	dist1x = (ballPrevCenter.first-x1);
	dist1y = (ballPrevCenter.second-y1);
	dist2x = (ballPrevCenter.first-x2);
	dist2y = (ballPrevCenter.second-y2);
	dist1 = Math.sqrt(dist1x*dist1x+dist1y*dist1y);
	dist2 = Math.sqrt(dist2x*dist2x+dist2y*dist2y);
	transX = (ballCenter.first - ballPrevCenter.first)/ratio;
	transY = (ballCenter.second - ballPrevCenter.second)/ratio;
	
	if (dist1>=dist2){
		ball.setTX((x2-ball.getRadius())/ratio);
		ball.setTY((y2-ball.getRadius())/ratio);
		//System.out.println("root chosen: 2, as dist1 = "+dist1+", and dist2 = "+dist2);
	}else{
		ball.setTX((x1-ball.getRadius())/ratio);
		ball.setTY((y1-ball.getRadius())/ratio);
		//System.out.println("root chosen: 1, as dist1 = "+dist1+", and dist2 = "+dist2);
	}
	//adjust current position of the ball
	ball.setXPrev2(ball.getTX()-transX);
	ball.setYPrev2(ball.getTY()-transY);
	updateNumbers();
}

	public void updateNumbers(){
		//ballCenter.first = ball.getCenterX();
		//ballCenter.second = ball.getCenterY();
		ballCenter.first = ball.getTX()*ratio+ball.getRadius(); 
		ballCenter.second = ball.getTY()*ratio+ball.getRadius();
		ballPrevCenter.first = ball.getXPrev2()*ratio+ball.getRadius();
		ballPrevCenter.second = ball.getYPrev2()*ratio+ball.getRadius();
		playerCenter.first = player.getCenterX(); 
		playerCenter.second = player.getCenterY();
		//playerCenter.first = player.getTempX()+player.getWidth()/2; 
		//playerCenter.second = player.getTempY()+player.getHeight()/2;
	}
	

	
	public double checkDistance(){
		return MyMath.distanceTwoPoints(ballCenter, playerCenter);
	}

	public void checkFirstTouch(){
		if (!player.getHasABall()) storeParameters();
		if (!hasBallPrevious &&(player.getHasABall())) {
			firstTouchCounter=1;
			firstTouch=true;
			thunderVisible=false;
			thunderCounter=0;
			//trigger First Touch Shot Enabled message to the screen
			System.out.println("First Touch Shot Enabled");
		}
		if (firstTouchCounter>0) firstTouchCounter++;
		if (firstTouchCounter>=(handler.getFPS()*firstTouchPeriod)) {
			//trigger First Touch Shot Disabled message to the screen
			System.out.println("First Touch Shot Disabled");
			firstTouchCounter=0;
			firstTouch=false;
			storeParameters();
		}
		//System.out.println("has a ball: "+player.getHasABall()+", had a ball: "+hasBallPrevious+", first touch: "+firstTouch);
		hasBallPrevious=player.getHasABall();
	}

	public void checkThunder(){
		if (thunderVisible) thunderCounter++;
		if ((thunderCounter)>=(thunderPeriod*handler.getFPS())){
			thunderCounter=0;
			thunderVisible=false;
		}
	}

	public void tick(){
		if ((TrainingState.trainingPlayer!=0)&&player.getCallBall()) translateBall();
		updateNumbers();
		checkFirstTouch();
		checkThunder();
			
	if ((checkDistance()<=(ball.getRadius()+player.getRadius())) && contactAllowed){	
		updateNumbers();
		//System.out.println("Player - normal ball adjustment");
		adjustBall();
		updateNumbers();
		deflectBall();
		updateNumbers();
	}

		if (checkDistance()>(ball.getRadius()+player.getRadius())) 
		{contactAllowed = true;
		}else{contactAllowed = false;}
		
		
		if ((checkDistance()>(ball.getRadius()+player.getRadius()))&&(checkDistance()<=(2*(ball.getRadius()+player.getRadius()))) 
				&& contactAllowed2) {	
				//updateNumbers();
				centersLine = MyMath.lineOnTwoPoints(ballCenter,ballPrevCenter);
				perpenLine = MyMath.perpendicularToLineOnPoint(centersLine, playerCenter);
				D = MyMath.interOfLines(centersLine, perpenLine);
				z= MyMath.distanceTwoPoints(D, playerCenter); //distance between the center of non-ball player and the line connecting centers of two ball positions
				if (MyMath.isPointBetweenPoints(D, ballCenter, ballPrevCenter)) {
					didBallPassOrb = true;
				if (z<(ball.getRadius()+player.getRadius())&&didBallPassOrb){
				adjustBall();
				updateNumbers();
				//System.out.println("Player - rare example of extended ball adjustment");
				deflectBall();
				updateNumbers();
				}
				}
			}	
			if ((checkDistance()<=(ball.getRadius()+player.getRadius())) && contactAllowed) {
				//System.out.println("distance ="+checkDistance());
				updateNumbers();
				//System.out.println("Player - normal ball adjustment");
				adjustBall();
				updateNumbers();
				deflectBall();
				updateNumbers();
			}
					
			if (checkDistance()>=(ball.getRadius()+player.getRadius())) 
				{contactAllowed = true;
				}else{contactAllowed = false;}

			if ( (MyMath.distanceTwoPoints(playerCenter, ballPrevCenter)>=(ball.getRadius()+player.getRadius())) 
				&&
				(MyMath.distanceTwoPoints(playerCenter, ballCenter)>=(ball.getRadius()+player.getRadius())) )
			{contactAllowed2 = true;
			}else{contactAllowed2 = false;}	

		dribble();
		getInput();

		if (player.getHasABall()==true){
		if (((forcePreviousState==true)&&(force==false))||(forceInd>=(handler.getFPS()*ball.getforceMaxTime()))){
			shoot();
		}
	}else{player.setSetForShot(false);}
		forcePreviousState = force;
		}

	public void render(Graphics g){
		if ((thunderVisible)&&(!firstTouch)&&(player.getMode()==1)) {
				g.drawImage(Assets.ballIndicatorBig, (int) (handler.getWidth()*0.925+ballIndicatorCorrection/2), (int) (handler.getHeight()*0.83+ballIndicatorCorrection/2),  50 , 50 , null);
				g.drawImage(Assets.kickingShoeBlue, (int) (handler.getWidth()*0.95), (int) (handler.getHeight()*0.79),  50, 85, null);
			}
		if ((thunderVisible)&&(!firstTouch)&&(player.getMode()==2)) {
				g.drawImage(Assets.ballIndicatorBig, (int) (handler.getWidth()*0.925+ballIndicatorCorrection/2), (int) (handler.getHeight()*0.09+ballIndicatorCorrection/2),  50 , 50 , null);
				g.drawImage(Assets.kickingShoeRed, (int) (handler.getWidth()*0.95), (int) (handler.getHeight()*0.05),  50, 85, null);
			}

		if (firstTouch) {
				ballIndicatorSize = (int)(50*firstTouchCounter/(firstTouchPeriod*handler.getFPS()));
				if (ballIndicatorSize%2!=0) ballIndicatorSize++;
				ballIndicatorCorrection = (int)((50-ballIndicatorSize)/2);
				if (ballIndicatorCorrection%2!=0) ballIndicatorCorrection++;
			}
		if ((firstTouch)&&(player.getMode()==1))g.drawImage(Assets.ballIndicatorBig, (int) (handler.getWidth()*0.925+ballIndicatorCorrection/2), (int) (handler.getHeight()*0.83+ballIndicatorCorrection/2),  ballIndicatorSize+ballIndicatorCorrection , ballIndicatorSize+ballIndicatorCorrection , null);
		if ((firstTouch)&&(player.getMode()==2))g.drawImage(Assets.ballIndicatorBig, (int) (handler.getWidth()*0.925+ballIndicatorCorrection/2), (int) (handler.getHeight()*0.09+ballIndicatorCorrection/2),  ballIndicatorSize+ballIndicatorCorrection , ballIndicatorSize+ballIndicatorCorrection , null);

		if (updateVelocity){
			velocity = formatter.format(Vb2);
			updateVelocity=false;
			showVelocity=true;
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("Tahoma", Font.BOLD, 15));
		if ((player.getMode()==1)&&showVelocity)g.drawString("V="+velocity+"m/s",(int)(handler.getWidth()*0.9),(int) (handler.getHeight()*0.75));
		if ((player.getMode()==2)&&showVelocity)g.drawString("V="+velocity+"m/s",(int)(handler.getWidth()*0.9),(int) (handler.getHeight()*0.25));

	}

	public Player getPlayer(){
		return player;
	}
	public Ball getBall(){
		return ball;
	}
	public double getForceInd(){
		return forceInd;
	}
	public double getDirectionInd(){
		return directionInd;
	}
	public double getRotationInd(){
		return rotationInd;
	}
	public void setThunderVisible (boolean flag){thunderVisible=flag;}
	public boolean getThunderVisible(){return thunderVisible;}
}
