package contact;

import entities.Entity;
import entities.creatures.Ball;
import mathToolBoxKajder.MyMath;
import mathToolBoxKajder.doublePair;

public class BallFlatContact extends ContactManager {
    Ball ball;
    Entity body;
    private double alpha, gamma, xPrev2, yPrev2, newXPrev2, newYPrev2, dumping, ratio, newX, newY, transX, transY;
    //private double contactX, contactY;
    private boolean isHorizontal;
    private boolean contactAllowed = true;
    protected doublePair D = new doublePair(0, 0);
    protected doublePair centersLine = new doublePair(0, 0);
    protected doublePair perpenLine = new doublePair(0, 0);
    protected doublePair ballPrevCenter = new doublePair(0, 0);
    protected doublePair bodyCenter = new doublePair(0, 0);
    protected doublePair ballCenter = new doublePair(0, 0);

    public BallFlatContact(Ball ball, Entity body, double dumping, boolean isHorizontal) {
        super(ball, body);
        // TODO Auto-generated constructor stub
        this.ball = ball;
        this.body = body;
        this.dumping = dumping;
        this.isHorizontal = isHorizontal;
        this.ratio = ball.getRatio();
    }

    public void deflectBall() {
        alpha = ball.getTempAngle();

        //gamma:
        if (isHorizontal == true) {
            if ((alpha >= (Math.PI / 2)) && (alpha <= (3 * Math.PI / 2))) {
                gamma = 2 * alpha - Math.PI;
            } else {
                gamma = 2 * alpha + Math.PI;
            }
        }
        if (isHorizontal == false) {
            gamma = 2 * alpha;
        }

        //System.out.println("CONTACT! \nalpha = "+(ball.getTempAngle())*180/Math.PI+", gamma = "+(gamma*180/Math.PI));
        //rotation module:
        xPrev2 = ball.getXPrev2();
        yPrev2 = ball.getYPrev2();
        //1)subtract
        xPrev2 -= ball.getTX();
        yPrev2 -= ball.getTY();
        //2)rotate
        newXPrev2 = (xPrev2 * Math.cos(gamma) - yPrev2 * Math.sin(gamma));
        newYPrev2 = (xPrev2 * Math.sin(gamma) + yPrev2 * Math.cos(gamma));
        //3)add back
        xPrev2 = newXPrev2 + ball.getTX();
        yPrev2 = newYPrev2 + ball.getTY();

        //velocity dumping:
        xPrev2 += (ball.getTX() - xPrev2) * dumping;
        yPrev2 += (ball.getTY() - yPrev2) * dumping;

        ball.setXPrev2(xPrev2);
        ball.setYPrev2(yPrev2);

        updateNumbers();
    }


