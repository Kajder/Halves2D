package contact;

import entities.Entity;
import entities.creatures.Player;

public class PlayerFlatContact extends ContactManager{
	
	Player player;
	Entity body;
	boolean isHorizontal;
	private boolean moveXAllowedFlat=true, moveYAllowedFlat=true;
	

	public PlayerFlatContact(Player player, Entity body, boolean isHorizontal) {
		super(player, body);
		// TODO Auto-generated constructor stub
		this.player=player;
		this.body=body;
		this.isHorizontal = isHorizontal;
	}

	public double findDistanceToBeX(){
		if (player.getCenterX()>=body.getCenterX()) return player.getCenterX()-player.getRadius()+player.getxMove()-body.getCenterX()-body.getWidth()/2+0.5;
		if (player.getCenterX()<body.getCenterX()) return body.getCenterX()-body.getWidth()/2-player.getCenterX()-player.getRadius()-player.getxMove()+1.5;
		return 1;
	}
	public double findDistanceToBeY(){
		if (player.getCenterY()>=body.getCenterY()) return player.getCenterY()-player.getRadius()+player.getyMove()-body.getCenterY()-body.getHeight()/2+6.5;
		if (player.getCenterY()<body.getCenterY()) return body.getCenterY()-body.getHeight()/2-player.getCenterY()-player.getRadius()-player.getyMove()+5.5;
		return 1;
	}
	public boolean isMovingAwayX(){
		if ((player.getCenterX()>=body.getCenterX())&&(player.getxMove()>0)) return true;
		if ((player.getCenterX()<=body.getCenterX())&&(player.getxMove()<0)) return true;
		return false;
	}
	public boolean isMovingAwayY(){
		if ((player.getCenterX()>=body.getCenterX())&&(player.getxMove()>0)) return true;
		if ((player.getCenterX()<=body.getCenterX())&&(player.getxMove()<0)) return true;
		return false;
	}
	
	public void tick(){
		if ((isHorizontal)&&(findDistanceToBeX()<=0)&&(findDistanceToBeY()<=0)) {
			moveYAllowedFlat = false; //osobne flagi dla X i Y. tutaj blokowany tylko Y. Po drugie, dobrze wymierzy� odleg�o�� zerow� dla zawodnik�w
		}
		if ((isHorizontal==false)&&(findDistanceToBeX()<=0)&&(findDistanceToBeY()<=0)) {
			moveXAllowedFlat = false; //tutaj blokowany tylko X
		}
		if ((findDistanceToBeX()>0)||isMovingAwayX()){
			moveXAllowedFlat = true; //odblokowany tylko x
			moveYAllowedFlat = true;
		}
	
		if ((findDistanceToBeY()>0)||isMovingAwayX()){
			moveYAllowedFlat = true; //odblokowany tylko y
			moveXAllowedFlat = true;
		}
		
		
		//System.out.println("distance to be x: "+findDistanceToBeX()+", distance to be y: "+findDistanceToBeY()+" ,player xMove: "+player.getxMove()+" ,player yMove: "+player.getyMove());
	}
	
	public boolean getMoveXAllowedFlat(){
		return moveXAllowedFlat;
	}
	public boolean getMoveYAllowedFlat(){
		return moveYAllowedFlat;
	}	
	
}
