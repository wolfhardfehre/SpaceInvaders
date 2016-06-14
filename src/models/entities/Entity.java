package models.entities;

import models.Sprite;
import controller.Session;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected Sprite sprite;
    private Rectangle me = new Rectangle();
    private Rectangle him = new Rectangle();

    public Entity(String ref, int x, int y) {
        this.sprite = Session.getInstance().getSprite(ref);
        this.x = x;
        this.y = y;
    }

    public void move(long delta) {
        x += (delta * dx) / 1000;
        y += (delta * dy) / 1000;
    }

    public void setHorizontalMovement(double dx) {
        this.dx = dx;
    }

    public void setVerticalMovement(double dy) {
        this.dy = dy;
    }

    public double getHorizontalMovement() {
        return dx;
    }

    public double getVerticalMovement() {
        return dy;
    }

    public void draw(Graphics g) {
        sprite.draw(g,(int) x,(int) y);
    }

    public void doLogic() {}

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public boolean collidesWith(Entity other) {
        me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
        him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
        return me.intersects(him);
    }

    public abstract void collidedWith(Entity other);
}
