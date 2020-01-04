package contact;

import entities.Entity;
import entities.creatures.Player;

public class PlayerVsOrb extends ContactManager {
    Player player;
    Entity body;
    double distanceToBeX, distanceToBeY, distanceToBe;

    public PlayerVsOrb(Player player, Entity body) {
        super(player, body);
        this.player = player;
        this.body = body;
    }

    public void tick() {
        distanceToBeX = Math.abs((player.getCenterX() + player.getxMove()) - body.getCenterX());
        distanceToBeY = Math.abs((player.getCenterY() + player.getyMove()) - body.getCenterY());
        distanceToBe = Math.sqrt(Math.pow(distanceToBeX, 2) + Math.pow(distanceToBeY, 2));

        if ((distanceToBeX + 5 < (player.getRadius() + body.getRadius())) && ((distanceToBeY + 8 < (player.getRadius() + body.getRadius())))) {
            player.setXMoveAllowedOrb(false);
            player.setYMoveAllowedOrb(false);
        }

        if (
                ((distanceToBeX > Math.abs(player.getCenterX() - body.getCenterX())) && distanceToBe < 3 * (player.getRadius() + body.getRadius()))
                        ||
                        ((distanceToBe > (player.getRadius() + body.getRadius())) && distanceToBe < 3 * (player.getRadius() + body.getRadius()))
        ) {
            player.setXMoveAllowedOrb(true);
        }

        if (
                ((distanceToBeY > Math.abs(player.getCenterY() - body.getCenterY())) && distanceToBe < 3 * (player.getRadius() + body.getRadius()))
                        ||
                        ((distanceToBe > (player.getRadius() + body.getRadius())) && distanceToBe < 3 * (player.getRadius() + body.getRadius()))
        ) {
            player.setYMoveAllowedOrb(true);
        }

    }


}
