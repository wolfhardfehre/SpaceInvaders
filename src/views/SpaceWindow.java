package views;


import models.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SpaceWindow extends JFrame {

    private SpaceCanvas canvas;

    public SpaceWindow(String draw) {
        super(draw);
        initWindow();
    }

    protected void initWindow() {
        initPanel();
        pack();
        setResizable(false);
        setVisible(true);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void initPanel() {
        JPanel panel = (JPanel) getContentPane();
        Dimension dimension = new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        panel.setPreferredSize(dimension);
        panel.setLayout(null);
        canvas = new SpaceCanvas();
        panel.add(canvas);
    }

    public SpaceCanvas getCanvas() {
        return canvas;
    }
}
