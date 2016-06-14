package views;

import controller.KeyInputHandler;
import models.Constants;

import java.awt.*;
import java.awt.image.BufferStrategy;


public class SpaceCanvas extends Canvas {

    private BufferStrategy strategy;

    public SpaceCanvas() {
        super();
        setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setIgnoreRepaint(true);
        addKeyListener(new KeyInputHandler());
        requestFocus();
    }

    public BufferStrategy getStrategy() {
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        return strategy;
    }

    public Graphics2D getGraphics2D() {
        Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        return graphics;
    }
}
