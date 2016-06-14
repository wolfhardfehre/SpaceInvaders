package models.entities;


import controller.Game;
import models.Constants;


public class AlienEntity extends Entity {
    private Game game;

    public AlienEntity(Game game, String path, int x, int y) {
        super(path, x, y);
        this.game = game;
        dx = -Constants.ALIEN_MOVEMENT_SPEED;
    }

    public void move(long delta) {
        if (dx < 0 && x < 10 || dx > 0 && x > 750) {
            game.updateLogic();
        }
        super.move(delta);
    }

    public void doLogic() {
        dx = -dx;
        y += 10;
        if (y > 570) {
            game.notifyDeath();
        }
    }

    public void collidedWith(Entity other) {
        // collisions with aliens are handled elsewhere
    }
}
