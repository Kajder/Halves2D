package entities.creatures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import entities.Picture;
import game2d.Handler;
import gfx.Animation;
import gfx.Assets;
import input.KeyManager;

public class Player extends Creature {

  public static int DefPlayerWidth = 25, DefPlayerHeight = 20;
  boolean up, down, left, right, sprint, moveXallowedOrb = true, moveYallowedOrb = true, moveXallowedFlat = true, moveYallowedFlat = true, callBall;
  private double tempX = x, tempY = y, prevXdouble, prevYdouble, c1x, c1y, c2x, c2y, deadZone1 = KeyManager.deadZone1, deadZone2 = KeyManager.deadZone2;
  private double playerAngle, playerMass, footMass, stamina, staminaInitial, staminaForceFactor, staminaForceFactorMax, staminaForceFactorMin, staminaReduction, staminaRebuilt, exhaustedSpeed;
  private double sprintSpeed, sprintBallSpeed, walkSpeed, sprintLoadTime, sprintLoadIncrement, sprintBallLoadIncrement, delta;
  private float playerFPS, alignmentFPS;
  private int score, a, b, walkPeriod = 10000, currX, currY, prevX, prevY;
  private boolean setForShot, shotCancellation, dropBallGate = true, isRunning, sprintPrev;
  public static Animation walkPlayer, walkPlayer1, walkPlayer2;
  private Timer timer;
  Picture goal;
  Graphics2D g2d;

  public Player(Handler handler, BufferedImage icon, double x, double y, int mode, Picture goal) {
    super(handler, icon, x, y, DefPlayerWidth, DefPlayerHeight);
    this.mode = mode;
    this.goal = goal;
    sprintSpeed = handler.getParameters().getCustomParameters().get("sprintSpeed") / 100;
    walkSpeed = handler.getParameters().getCustomParameters().get("walkSpeed") / 100;
    sprintBallSpeed = (sprintSpeed + walkSpeed) / 2;
    mayCatch = true;
    walkPlayer1 = new Animation(walkPeriod, Assets.framesPlayer1);
    walkPlayer2 = new Animation(walkPeriod, Assets.framesPlayer2);
    staminaInitial = handler.getParameters().getCustomParameters().get("stamina");
    stamina = staminaInitial;
    exhaustedSpeed = 0.5;
    staminaForceFactorMax = 1;
    staminaForceFactorMin = 0.5;
    staminaReduction = 3.5;
    staminaRebuilt = 4;
    playerFPS = handler.getFPS();
    alignmentFPS = (float) (Math.round(60 / playerFPS * 10000f)) / 10000f;
    sprintSpeed *= alignmentFPS;
    sprintBallSpeed *= alignmentFPS;
    walkSpeed *= alignmentFPS;

    if (mode == 1) {
      playerMass = handler.getParameters().getCustomParameters().get("blueMass");//kg
    }
    if (mode == 2) {
      playerMass = handler.getParameters().getCustomParameters().get("redMass");//kg
    }
    footMass = playerMass * 0.022;//kg
    sprintLoadTime = 3;
    sprintLoadIncrement = ((sprintSpeed - walkSpeed)) / (sprintLoadTime * handler.getFPS());
    sprintBallLoadIncrement = ((sprintBallSpeed - walkSpeed)) / (sprintLoadTime * handler.getFPS());
  }

  public void checkIfRunning() {
    isRunning = false;
    if ((x != prevXdouble) || (y != prevYdouble)) {
      isRunning = true;
    }
    prevXdouble = x;
    prevYdouble = y;
  }


  @Override
  public void tick() {
    if (stamina < staminaInitial) {
      stamina += staminaRebuilt;
    }
    if (stamina <= 150) {
      staminaForceFactor = staminaForceFactorMin;
    } else {
      staminaForceFactor = staminaForceFactorMax;
    }
    //if (mode==1) System.out.println("stamina P1: "+stamina);
    //if (mode==2) System.out.println("stamina P2: "+stamina);
    getInput();
    move();
    if (getHasABall() == true) {
      Ball.speed = speed;
      Ball.playerAngle = playerAngle;
    }
    walk();
    checkIfRunning();
    //System.out.println("Player1 frame: "+walkPlayer1.getIndex());
    //	handler.getGameCamera().centerOnEntity(this);
  }