    public void tick() {
        updateNumbers();
		/*if ((findDistanceX()<=0)&&(findDistanceY()<=0)&&contactAllowed) {
			deflectBall();
			contactAllowed = false;
		}*/
        if ((findDistanceX() <= 0) && (findDistanceY() <= 0) && contactAllowed) {//current ball pos inside the body
            if ((bodyCenter.first - body.getWidth() / 2 - ballPrevCenter.first - ball.getRadius()) > 0) {//prev ball pos left to the body
                centersLine = MyMath.lineOnTwoPoints(ballCenter, ballPrevCenter);
                newX = bodyCenter.first - body.getWidth() / 2 - ball.getRadius();
                newY = centersLine.first * newX + centersLine.second;
                adjustBall();
            }
            if ((ballPrevCenter.first - ball.getRadius() - bodyCenter.first - body.getWidth() / 2) > 0) {//prev ball pos right to the body
                centersLine = MyMath.lineOnTwoPoints(ballCenter, ballPrevCenter);
                newX = bodyCenter.first + body.getWidth() / 2 + ball.getRadius();
                newY = centersLine.first * newX + centersLine.second;
                adjustBall();
            }

            if ((bodyCenter.second - body.getHeight() / 2 - ballPrevCenter.second - ball.getRadius()) > 0) {//prev ball pos above the body
                if (ballCenter.first == ballPrevCenter.first) {//ball trajectory is vertical
						/*
						System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEe");
						System.out.println("body center coordinates: ("+bodyCenter.first+","+bodyCenter.second+")");
						System.out.println("ball center coordinates: ("+ballCenter.first+","+ballCenter.second+")");
						System.out.println("ball  prev center coordinates: ("+ballPrevCenter.first+","+ballPrevCenter.second+")");
						System.out.println("radius: "+ball.getRadius()+", width/2 :"+body.getWidth()/2);
						*/
                    updateNumbers();
                    newY = bodyCenter.second - body.getHeight() / 2 - ball.getRadius();
                    newX = ballCenter.first;
                    adjustBall();
                } else {
                    centersLine = MyMath.lineOnTwoPoints(ballCenter, ballPrevCenter);
                    newY = bodyCenter.second - body.getHeight() / 2 - ball.getRadius();
                    newX = (newY - centersLine.second) / centersLine.first;
                    adjustBall();
                }
            }

            if ((ballPrevCenter.second - ball.getRadius() - bodyCenter.second - body.getHeight() / 2) > 0) {//prev ball pos behind the wall
                if (ballCenter.first == ballPrevCenter.first) {//ball trajectory is vertical
                    updateNumbers();
                    newY = bodyCenter.second + body.getHeight() / 2 + ball.getRadius();
                    newX = ballCenter.first;
                    adjustBall();
                } else {
                    centersLine = MyMath.lineOnTwoPoints(ballCenter, ballPrevCenter);
                    newY = bodyCenter.second + body.getHeight() / 2 + ball.getRadius();
                    newX = (newY - centersLine.second) / centersLine.first;
                    adjustBall();
                }
            }
        }


        //cases for ball passing through the body without contact:
        if ((findDistanceY() < 0) && contactAllowed) {
            if ((ballCenter.first - ball.getRadius() - bodyCenter.first - body.getWidth() / 2) > 0) {//current ball pos right to the body
                if ((bodyCenter.first - body.getWidth() / 2 - ballPrevCenter.first - ball.getRadius()) > 0) {//prev ball pos left to the body
					/*
					System.out.println("body moving from right to left");
					System.out.println("body center coordinates: ("+bodyCenter.first+","+bodyCenter.second+")");
					System.out.println("ball center coordinates: ("+ballCenter.first+","+ballCenter.second+")");
					System.out.println("ball  prev center coordinates: ("+ballPrevCenter.first+","+ballPrevCenter.second+")");
					System.out.println("radius: "+ball.getRadius()+", width/2 :"+body.getWidth()/2);
					*/
                    centersLine = MyMath.lineOnTwoPoints(ballCenter, ballPrevCenter);
                    newX = bodyCenter.first - body.getWidth() / 2 - ball.getRadius();
                    newY = centersLine.first * newX + centersLine.second;
                    adjustBall();
                }
            }
            if ((bodyCenter.first - body.getWidth() / 2 - ballCenter.first - ball.getRadius()) > 0) {//current ball pos left to the body
                if ((ballPrevCenter.first - ball.getRadius() - bodyCenter.first - body.getWidth() / 2) > 0) {//prev ball pos right to the body
					/*
					System.out.println("body moving from left to right");
					System.out.println("body center coordinates: ("+bodyCenter.first+","+bodyCenter.second+")");
					System.out.println("ball center coordinates: ("+ballCenter.first+","+ballCenter.second+")");
					System.out.println("ball  prev center coordinates: ("+ballPrevCenter.first+","+ballPrevCenter.second+")");
					System.out.println("radius: "+ball.getRadius()+", width/2 :"+body.getWidth()/2);
					*/
                    centersLine = MyMath.lineOnTwoPoints(ballCenter, ballPrevCenter);
                    newX = bodyCenter.first + body.getWidth() / 2 + ball.getRadius();
                    newY = centersLine.first * newX + centersLine.second;
                    adjustBall();
                }
            }
        }

        if ((findDistanceX() <= 0) && contactAllowed) {
            if ((ballCenter.second - ball.getRadius() - bodyCenter.second - body.getHeight() / 2) > 0) {//current ball pos below the body (Player1)
                if ((bodyCenter.second - body.getHeight() / 2 - ballPrevCenter.second - ball.getRadius()) >= 0) {//prev ball pos above the body
                    if (ballCenter.first == ballPrevCenter.first) {//ball trajectory is vertical
                        updateNumbers();
                        newY = bodyCenter.second - body.getHeight() / 2 - ball.getRadius();
                        newX = ballCenter.first;
                        adjustBall();
                    } else {
                        centersLine = MyMath.lineOnTwoPoints(ballCenter, ballPrevCenter);
                        newY = bodyCenter.second - body.getHeight() / 2 - ball.getRadius();
                        newX = (newY - centersLine.second) / centersLine.first;
                        adjustBall();
                    }
                }
            }
            if ((bodyCenter.second - ballCenter.second - ball.getRadius() - body.getHeight() / 2) >= 0) {//current ball pos on the pitch (Player2)
                if ((ballPrevCenter.second - ball.getRadius() - bodyCenter.second - body.getHeight() / 2) > 0) {//prev ball pos behind the wall
                    if (ballCenter.first == ballPrevCenter.first) {//ball trajectory is vertical
                        updateNumbers();
                        newY = bodyCenter.second + body.getHeight() / 2 + ball.getRadius();
                        newX = ballCenter.first;
                        adjustBall();
                    } else {
                        centersLine = MyMath.lineOnTwoPoints(ballCenter, ballPrevCenter);
                        newY = bodyCenter.second + body.getHeight() / 2 + ball.getRadius();
                        newX = (newY - centersLine.second) / centersLine.first;
                        adjustBall();
                    }
                }
            }
        }

        if ((findDistanceX() >= 0) || (findDistanceY() >= 0)) {
            contactAllowed = true;
        }
    }

