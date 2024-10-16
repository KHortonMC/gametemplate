package gametemplate;

import gametemplate.gameobject.StaticBrick;
import gametemplate.gameobject.TetrisBlock;
import gametemplate.gameobject.TetrisPiece;
import gametemplate.graphics.Rect;

public class Tetris {
    public static final double SCALE = 30.0;
    public static final double X_ORIGIN = SCALE;
    public static final double Y_ORIGIN = 0;
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;
    public static final int SCREEN_WIDTH = (int) ((GRID_WIDTH + 2) * SCALE);
    public static final int SCREEN_HEIGHT = (int) ((GRID_HEIGHT + 1) * SCALE);

    private Tetris() {}

    @SuppressWarnings("exports")
    public static TetrisPiece activePiece = null;

    public static void buildObjects() {
        for (int i = 0; i < GRID_WIDTH * GRID_HEIGHT; i++) {
            new TetrisBlock();
        }

        activePiece = new TetrisPiece();

        Rect gamebounds = new Rect(X_ORIGIN, Y_ORIGIN, GRID_WIDTH * SCALE, GRID_HEIGHT * SCALE);

        new StaticBrick(new Rect(gamebounds.getX() - SCALE, gamebounds.getY(), SCALE, gamebounds.getH()));
        new StaticBrick(new Rect(gamebounds.getX()+gamebounds.getW(), gamebounds.getY(), SCALE, gamebounds.getH()));
        new StaticBrick(new Rect(gamebounds.getX() - SCALE, gamebounds.getY() + gamebounds.getH(), gamebounds.getW() + SCALE*2, SCALE));     
    }
}
