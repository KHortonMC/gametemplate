package gametemplate.gameobject;

import java.util.LinkedList;

import gametemplate.graphics.Drawable;
import gametemplate.graphics.Rect;
import gametemplate.graphics.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class GameObject implements Drawable {
    static LinkedList<GameObject> objects = new LinkedList<>();
    static int lastID = 0;

    int id = -1;
    Rect bounding = null;

    public Vector2 getPosition() {
        if (bounding == null) {
            return Vector2.ZERO;
        }
        return bounding.getPosition();
    }

    boolean isActive = false;
    public boolean getActive() { return this.isActive; }
    public void setActive(boolean active) { this.isActive = active;}

    boolean isVisible = false;
    public boolean getVisible() { return this.isVisible; }
    public void setVisible(boolean visible) { this.isVisible = visible; }

    public static void updateAll() {
        for (GameObject o : objects) {
            if (o.isActive) {
                o.update();
            }
        }
    }

    public static void drawAll(GraphicsContext gc) {
        for (GameObject o : objects) {
            if (o.isVisible) {
                o.draw(gc);
            }
        }
    }

    protected GameObject() {
        objects.add(this);
        this.id = lastID++;
    }

    protected GameObject findCollision() {
        if (this.bounding == null || !this.isActive) return null;

        for (GameObject o : objects) {
            if (o != null 
                && o != this 
                && o.isActive 
                && o.bounding != null 
                && this.bounding.doesCollide(o.bounding)) {
                    return o;
                }
            
        }

        return null;
    }

    public void update() {
        // for future classes to Override
    }
    
    public void draw(GraphicsContext gc) {
        // for future classes to Override
    }
}
