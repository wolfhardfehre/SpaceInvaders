package controller;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputHandler extends KeyAdapter {

    private static boolean waitingForKeyPress = true;
    private static OnKeyPressed callBack;

    public interface OnKeyPressed {
        void onLeft(boolean left);
        void onRight(boolean right);
        void onFire(boolean fire);
        void onStart();
    }

    public static void bind(Object object) {
        KeyInputHandler.callBack = (OnKeyPressed) object;
    }

    public static void setWaitingForKeyPress(boolean wait) {
        KeyInputHandler.waitingForKeyPress = wait;
    }

    public void keyPressed(KeyEvent e) {
        if (!waitingForKeyPress) {
            call(e.getKeyCode(), true);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (!waitingForKeyPress) {
            call(e.getKeyCode(), false);
        }
    }

    private void call(int keyCode, boolean pressed) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                callBack.onLeft(pressed); break;
            case KeyEvent.VK_RIGHT:
                callBack.onRight(pressed); break;
            case KeyEvent.VK_SPACE:
                callBack.onFire(pressed); break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
    }

    public void keyTyped(KeyEvent e) {
        if (waitingForKeyPress) {
            callBack.onStart();
            waitingForKeyPress = false;
        }

    }
}
