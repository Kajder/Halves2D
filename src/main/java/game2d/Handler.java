package game2d;

import input.KeyManager;
import input.MouseManager;
import model.Parameters;

public class Handler {

    private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public int getFPS() {
        return game.getFPS();
    }

    public float getCompensation() {
        return game.getCompensation();
    }

    public int getWidth() {
        return game.getWidth();
    }

    public int getPitchWidth() {
        return game.getPitchWidth();
    }

    public int getHeight() {
        return game.getHeight();
    }

    public Game getGame() {
        return game;
    }

    public Parameters getParameters() {
        return getGame().getParameters();
    }
}
