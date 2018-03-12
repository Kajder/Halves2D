package contact;

import entities.Entity;


public class ContactManager {
private Entity body1;
private Entity body2;
protected double distance, distanceX, distanceY;
	
	
	public ContactManager(Entity body1, Entity body2){
	this.body1 = body1;
	this.body2 = body2;
	}
	
	public double checkDistance(){
		distanceX = body1.getCenterX()-body2.getCenterX();
		distanceY = body1.getCenterY()-body2.getCenterY();
		distance = Math.sqrt((distanceX*distanceX)+(distanceY*distanceY));
		return distance;
	}
	
	
	//public void tick(){	
	//}
	
	
	
	
}