  public void dropBall(long waitMills) {
    if (dropBallGate) {
      timer = new Timer();
      mayCatch = false;
      dropBallGate = false;
      System.out.println("May Not Catch");
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          mayCatch = true;
          System.out.println("May Catch");
          dropBallGate = true;
          timer.cancel();
        }
      }, waitMills);
    }
  }

  public void walk() {
    if (mode == 1) {
      walkPlayer = walkPlayer1;
    }
    if (mode == 2) {
      walkPlayer = walkPlayer2;
    }
    prevX = currX;
    prevY = currY;
    currX = (int) x;
    currY = (int) y;
    if ((currX != prevX) || (currY != prevY)) {
      walkPlayer.setPeriod(100 - 40 * speed);
      stamina = stamina - speed * staminaReduction;
      if (stamina <= 0) {
        stamina = 0;
      }
    }
    if ((currX == prevX) && (currY == prevY) && (walkPlayer.getIndex() <= 14)) {
      walkPlayer.setPeriod(10000);
      walkPlayer.setIndex(0);
    }
    if (walkPlayer.getIndex() == 14) {
      walkPlayer.setIndex(0);
    }

    if (setForShot) {
      walkPlayer.setPeriod(70);
      walkPlayer.setIndex(15);
    }

    if (walkPlayer.getIndex() == 19) {
      walkPlayer.setIndex(0);
      walkPlayer.setPeriod(100 - 40 * speed);
    }
    if (shotCancellation) {
      walkPlayer.setIndex(1);
      walkPlayer.setPeriod(100 - 40 * speed);
    }

    walkPlayer.tick();
  }

  @Override
  public void render(Graphics g) {
    g2d = (Graphics2D) g;
    if (mode == 1) {
      g2d.rotate(-playerAngle - Math.PI, x + DefPlayerWidth / 2, y + DefPlayerHeight / 2);
      g2d.drawImage(walkPlayer1.getCurrentFrame(), (int) (x), (int) (y), width, height, null);
      g2d.rotate(playerAngle + Math.PI, x + DefPlayerWidth / 2, y + DefPlayerHeight / 2);
    }
    if (mode == 2) {
      g2d.rotate(-playerAngle, x + DefPlayerWidth / 2, y + DefPlayerHeight / 2);
      g2d.drawImage(walkPlayer2.getCurrentFrame(), (int) (x), (int) (y), width, height, null);
      g2d.rotate(playerAngle, x + DefPlayerWidth / 2, y + DefPlayerHeight / 2);
    }
  }

	/*public float getRadius(){
		return (float) (0.5*Math.sqrt(DefPlayerWidth*DefPlayerWidth+DefPlayerHeight*DefPlayerHeight));
	}*/

  //private void scalePlayer(){
  //	width = (int) (MinSize+(y*((MaxSize-MinSize)/game.getHeight())));
  //	height = (int) (MinSize+(y*((MaxSize-MinSize)/game.getHeight())));
  //	System.out.println("x= "+x+"  y="+y);
  //}

  private void adaptMode() {
    if (mode == 1) {
      up = handler.getKeyManager().P1_up;
      down = handler.getKeyManager().P1_down;
      left = handler.getKeyManager().P1_left;
      right = handler.getKeyManager().P1_right;
      sprint = handler.getKeyManager().P1_sprint;
      callBall = handler.getKeyManager().P1_dropOrCallBall;
      //to lines:
			/*leftBorder = handler.getWidth()*14/100;
			rightBorder = handler.getWidth()*83/100;
			upBorder = handler.getHeight()*50/100;
			downBorder = handler.getHeight()*91/100;
			*/
      //to walls:
      //leftBorder = handler.getWidth()*12/100;
      //rightBorder = handler.getWidth()*86/100;
      upBorder = handler.getHeight() * 50 / 100;
      downBorder = handler.getHeight() * 94 / 100;
    }

    if (mode == 2) {
      up = handler.getKeyManager().P2_up;
      down = handler.getKeyManager().P2_down;
      left = handler.getKeyManager().P2_left;
      right = handler.getKeyManager().P2_right;
      sprint = handler.getKeyManager().P2_sprint;
      callBall = handler.getKeyManager().P2_dropOrCallBall;
      //to lines:
			/*leftBorder = handler.getWidth()*14/100;
			rightBorder = handler.getWidth()*83/100;
			upBorder = handler.getHeight()*6/100;
			downBorder = handler.getHeight()*50/100 - DefPlayerHeight;
			*/
      //to walls:
      //leftBorder = handler.getWidth()*12/100;
      //rightBorder = handler.getWidth()*86/100;
      upBorder = handler.getHeight() * 4 / 100;
      downBorder = handler.getHeight() * 50 / 100 - DefPlayerHeight;
    }
  }

  public void calculateSprintSpeed() {
    if ((!sprintPrev) || (!isRunning)) {
      delta = 0;
    }
    if ((speed < sprintSpeed) && isRunning) {
      delta += sprintLoadIncrement;
    }
    speed = (float) (walkSpeed + delta);
    if (speed > sprintSpeed) {
      speed = (float) sprintSpeed;
    }
    //System.out.println("speed: " + speed + ", increment val: " + sprintLoadIncrement);
  }

  public void calculateSprintBallSpeed() {
    if ((!sprintPrev) || (!isRunning)) {
      delta = 0;
    }
    if ((speed < sprintBallSpeed) && (isRunning)) {
      delta += sprintBallLoadIncrement;
    }
    speed = (float) (walkSpeed + delta);
    if (speed > sprintBallSpeed) {
      speed = (float) sprintBallSpeed;
    }
    //System.out.println("speed: " + speed + ", increment val: " + sprintBallLoadIncrement);
  }

  private void getInput() {
    sprintPrev = sprint;
    adaptMode();
    if (mode == 1) {
      playerAngle = Math.PI;
    }
    if (mode == 2) {
      playerAngle = 0;
    }
    xMove = 0;
    yMove = 0;
//SPEED
    if ((sprint) && (!hasAball)) {
      calculateSprintSpeed();
    }
    if (sprint && hasAball) {
      calculateSprintBallSpeed();
    }
    if (!sprint) {
      speed = (float) walkSpeed;
    }
    if (stamina <= 10) {
      speed = (float) exhaustedSpeed;
    }
    if ((!up) && (!down) && (!left) && (!right)) {
      speed = 0;
    }


//KEYBOARD
    //keyboard and D-Pad up
    if (up) {
      playerAngle = Math.PI;
    }
    //keyboard and D-Pad down
    if (down) {
      playerAngle = 0;
    }
    //keyboard and D-Pad right
    if (right) {
      playerAngle = Math.PI / 2;
    }
    //keyboard and D-Pad left
    if (left) {
      playerAngle = 3 * Math.PI / 2;
    }
    if (up && right) {
      playerAngle = Math.toRadians(135);
    }
    if (up && left) {
      playerAngle = Math.toRadians(225);
    }
    if (down && right) {
      playerAngle = Math.toRadians(45);
    }
    if (down && left) {
      playerAngle = Math.toRadians(-45);
    }
    xMove = (float) (speed * Math.sin(playerAngle));
    yMove = (float) (speed * Math.cos(playerAngle));
    if (left && right) {
      xMove = 0;
    }
    if (up && down) {
      yMove = 0;
    }
    if (hasAball && callBall) {
      dropBall(1000);
    }

//ANALOG
    //P1 analog move
    if (KeyManager.controller1 != null) {
      if ((sprint) && (!hasAball)) {
        calculateSprintSpeed();
      }
      if (sprint && hasAball) {
        calculateSprintBallSpeed();
      }
      if (!sprint) {
        speed = (float) walkSpeed;
      }
      if (stamina <= 10) {
        speed = (float) exhaustedSpeed;
      }

      //if(!isRunning) speed=0;
      if (hasAball && callBall) {
        dropBall(1000);
      }
      c1x = handler.getKeyManager().getController1axisX();
      c1y = handler.getKeyManager().getController1axisY();
      if (mode == 1) {
        if (c1x < deadZone1 && c1x > -deadZone1 && c1y < deadZone1 && c1y > -deadZone1) {
          speed = 0;
        }

        if (c1x < deadZone1 && c1x > -deadZone1 && c1y < deadZone1 && c1y > -deadZone1) {
          playerAngle = Math.PI;
        }
        if ((c1x < deadZone1 && c1x > -deadZone1 && c1y < -deadZone1) || up) {
          playerAngle = Math.PI;
        }
        if ((c1x < deadZone1 && c1x > -deadZone1 && c1y > deadZone1) || down) {
          playerAngle = 0;
        }
        if ((c1x < -deadZone1 && c1y < deadZone1 && c1y > -deadZone1) || left) {
          playerAngle = -Math.PI / 2;
        }
        if (c1x < -deadZone1 && c1y < -deadZone1) {
          playerAngle = -Math.PI / 2 + Math.asin(c1y / Math.sqrt(c1x * c1x + c1y * c1y));
        }
        if (c1x < -deadZone1 && c1y > deadZone1) {
          playerAngle = -Math.PI / 2 + Math.asin(c1y / Math.sqrt(c1x * c1x + c1y * c1y));
        }
        if ((c1x > deadZone1 && c1y < deadZone1 && c1y > -deadZone1) || right) {
          playerAngle = Math.PI / 2;
        }
        if (c1x > deadZone1 && c1y < -deadZone1) {
          playerAngle = Math.PI / 2 - Math.asin(c1y / Math.sqrt(c1x * c1x + c1y * c1y));
        }
        if (c1x > deadZone1 && c1y > deadZone1) {
          playerAngle = Math.PI / 2 - Math.asin(c1y / Math.sqrt(c1x * c1x + c1y * c1y));
        }
        if (up && right) {
          playerAngle = Math.toRadians(135);
        }
        if (up && left) {
          playerAngle = Math.toRadians(225);
        }
        if (down && right) {
          playerAngle = Math.toRadians(45);
        }
        if (down && left) {
          playerAngle = Math.toRadians(-45);
        }
      }
      if (mode == 1) {
        //System.out.println("Player"+mode+", speed: "+speed);
        if (((handler.getKeyManager().getController1axisX() > deadZone1) || (handler.getKeyManager().getController1axisX() < -deadZone1))
            ||
            ((handler.getKeyManager().getController1axisY() > deadZone1) || (handler.getKeyManager().getController1axisY() < -deadZone1))) {
          xMove = (float) (speed * Math.sin(playerAngle));
          yMove = (float) (speed * Math.cos(playerAngle));
          //System.out.println("move angle: "+Math.toDegrees(Math.acos(handler.getKeyManager().getController1axisX()/(Math.sqrt(Math.pow(handler.getKeyManager().getController1axisX(),2) + Math.pow(handler.getKeyManager().getController1axisY(),2))))));
          //a = (int) (xMove);
          //b = (int) (yMove);
          //System.out.println("move angle casted: "+Math.toDegrees(Math.acos(a/Math.sqrt((a*a)+(b*b)))));
          //System.out.println("a: "+a+", b: "+b);
        }
        //if (c1x<deadZone1 && c1x>-deadZone1 && c1y<deadZone1 && c1y>-deadZone1) yMove=0;
      }
    }
    //P2 analog move
    if (KeyManager.controller2 != null) {
      if ((sprint) && (!hasAball)) {
        calculateSprintSpeed();
      }
      if (sprint && hasAball) {
        calculateSprintBallSpeed();
      }
      if (!sprint) {
        speed = (float) walkSpeed;
      }
      if (stamina <= 10) {
        speed = (float) exhaustedSpeed;
      }

      //if(!isRunning) speed=0;
      if (hasAball && callBall) {
        dropBall(1000);
      }
      c2x = handler.getKeyManager().getController2axisX();
      c2y = handler.getKeyManager().getController2axisY();
      if (mode == 2) {
        if (c2x < deadZone2 && c2x > -deadZone2 && c2y < deadZone2 && c2y > -deadZone2) {
          speed = 0;
        }

        if (c2x < deadZone2 && c2x > -deadZone2 && c2y < deadZone2 && c2y > -deadZone2) {
          playerAngle = 0;
        }
        if ((c2x < deadZone2 && c2x > -deadZone2 && c2y < -deadZone2) || up) {
          playerAngle = Math.PI;
        }
        if ((c2x < deadZone2 && c2x > -deadZone2 && c2y > deadZone2) || down) {
          playerAngle = 0;
        }
        if ((c2x < -deadZone2 && c2y < deadZone2 && c2y > -deadZone2) || left) {
          playerAngle = -Math.PI / 2;
        }
        if (c2x < -deadZone2 && c2y < -deadZone2) {
          playerAngle = -Math.PI / 2 + Math.asin(c2y / Math.sqrt(c2x * c2x + c2y * c2y));
        }
        if (c2x < -deadZone2 && c2y > deadZone2) {
          playerAngle = -Math.PI / 2 + Math.asin(c2y / Math.sqrt(c2x * c2x + c2y * c2y));
        }
        if ((c2x > deadZone2 && c2y < deadZone2 && c2y > -deadZone2) || right) {
          playerAngle = Math.PI / 2;
        }
        if (c2x > deadZone2 && c2y < -deadZone2) {
          playerAngle = Math.PI / 2 - Math.asin(c2y / Math.sqrt(c2x * c2x + c2y * c2y));
        }
        if (c2x > deadZone2 && c2y > deadZone2) {
          playerAngle = Math.PI / 2 - Math.asin(c2y / Math.sqrt(c2x * c2x + c2y * c2y));
        }
        if (up && right) {
          playerAngle = Math.toRadians(135);
        }
        if (up && left) {
          playerAngle = Math.toRadians(225);
        }
        if (down && right) {
          playerAngle = Math.toRadians(45);
        }
        if (down && left) {
          playerAngle = Math.toRadians(-45);
        }
      }
      if (mode == 2) {
        if (((handler.getKeyManager().getController2axisX() > deadZone2) || (handler.getKeyManager().getController2axisX() < -deadZone2))
            ||
            ((handler.getKeyManager().getController2axisY() > deadZone2) || (handler.getKeyManager().getController2axisY() < -deadZone2))) {
          xMove = (float) (speed * Math.sin(playerAngle));
          yMove = (float) (speed * Math.cos(playerAngle));
        }
        //if (c2x<deadZone2 && c2x>-deadZone2 && c2y<deadZone2 && c2y>-deadZone2) yMove=0;
      }
    }
  }

  public void move() {
	/*	if (  //goals limits mo¿liwe ¿e usunê to po dodaniu obiektów s³upków i stworzeniu kontaktu z graczami
				((y+yMove) > ((handler.getHeight()*91/100)-DefPlayerHeight-0.042*goal.getGoalHeight())||(y+yMove) < ((handler.getHeight()*6/100)+0.42*goal.getGoalHeight()))&&
				(((x+xMove) > (handler.getWidth()/2-DefPlayerWidth+0.36*goal.getGoalWidth()))&&((x+xMove) < (handler.getWidth()/2+0.36*goal.getGoalWidth()+2*goal.getRadius()))
				||
				((x+xMove) > (handler.getWidth()/2-DefPlayerWidth-0.4*goal.getGoalWidth()))&&((x+xMove) < (handler.getWidth()/2-0.4*goal.getGoalWidth()+2*goal.getRadius())))
				) 
		{
		}else {*/
    moveX();
    moveY();
  }

  public void moveX() {
//	if (((x+xMove) > leftBorder) && ((x+xMove) < rightBorder) && moveXallowed)
//	{
    //x+=xMove;
    //System.out.println("xMove= "+xMove+"  x="+x);
    if (moveXallowedOrb && moveXallowedFlat) {
      tempX += xMove;
      x = (tempX);//x=(int)(tempX);
      //tempX=x;
    }
//	}

  }

  public void moveY() {
    if (((y + yMove) > upBorder) && ((y + yMove) < downBorder) && moveYallowedOrb && moveYallowedFlat) {
      //y+=yMove;

      tempY += yMove;
      y = (tempY);//y=(int)(tempY);
      //tempY=y;
    }

  }
