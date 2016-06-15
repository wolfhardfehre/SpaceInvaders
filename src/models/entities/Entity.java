package models.entities;

import controller.Game;
import controller.Session;
import models.Sprite;

import java.awt.*;

public abstract class Entity {

    protected double y;
    protected double x;
    protected double dy;
    protected double dx;
    private Sprite sprite;
    private Rectangle me = new Rectangle();
    private Rectangle they = new Rectangle();

    public Entity(String ref, int x, int y) {
        Session session = Session.getInstance();
        this.sprite = session.getSprite(ref);
        this.x = x;
        this.y = y;
    }

    public void move(long delta) {
        x += (delta * dx) / 1000;
        y += (delta * dy) / 1000;
    }

    public void doLogic() {}

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

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public void draw(Graphics g) {
        sprite.draw(g, getX(), getY());
    }

    public boolean collidesWith(Entity other) {
        me.setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        they.setBounds(other.getX(), other.getY(), other.sprite.getWidth(), other.sprite.getHeight());
        return me.intersects(they);
    }

    public abstract void collidedWidth(Entity other);
}
