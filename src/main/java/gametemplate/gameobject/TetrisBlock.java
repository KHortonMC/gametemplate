package gametemplate.gameobject;

import gametemplate.graphics.Rectangle;
import gametemplate.graphics.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TetrisBlock extends GameObject {
    Rectangle brick = null;

    private static final double SCALE = 10.0;
    private static final double X_ORIGIN = 50.0;
    private static final double Y_ORIGIN = 200.0;

    public TetrisBlock() throws MaxObjectsException {
        brick = new Rectangle();
        brick.setSize(new Vector2(SCALE, SCALE));
    }

    public TetrisBlock(TetrisBlock copy) throws MaxObjectsException {
        brick = new Rectangle();
        brick.setSize(new Vector2(SCALE, SCALE));
        brick.setColor(copy.brick.getColor());
    }

    public void setColor(Color color) {
        brick.setColor(color);
    }

    public void setGridPosition(int x, int y) {
        brick.setPosition(new Vector2(X_ORIGIN + SCALE * x, Y_ORIGIN + SCALE * y));        
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
