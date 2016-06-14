package models;


public interface Constants {

    ///////////////////////////////
    // Cartesian coordinate system
    //
    //     P(0,0)     x
    //        * ----------->
    //        |
    //      y |
    //        |
    //        V
    //
    ///////////////////////////////

    // Views
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 600;

    // Entities
    int ALIEN_COUNT = 60;

    int ALIEN_MOVEMENT_SPEED = 75;
    int ALIEN_X_SIZE = 50;
    int ALIEN_Y_SIZE = 30;
    int ALIEN_X_MARGIN = 100;
    int ALIEN_Y_MARGIN = 50;

    int GAME_WIDTH = WINDOW_WIDTH - 2 * ALIEN_X_MARGIN;
    int ALIEN_X_GMARGIN = ALIEN_X_MARGIN + (GAME_WIDTH % ALIEN_X_SIZE) / 2;
    int ALIEN_COLUMNS = GAME_WIDTH / ALIEN_X_SIZE;

    int FIRE_INTERVAL = 500;
    int SHOT_SPEED = -300;

    int SHIP_START_X = 370;
    int SHIP_START_Y = 550;
    int SHIP_SPEED = 300;

}
