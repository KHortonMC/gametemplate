package gametemplate.gameobject;

import gametemplate.graphics.Rect;
import gametemplate.graphics.Rectangle;
import gametemplate.graphics.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StaticBrick extends GameObject {
    Rectangle brick = null;

    public StaticBrick() {
        super();
        brick = new Rectangle();
        this.isActive = true;
        this.isVisible = true;
    }

    public StaticBrick(Rect rect) {
        super();
        bounding = rect;
        brick = new Rectangle(rect);
        brick.setColor(Color.CORNFLOWERBLUE);
        this.isActive = true;
        this.isVisible = true;
    }

    public Vector2 getPosition() { return brick.getPosition(); }
    
    @Override
    public void update() {
    }

    @Override
    public void draw(GraphicsContext gc) {
        brick.draw(gc);
    }

    @Override
    public String toString() {
        return brick.toString();
    }
}
