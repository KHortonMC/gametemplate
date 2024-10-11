package gametemplate;

import gametemplate.gameobject.MaxObjectsException;
import gametemplate.gameobject.StaticBrick;
import gametemplate.gameobject.TetrisPiece;
import gametemplate.gameobject.TetrisPiece.Style;
import gametemplate.graphics.Rect;

public class Tetris {
    public static final double SCALE = 15.0;
    public static final double X_ORIGIN = 100.0;
    public static final double Y_ORIGIN = 50.0;
    public static final int GRID_WIDTH = 12;
    public static final int GRID_HEIGHT = 30;

    public static TetrisPiece activePiece = null;

    public static void buildObjects() throws MaxObjectsException {
          try {
            activePiece = new TetrisPiece(Style.ZEE, false);

            Rect gamebounds = new Rect(X_ORIGIN, Y_ORIGIN, GRID_WIDTH * SCALE, GRID_HEIGHT * SCALE);

            new StaticBrick(new Rect(gamebounds.getX() - SCALE, gamebounds.getY(), SCALE, gamebounds.getH()));
            new StaticBrick(new Rect(gamebounds.getX()+gamebounds.getW(), gamebounds.getY(), SCALE, gamebounds.getH()));
            new StaticBrick(new Rect(gamebounds.getX(), gamebounds.getY() + gamebounds.getH(), gamebounds.getW(), SCALE));

        } catch (MaxObjectsException e) {
            throw new MaxObjectsException("Increase MAX_OBJECTS");
        }      
    }
}
