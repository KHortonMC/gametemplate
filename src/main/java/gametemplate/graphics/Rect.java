package gametemplate.graphics;

public final class Rect {
    public static int EDGE = 5;

    double x = 0.0;
    double y = 0.0;
    double w = 0.0;
    double h = 0.0;

    public Rect(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rect(Vector2 position, Vector2 size) {
        this.x = position.getX();
        this.y = position.getY();
        this.w = size.getX();
        this.h = size.getY();
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public double getW() {return w;}
    public double getH() {return h;}

    public Vector2 getPosition() { return new Vector2(x, y); }
    public Vector2 getSize() { return new Vector2(w, h); }

    public void setPosition(Vector2 position) { x = position.getX(); y = position.getY(); }
    public void setSize(Vector2 size) { w = size.getX(); h = size.getY(); }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setW(double w) { this.w = w; }
    public void setH(double h) { this.h = h; }

    public enum Collision {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public boolean doesCollide(Rect other) {
        return this.x <= other.x + other.w - EDGE
            && this.x + this.w - EDGE >= other.x
            && this.y <= other.y + other.h - EDGE
            && this.y + this.h - EDGE >= other.y;
    }

    // chatGPT was used to solve for collisionSide
    public Collision collisionSide(Rect other) {
        if (!doesCollide(other)) return Collision.NONE;

        double leftCollision = Math.abs((this.x + this.w) - other.x);    // right
        double rightCollision = Math.abs(this.x - (other.x + other.w));  // left
        double bottomCollision = Math.abs((this.y + this.h) - other.y);  // bottom
        double topCollision = Math.abs(this.y - (other.y + other.h));    // top

        // Find the minimum distance
        double minDistance = Math.min(Math.min(leftCollision, rightCollision), 
                                        Math.min(topCollision, bottomCollision));

        // Determine which side caused the collision
        if (minDistance == bottomCollision) {
            return Collision.BOTTOM;
        }
        else if (minDistance == topCollision) {
            return Collision.TOP;
        }
        else if (minDistance == leftCollision) {
            return Collision.RIGHT;
        } else {
            return Collision.LEFT;
        }
    }

    public String toString() {
        return "[" + x + "," + y + "," + w + "," + h + "]";
    }
}
