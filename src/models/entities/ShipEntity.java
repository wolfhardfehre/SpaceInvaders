package models.entities;


import controller.Game;

public class ShipEntity extends Entity {

    private final Game game;

    public ShipEntity(Game game, String ref, int x, int y) {
        super(ref, x, y);
        this.game = game;
    }

    public void move(long delta) {
        if (dx < 0 && x < 10 || dx > 0 && x > 750) {
            return;
        }
        super.move(delta);
    }

    @Override
    public void collidedWidth(Entity other) {
        if (other instanceof AlienEntity) {
            game.notifyDeath();
        }
    }
}
