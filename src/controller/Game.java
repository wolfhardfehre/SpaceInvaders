package controller;


import models.Constants;
import models.entities.Entity;
import views.SpaceCanvas;
import views.SpaceWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game {

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
        // TODO bind input keys
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
        // TODO ship
        entities.add(ship);
        for (int i=0; i<alienCount; i++) {
            int column = i % Constants.ALIEN_COLUMNS;
            int row = i / Constants.ALIEN_COLUMNS;
            int dx = Constants.ALIEN_X_GMARGIN + column * Constants.ALIEN_X_SIZE;
            int dy = Constants.ALIEN_Y_MARGIN + row * Constants.ALIEN_Y_SIZE;
            // TODO alien
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
        // TODO set wait
    }

    public void notifyWin() {
        message = "Well done! You won!";
        paused = true;
        // TODO set wait
    }

    public void notifyAlienKilled() {
        // TODO alien killed
    }

    public void tryToFire() {
        // TODO fire
    }

    private void moveEntities(long delta) {
        // TODO move
    }

    private void drawEntities() {
        // TODO draw
    }

    private void checkCollisions() {
        // TODO collision
    }

    private void removal() {
        entities.removeAll(removeList);
        removeList.clear();
    }

    private void doLogic() {
        // TODO step logic
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
        // TODO move
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
}