//SETTERS and GETTERS	

  public double getTempX() {
    return tempX;
  }

  public double getTempY() {
    return tempY;
  }

  public float getxMove() {
    return xMove;
  }

  public void setxMove(float xMove) {
    this.xMove = xMove;
  }

  public float getyMove() {
    return yMove;
  }

  public void setyMove(float yMove) {
    this.yMove = yMove;
  }

  public void setXMoveAllowedOrb(boolean permission) {
    moveXallowedOrb = permission;
  }

  public void setYMoveAllowedOrb(boolean permission) {
    moveYallowedOrb = permission;
  }

  public boolean getXMoveAllowedOrb() {
    return moveXallowedOrb;
  }

  public boolean getYMoveAllowedOrb() {
    return moveYallowedOrb;
  }

  public void setXMoveAllowedFlat(boolean permission) {
    moveXallowedFlat = permission;
  }

  public void setYMoveAllowedFlat(boolean permission) {
    moveYallowedFlat = permission;
  }

  public boolean getXMoveAllowedFlat() {
    return moveXallowedFlat;
  }

  public boolean getYMoveAllowedFlat() {
    return moveYallowedFlat;
  }

  public void setHasABall(boolean flag) {
    hasAball = flag;
  }

  public void setSetForShot(boolean flag) {
    setForShot = flag;
  }

  public void setShotCancellation(boolean flag) {
    shotCancellation = flag;
  }

  public boolean getHasABall() {
    return hasAball;
  }

  public void setScore(int scored) {
    score = scored;
  }

  public int getScore() {
    return score;
  }

  public double getPlayerAngle() {
    return playerAngle;
  }

  public double getStaminaForceFactor() {
    return staminaForceFactor;
  }

  public double getStamina() {
    return stamina;
  }

  public double getStaminaInitial() {
    return staminaInitial;
  }

  public boolean getCallBall() {
    return callBall;
  }

  public double getPlayerMass() {
    return playerMass;
  }

  public void setPlayerMass(double playerMass) {
    this.playerMass = playerMass;
  }

  public double getFootMass() {
    return footMass;
  }

  public void setFootMass(double footMass) {
    this.footMass = footMass;
  }

  public boolean getIsRunning() {
    return isRunning;
  }
}
