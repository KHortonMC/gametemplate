package gametemplate.gameobject;

import gametemplate.Tetris;
import gametemplate.graphics.Drawable;
import gametemplate.graphics.Rect;
import gametemplate.graphics.Vector2;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject implements Drawable {
    protected static class ObjectList {
        static int MAX_OBJECTS = Tetris.GRID_HEIGHT * Tetris.GRID_WIDTH + 20;
        static int INVALID_ID = -1;
        int lastID = 0;
        GameObject[] gameObjects = null;
        ObjectList() {
            gameObjects = new GameObject[ObjectList.MAX_OBJECTS];
        }

        int addObject(GameObject o) throws MaxObjectsException {
            o.myID = ObjectList.INVALID_ID;
            for (int i = 0; i < gameObjects.length; i++) {
                if (gameObjects[i] == null) {
                    gameObjects[i] = o;
                    o.myID = lastID++;
                    break;
                }
            }

            if (o.myID == ObjectList.INVALID_ID) {
                throw new MaxObjectsException();
            }

            return o.myID;
        }

        int removeObject(GameObject o) {
            for (int i = 0; i < gameObjects.length; i++) {
                // compare references, not content
                if (gameObjects[i] == o) { 
                    gameObjects[i] = null;
                }
            }
            return o.myID;
        }
    }

    int myID = -1;
    static ObjectList objects = null;
    public abstract Vector2 getPosition();
    Rect bounding = null;

    boolean isActive = false;
    public boolean getActive() { return this.isActive; }
    public void setActive(boolean active) { this.isActive = active;}

    boolean isVisible = false;
    public boolean getVisible() { return this.isVisible; }
    public void setVisible(boolean visible) { this.isVisible = visible; }


    public static void initialize() {
        objects = new ObjectList();
    }

    public static void updateAll() {
        for (int i = 0; i < objects.gameObjects.length; i++) {
            if (objects.gameObjects[i] != null
                && objects.gameObjects[i].isActive) {
                objects.gameObjects[i].update();
            }
        }
    }

    public static void drawAll(GraphicsContext gc) {
        for (int i = 0; i < objects.gameObjects.length; i++) {
            if (objects.gameObjects[i] != null
                && objects.gameObjects[i].isVisible) {
                objects.gameObjects[i].draw(gc);
            }
        }
    }

    protected GameObject() throws MaxObjectsException {
        if (objects.addObject(this) == ObjectList.INVALID_ID) {
            throw new MaxObjectsException();
        }
    }

    public void destruct() {
        objects.removeObject(this);
    }

    protected GameObject findCollision() {
        if (this.bounding == null
            || this.isActive == false) return null;

        GameObject otherObject = null;
        boolean collides = false;

        for (int i = 0; i < objects.gameObjects.length; i++) {
            // only compare to valid objects that aren't us!
            otherObject = objects.gameObjects[i];
            if (otherObject != null 
                && otherObject.isActive == true
                && otherObject != this
                && otherObject.bounding != null) {
                collides = this.bounding.doesCollide(otherObject.bounding);
                if (collides) {
                    System.out.println("Collision between object " + this.myID + " " + this.bounding.toString() + " and " + otherObject.myID + " " + otherObject.bounding.toString() + " using edge collision: " + Rect.EDGE);
                    return otherObject;
                }
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
