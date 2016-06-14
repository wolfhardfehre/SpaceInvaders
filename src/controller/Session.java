package controller;

import models.Sprite;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;


public class Session {

    private static Session session = new Session();
    private HashMap<String, Sprite> sprites = new HashMap<>();

    public static Session getInstance() {
        return session;
    }

    public Sprite getSprite(String path) {
        if (sprites.get(path) != null) {
            return sprites.get(path);
        }
        return buildSprite(path);
    }

    private Sprite buildSprite(String path) {
        BufferedImage sourceImage = null;
        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            if (url == null) {
                fail("Can't find ref: " + path);
            }
            assert url != null;
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            fail("Failed to load: " + path);
        }
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        assert sourceImage != null;
        Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
        image.getGraphics().drawImage(sourceImage, 0, 0, null);
        Sprite sprite = new Sprite(image);
        sprites.put(path, sprite);
        return sprite;
    }

    private void fail(String message) {
        System.err.println(message);
        System.exit(0);
    }
}