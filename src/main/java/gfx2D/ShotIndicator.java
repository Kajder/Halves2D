package gfx2D;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import contact.BallPlayerContact;
import game2d.Handler;

public class ShotIndicator {

    private Handler handler;
    private BallPlayerContact ballVsplayer;
    private Graphics2D g2d;
    private Path2D path = new Path2D.Float();
    private BufferedImage shoe, shoeTrans, halo, ballIndicatorBig, gradient;

    private int x, y; //x and y of ballIndicator/halo center - different for different players
    private int diameterBigBall = 30;
    private int shoeWidth = 12, shoeHeight = 36;
    private int mode;
    private int gradientWidth = 25, gradientHeight = 100, distanceBallGradientX, distanceBallGradientY, indicatorBallPosition;
    private int R;
    private double newAngle, newW, forceVal, forceValMax;
    private float sinA, cosA, sinT, cosT, sinAB, cosAB, gammaB, gammaC;
    //

    public ShotIndicator(Handler handler, BallPlayerContact ballVsplayer, BufferedImage ballIndicatorBig, BufferedImage shoeTrans, BufferedImage halo, BufferedImage shoe, BufferedImage gradient, int x, int y) {
        this.handler = handler;
        this.ballVsplayer = ballVsplayer;
        //this.g2d = g2d;
        this.shoe = shoe;
        this.halo = halo;
        this.ballIndicatorBig = ballIndicatorBig;
        this.shoeTrans = shoeTrans;
        this.gradient = gradient;
        this.x = x;
        this.y = y;
    }


    public void tickNrender(Graphics2D g2d) {
        newAngle = (ballVsplayer.getDirectionInd() / (ballVsplayer.getBall().getAngleMaxTime() * handler.getFPS())) * Math.PI / 3;
        newW = -(ballVsplayer.getRotationInd() / (ballVsplayer.getBall().getwMaxTime() * handler.getFPS())) * Math.PI / 2;
        forceVal = (int) (60 * Math.log(2 * ballVsplayer.getForceInd() + 0.3) + 70);
        forceValMax = (60 * Math.log(2 * (ballVsplayer.getBall().getforceMaxTime() * handler.getFPS()) + 0.3) + 70);
        mode = ballVsplayer.getPlayer().getMode();
        distanceBallGradientX = -gradientWidth / 2;
        distanceBallGradientY = -5 * diameterBigBall;
        indicatorBallPosition = (int) ((distanceBallGradientY + gradientHeight - gradientWidth / 4) - (gradientHeight * forceVal / forceValMax));

        if (mode == 2) {
            newAngle = -newAngle + Math.PI;
            newW = -newW;
            distanceBallGradientY = -distanceBallGradientY - gradientHeight;
            indicatorBallPosition = (int) ((distanceBallGradientY - gradientWidth / 4) + (gradientHeight * forceVal / forceValMax));
        }


        //ballIndicator
        g2d.drawImage(ballIndicatorBig, x - diameterBigBall / 2, y - diameterBigBall / 2, diameterBigBall, diameterBigBall, null);
        //halo - licz tylko gdy newAngle lub newW si? zmieni?
        //if newW or newAngle has changed
        g2d.setClip(calculateHaloPath());
        //}
        g2d.drawImage(halo, x - (diameterBigBall + 8) / 2, y - (diameterBigBall + 8) / 2, diameterBigBall + 8, diameterBigBall + 8, null);
        g2d.setClip(null);
        path.reset();

        //power, direction and rotation indicators

        //transparent shoe
        g2d.rotate(newAngle + newW, x, y);//
        g2d.drawImage(shoeTrans, x - diameterBigBall / 5, y + diameterBigBall / 2, shoeWidth, shoeHeight, null);
        g2d.rotate(-newAngle - newW, x, y);
        //shoe
        g2d.rotate(newAngle, x, y);//
        g2d.drawImage(shoe, x - diameterBigBall / 5, y + diameterBigBall / 2, shoeWidth, shoeHeight, null);
        g2d.rotate(-newAngle, x, y);
        //power
        g2d.drawImage(gradient, x + distanceBallGradientX, y + distanceBallGradientY, gradientWidth, gradientHeight, null);
        g2d.drawImage(ballIndicatorBig, x + distanceBallGradientX + gradientWidth / 4, y + indicatorBallPosition, gradientWidth / 2, gradientWidth / 2, null);
    }


    private Path2D calculateHaloPath() {

        R = halo.getHeight() / 2;
        sinA = (float) Math.sin(-(newAngle));
        cosA = (float) Math.cos(-(newAngle));
        sinT = (float) Math.sin(-(newAngle + newW / 2));
        cosT = (float) Math.cos(-(newAngle + newW / 2));
        sinAB = (float) Math.sin(-(newAngle + newW));
        cosAB = (float) Math.cos(-(newAngle + newW));//
        gammaB = (float) (sinT - sinT * cosT * cosA + sinA * cosT * cosT);
        gammaC = (float) (sinT - sinT * cosT * cosAB + sinAB * cosT * cosT);

        //System.out.println("alpha = "+newAngle*180/Math.PI);
        //System.out.println("beta = "+newW*180/Math.PI);
        path.moveTo(x, y); //point A, center of the halo
        //System.out.println("A=("+x+","+y+")");
        path.lineTo(x + R * sinAB, y + R * cosAB); //point C
        //System.out.println("C=("+(x+R*sinAB)+","+(y+R*cosAB)+")");
        path.lineTo(x + R * gammaC, y + R * (1 - gammaC * sinT) / cosT); //point C2
        //System.out.println("C2=("+(x+R*gammaC)+","+(y+R*(1-gammaC*sinT)/cosT)+")");
        path.lineTo(x + R * gammaB, y + R * (1 - gammaB * sinT) / cosT); //point B2
        //System.out.println("B2=("+(x+R*gammaB)+","+(y+R*(1-gammaB*sinT)/cosT)+")");
        path.lineTo(x + R * sinA, y + R * cosA); //point B
        //System.out.println("B=("+(x+R*sinA)+","+(y+R*cosA)+")");
        path.closePath();

        return path;
    }


}
