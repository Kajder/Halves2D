package contact;

import java.awt.Point;

import entities.Entity;
import entities.creatures.Ball;
import mathToolBoxKajder.*;

public class BallOrbContact extends ContactManager {
	Ball ball;
	Entity body;
	protected double ratio, z, f1, f2, f3, x1, x2, y1, y2, dist1, dist2, dist1x, dist2x, dist1y, dist2y, transX, transY,
	alpha, gamma,xPrev2,yPrev2,newXPrev2,newYPrev2, newDX, newDY, xDistance, yDistance, distance, dumping;
	protected double beta;
	protected boolean contactAllowed=true, contactAllowed2=true, didBallPassOrb;
	protected doublePair D = new doublePair(0,0); 
	protected doublePair centersLine = new doublePair(0,0); 
	protected doublePair perpenLine = new doublePair(0,0); 
	protected doublePair ballPrevCenter = new doublePair(0,0); 
	protected doublePair bodyCenter = new doublePair(0,0);
	protected doublePair ballCenter = new doublePair(0,0);
	protected doublePair[] rootPoints = new doublePair[2];
	
	public BallOrbContact(Ball ball, Entity body, double dumping){
		super(ball, body);
		this.ball=ball;
		this.body=body;
		this.dumping = dumping;
		this.ratio=ball.getRatio();
	}
	
	public void deflectBall(){
		alpha = ball.getTempAngle();
		
		//xDistance = body.getCenterX()-ball.getCenterX();
		//yDistance =body.getCenterY()-ball.getCenterY();
		//System.out.println("DEFLECT BALL DATA start");
		
		xDistance = bodyCenter.first-ballCenter.first;
		yDistance = bodyCenter.second-ballCenter.second;
		/*System.out.println("body = ("+bodyCenter.first+";"+bodyCenter.second+")");
		System.out.println("ball = ("+ballCenter.first+";"+ballCenter.second+")");
		System.out.println("xDistance = "+xDistance+", yDistance = "+yDistance);
		*/
		if ((xDistance>0) && (yDistance>0)) beta = (Math.atan(xDistance/yDistance))+Math.PI;
		if ((xDistance>0) && (yDistance<0)) beta = (Math.atan(xDistance/yDistance));
		if ((xDistance<0) && (yDistance>0)) beta = (Math.atan(xDistance/yDistance))+Math.PI;
		if ((xDistance<0) && (yDistance<0)) beta = (Math.atan(xDistance/yDistance));
		if ((xDistance==0) && (yDistance>0)) beta = 1*Math.PI;
		if ((xDistance==0) && (yDistance<0)) beta = 0;
		if ((xDistance>0) && (yDistance==0)) beta = 1.5*Math.PI;
		if ((xDistance<0) && (yDistance==0)) beta = 0.5*Math.PI;
		//beta = MyMath.angleFromComponents(xDistance, yDistance);
		gamma =  (2*alpha - 2*beta - Math.PI);
	
		//System.out.println("BALL-ORB CONTACT! \nalpha = "+(ball.getTempAngle())*180/Math.PI+", beta = "+(beta*180/Math.PI)+", gamma = "+Math.toDegrees(gamma));
		//System.out.println("\ndistance: "+checkDistance()+", sum of radians"+(ball.getRadius()+body.getRadius()));
		//System.out.println("DEFLECT BALL DATA stop");
		//System.out.println("xPrev2 = "+ball.getXPrev2()+", yPrev2 = "+ball.getYPrev2()+", tx = "+ball.getTX()+", ty = "+ball.getTY());
		
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
	rootPoints = MyMath.interOfLineAndCircle(centersLine, bodyCenter, ball.getRadius()+body.getRadius());
	x1=rootPoints[0].first;
	y1=rootPoints[0].second;
	x2=rootPoints[1].first;
	y2=rootPoints[1].second;
	}else{ //centersLine is vertical
	rootPoints = MyMath.interOfVerticalLineAndCircle(ballCenter.first, bodyCenter, ball.getRadius()+body.getRadius());
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

protected void updateNumbers(){
	ballCenter.first = ball.getTX()*ratio+ball.getRadius(); 
	ballCenter.second = ball.getTY()*ratio+ball.getRadius();
	ballPrevCenter.first = ball.getXPrev2()*ratio+ball.getRadius();
	ballPrevCenter.second = ball.getYPrev2()*ratio+ball.getRadius();
	bodyCenter.first = body.getCenterX(); bodyCenter.second = body.getCenterY();
}

public double checkDistance(){
return Math.sqrt((
		(ballCenter.first-bodyCenter.first)*(ballCenter.first-bodyCenter.first))
		+
		(ballCenter.second-bodyCenter.second)*(ballCenter.second-bodyCenter.second));
}

public void tick(){
	updateNumbers();
if ((checkDistance()>(ball.getRadius()+body.getRadius()))&&(checkDistance()<=(2*(ball.getRadius()+body.getRadius()))) 
	&& contactAllowed2) {	
	//updateNumbers();
	centersLine = MyMath.lineOnTwoPoints(ballCenter,ballPrevCenter);
	perpenLine = MyMath.perpendicularToLineOnPoint(centersLine, bodyCenter);
	D = MyMath.interOfLines(centersLine, perpenLine);
	z= MyMath.distanceTwoPoints(D, bodyCenter); //distance between the center of non-ball body and the line connecting centers of two ball positions
	if (MyMath.isPointBetweenPoints(D, ballCenter, ballPrevCenter)) {
		didBallPassOrb = true;
	if (z<(ball.getRadius()+body.getRadius())&&didBallPassOrb){
	adjustBall();
	/*System.out.println("rare example of extended ball adjustment");
	System.out.println("ballCenter=("+ballCenter.first+","+ballCenter.second+"), ballCentPrev=("+ballPrevCenter.first+","+ballPrevCenter.second+")");
	System.out.println("bodyCenter=("+bodyCenter.first+","+bodyCenter.second+")");
	*/
	deflectBall();
	}
	}
}	
if ((checkDistance()<=(ball.getRadius()+body.getRadius())) && contactAllowed) {
	System.out.println("distance ="+distance);
	//updateNumbers();
	adjustBall();
	/*System.out.println("normal ball adjustment");
	System.out.println("ballCenter=("+ballCenter.first+","+ballCenter.second+"), ballCentPrev=("+ballPrevCenter.first+","+ballPrevCenter.second+")");
	System.out.println("bodyCenter=("+bodyCenter.first+","+bodyCenter.second+")");
	*/
	deflectBall();
}
		
if (checkDistance()>(ball.getRadius()+body.getRadius())) 
	{contactAllowed = true;
	}else{contactAllowed = false;}

if ( (MyMath.distanceTwoPoints(bodyCenter, ballPrevCenter)>(ball.getRadius()+body.getRadius())) 
	&&
	(MyMath.distanceTwoPoints(bodyCenter, ballCenter)>(ball.getRadius()+body.getRadius())) )
{contactAllowed2 = true;
}else{contactAllowed2 = false;}	

	}
	
public boolean getContactAllowed(){return contactAllowed;}


}
