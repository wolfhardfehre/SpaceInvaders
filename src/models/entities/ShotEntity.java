package models.entities;


import controller.Game;
import models.Constants;

public class ShotEntity extends Entity {

    private final Game game;
    private boolean used = false;

    public ShotEntity(Game game, String ref, int x, int y) {
        super(ref, x, y);
        this.game = game;
        dy = Constants.SHOT_SPEED;
    }

    public void move(long delta) {
        super.move(delta);
        if (y < -100) {
            game.removeEntity(this);
        }
    }

    @Override
    public void collidedWidth(Entity other) {
        if (used) {
            return;
        }

        if (other instanceof AlienEntity) {
            game.removeEntity(this);
            game.removeEntity(other);
            game.notifyAlienKilled();
            used = true;
        }
    }
}