    private void adjustBall() {
        transX = (ballCenter.first - ballPrevCenter.first) / ratio;
        transY = (ballCenter.second - ballPrevCenter.second) / ratio;
        ball.setTX((newX - ball.getRadius()) / ratio);
        ball.setTY((newY - ball.getRadius()) / ratio);
        ball.setXPrev2(ball.getTX() - transX);
        ball.setYPrev2(ball.getTY() - transY);
        updateNumbers();
        deflectBall();
        contactAllowed = false;
    }

    public double findDistanceX() {
        if (ballCenter.first >= bodyCenter.first) {
            return ballCenter.first - ball.getRadius() - bodyCenter.first - body.getWidth() / 2;
        }
        if (ballCenter.first < bodyCenter.first) {
            return bodyCenter.first - body.getWidth() / 2 - ballCenter.first - ball.getRadius();
        }
        return 1;
    }

    public double findDistanceY() {
        if (ballCenter.second >= bodyCenter.second) {
            return ballCenter.second - ball.getRadius() - bodyCenter.second - body.getHeight() / 2;
        }
        if (ballCenter.second < bodyCenter.second) {
            return bodyCenter.second - body.getHeight() / 2 - ballCenter.second - ball.getRadius();
        }
        return 1;
    }

    protected void updateNumbers() {
        ballCenter.first = ball.getTX() * ratio + ball.getRadius();
        ballCenter.second = ball.getTY() * ratio + ball.getRadius();
        ballPrevCenter.first = ball.getXPrev2() * ratio + ball.getRadius();
        ballPrevCenter.second = ball.getYPrev2() * ratio + ball.getRadius();
        bodyCenter.first = body.getCenterX();
        bodyCenter.second = body.getCenterY();
    }

    public boolean getContactAllowed() {
        return contactAllowed;
    }

}
