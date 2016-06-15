package controller;


import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import models.Constants;
import models.entities.AlienEntity;
import models.entities.Entity;
import models.entities.ShipEntity;
import models.entities.ShotEntity;
import views.SpaceCanvas;
import views.SpaceWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game implements KeyInputHandler.OnKeyPressed {

    private final SpaceCanvas canvas;
    private BufferStrategy strategy;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Entity> removeList = new ArrayList<>();
    private Entity ship;
    private long lastFire = 0;
    private int alienCount;
    private String message = "";
    private boolean paused = true;
    private boolean running = true;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean firePressed = false;
    private boolean logicRequiredThisLoop = false;
    private Graphics2D graphics;

    public static void main(String[] args) {
        Game game = new Game();
        game.gameLoop();
    }

    public Game() {
        KeyInputHandler.bind(this);
        SpaceWindow window = new SpaceWindow("Space Invaders 101");
        canvas = window.getCanvas();
        strategy = canvas.getStrategy();
        initEntities();
    }

    public void gameLoop() {
        long before = System.currentTimeMillis();

        while (running) {
            long now = System.currentTimeMillis();
            graphics = canvas.getGraphics2D();
            moveEntities(now - before);
            drawEntities();
            checkCollisions();
            removal();
            doLogic();
            message();
            redraw();
            moveShip();
            fire();
            delay();
            before = now;
        }
    }

    private void initEntities() {
        alienCount = Constants.ALIEN_COUNT;
        ship = new ShipEntity(this, "images/ship.gif", Constants.SHIP_START_X, Constants.SHIP_START_Y);
        entities.add(ship);
        for (int i=0; i<alienCount; i++) {
            int column = i % Constants.ALIEN_COLUMNS;
            int row = i / Constants.ALIEN_COLUMNS;
            int dx = Constants.ALIEN_X_GMARGIN + column * Constants.ALIEN_X_SIZE;
            int dy = Constants.ALIEN_Y_MARGIN + row * Constants.ALIEN_Y_SIZE;
            Entity alien = new AlienEntity(this, "images/alien.gif", dx, dy);
            entities.add(alien);
        }
    }

    public void updateLogic() {
        logicRequiredThisLoop = true;
    }

    public void removeEntity(Entity entity) {
        removeList.add(entity);
    }

    public void notifyDeath() {
        message = "Oh no! They got you, try again?";
        paused = true;
        KeyInputHandler.setWaiting(true);
    }

    public void notifyWin() {
        message = "Well done! You won!";
        paused = true;
        KeyInputHandler.setWaiting(true);
    }

    public void notifyAlienKilled() {
        alienCount--;
        if (alienCount == 0) {
            notifyWin();
        }
        for (Entity entity : entities) {
            if (entity instanceof AlienEntity) {
                entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
            }
        }
    }

    public void tryToFire() {
        if (System.currentTimeMillis() - lastFire < Constants.FIRE_INTERVAL) {
            return;
        }
        lastFire = System.currentTimeMillis();
        ShotEntity shot = new ShotEntity(this, "images/shot.gif", ship.getX() + 10, ship.getY() - 30);
        entities.add(shot);
    }

    private void moveEntities(long delta) {
        if (!paused) {
            for (Entity entity : entities) {
                entity.move(delta);
            }
        }
    }

    private void drawEntities() {
        for (Entity entity : entities) {
            entity.draw(graphics);
        }
    }

    private void checkCollisions() {
        for (int p=0; p<entities.size(); p++) {
            for (int s=p+1; s<entities.size(); s++) {
                Entity me = entities.get(p);
                Entity him = entities.get(s);

                if (me.collidesWith(him)) {
                    me.collidedWidth(him);
                    him.collidedWidth(me);
                }
            }
        }
    }

    private void removal() {
        entities.removeAll(removeList);
        removeList.clear();
    }

    private void doLogic() {
        if (logicRequiredThisLoop) {
            for (Entity entity : entities) {
                entity.doLogic();
            }
            logicRequiredThisLoop = false;
        }
    }

    private void message() {
        if (paused) {
            startText();
        }
    }

    private void redraw() {
        graphics.dispose();
        strategy.show();
    }

    private void moveShip() {
        ship.setHorizontalMovement(0);
        if (leftPressed && !rightPressed) {
            ship.setHorizontalMovement(-Constants.SHIP_SPEED);
        } else if (rightPressed && !leftPressed) {
            ship.setHorizontalMovement(Constants.SHIP_SPEED);
        }
    }

    private void fire() {
        if (firePressed) {
            tryToFire();
        }
    }

    private void delay() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            System.out.println("Thread didn't sleep!");
        }
    }

    private void startText() {
        graphics.setColor(Color.white);
        graphics.drawString(message, (Constants.WINDOW_WIDTH - graphics.getFontMetrics().stringWidth(message)) / 2, 250);
        graphics.drawString("Press any key", (Constants.WINDOW_WIDTH - graphics.getFontMetrics().stringWidth("Press any key")) / 2, 300);
    }

    @Override
    public void onLeft(boolean left) {
        this.leftPressed = left;
    }

    @Override
    public void onRight(boolean right) {
        this.rightPressed = right;
    }

    @Override
    public void onFire(boolean fire) {
        this.firePressed = fire;
    }

    @Override
    public void onStart() {
        entities.clear();
        initEntities();

        paused = false;
        leftPressed = false;
        rightPressed = false;
        firePressed = false;
    }
}
