package gametemplate.gameobject;

import gametemplate.Tetris;
import gametemplate.graphics.Rectangle;
import gametemplate.graphics.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TetrisBlock extends GameObject {
    Rectangle brick = null;

    public TetrisBlock() throws MaxObjectsException {
        brick = new Rectangle();
        brick.setSize(new Vector2(Tetris.SCALE, Tetris.SCALE));
    }

    public TetrisBlock(TetrisBlock copy) throws MaxObjectsException {
        brick = new Rectangle();
        brick.setSize(new Vector2(Tetris.SCALE, Tetris.SCALE));
        brick.setColor(copy.brick.getColor());
    }

    public Vector2 getPosition() { return brick.getPosition(); }

    public void setColor(Color color) {
        brick.setColor(color);
    }

    public void dropOne() {
        brick.setPosition(new Vector2(brick.getPosition().getX(), brick.getPosition().getY() + Tetris.SCALE));
        bounding = brick.getRect();
    }

    public static Vector2 getCoordinatePosition(double x, double y) {
        return new Vector2(Tetris.X_ORIGIN + Tetris.SCALE * x, Tetris.Y_ORIGIN + Tetris.SCALE * y);
    }

    public static Vector2 getGridPosition(double x, double y) {
        return new Vector2((x - Tetris.X_ORIGIN) / Tetris.SCALE, (y - Tetris.Y_ORIGIN) / Tetris.SCALE);
    }

    public void setGridPosition(double x, double y) {
        brick.setPosition(TetrisBlock.getCoordinatePosition(x, y));    
        bounding = brick.getRect();
    }

    @Override
    public void update() {
        super.update();
        // why is this here?
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        brick.draw(gc);
    }
}
