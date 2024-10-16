package gametemplate.gameobject;

import java.util.Random;

import gametemplate.Main;
import gametemplate.Tetris;
import gametemplate.graphics.Vector2;
import javafx.scene.paint.Color;

public class TetrisPiece extends GameObject  {
    private static final int NUM_BLOCKS = 4;
    TetrisBlock[][] layout = new TetrisBlock[NUM_BLOCKS][NUM_BLOCKS];
    TetrisBlock[][] rotateLayout = new TetrisBlock[NUM_BLOCKS][NUM_BLOCKS];
    double x = 0.0;
    double y = 0.0;

    public enum Style {
        LINE,
        SQUARE,
        ELBOW,
        TEE,
        ZEE;
    }

    Style style = Style.LINE;
    boolean flipped = false;
    Random random = null;

    public TetrisPiece() {
        super();
        this.isActive = true;
        this.isVisible = false;
        this.random = new Random();
        newRandomPiece();
    }

    public Vector2 getPosition() { return new Vector2(x, y); }

    private void newRandomPiece() {
        this.style = Style.values()[random.nextInt(5)];
        this.flipped = random.nextBoolean();
        this.assembleBlocks();
        this.setGridPosition(4,0, layout);
    }

    void setColor(Color color) {
        for (int r = 0; r < NUM_BLOCKS; r++) {
            for (int c = 0; c < NUM_BLOCKS; c++) {
                if (layout[r][c] != null) {
                    layout[r][c].setColor(color);
                }
            }
        }
    }

    
    void flipBlock() {
        TetrisBlock block = null;

        for (int r = 0; r < NUM_BLOCKS; r++) {
            for (int c = 0; c < NUM_BLOCKS/2; c++) {
                block = layout[r][c];
                layout[r][c] = layout[r][NUM_BLOCKS - (1+c)];
                layout[r][NUM_BLOCKS - (1+c)] = block;
            }
        }
    }

    private void assembleBlocks() {
        switch (style) {
            case Style.LINE:
                layout[0][1] = TetrisBlock.getFreeBlock();
                layout[1][1] = TetrisBlock.getFreeBlock();
                layout[2][1] = TetrisBlock.getFreeBlock();
                layout[3][1] = TetrisBlock.getFreeBlock();
                setColor(flipped ? Color.BISQUE : Color.DARKORCHID);
                break;

            case Style.SQUARE:
                layout[1][1] = TetrisBlock.getFreeBlock();
                layout[1][2] = TetrisBlock.getFreeBlock();
                layout[2][1] = TetrisBlock.getFreeBlock();
                layout[2][2] = TetrisBlock.getFreeBlock();
                setColor(flipped ? Color.DARKCYAN : Color.DARKGOLDENROD);
                break;    
                
            case Style.TEE:
                layout[1][1] = TetrisBlock.getFreeBlock();
                layout[1][2] = TetrisBlock.getFreeBlock();
                layout[1][3] = TetrisBlock.getFreeBlock();
                layout[2][2] = TetrisBlock.getFreeBlock();
                setColor(flipped ? Color.HOTPINK : Color.HONEYDEW);
                break;

            case Style.ELBOW:
                layout[1][1] = TetrisBlock.getFreeBlock();
                layout[1][2] = TetrisBlock.getFreeBlock();
                layout[2][2] = TetrisBlock.getFreeBlock();
                layout[3][2] = TetrisBlock.getFreeBlock();
                setColor(flipped ? Color.MAGENTA : Color.LIGHTSKYBLUE);
                break;        
                
            case Style.ZEE:
                layout[1][1] = TetrisBlock.getFreeBlock();
                layout[1][2] = TetrisBlock.getFreeBlock();
                layout[2][2] = TetrisBlock.getFreeBlock();
                layout[2][3] = TetrisBlock.getFreeBlock();
                setColor(flipped ? Color.THISTLE : Color.PAPAYAWHIP);
                break;   
 
                default:
                break;
        }

        if (flipped) {
            flipBlock();
        }
    }

    public void setGridPosition(double x, double y, TetrisBlock[][] layout) {
        this.x = x; 
        this.y = y;
        for (int r = 0; r < NUM_BLOCKS; r++) {
            for (int c = 0; c < NUM_BLOCKS; c++) {
                if (layout[r][c] != null) {
                    layout[r][c].setGridPosition(x+c, y+r);
                }
            }
        }
    }

    public double checkCollisionDistance(TetrisBlock[][] layout) {
        GameObject collision = null;
        double dist = 0.0;
        for (int r = 0; r < NUM_BLOCKS; r++) {
            for (int c = 0; c < NUM_BLOCKS; c++) {
                if (layout[r][c] != null) {
                    collision = layout[r][c].findCollision();
                    if (collision != null) {
                        dist = collision.bounding.getY() - layout[r][c].bounding.getY();
                    }
                }
            }
        } 
        return dist;
    }

    public GameObject checkCollision(TetrisBlock[][] layout) {
        GameObject collision = null;
        for (int r = 0; r < NUM_BLOCKS; r++) {
            for (int c = 0; c < NUM_BLOCKS; c++) {
                if (layout[r][c] != null) {
                    collision = layout[r][c].findCollision();
                    if (collision != null) {
                        return collision;
                    }
                }
            }
        }
        // clear any self-collisions
        // for (int r = 0; r < NUM_BLOCKS; r++) {
        //     for (int c = 0; c < NUM_BLOCKS; c++) {
        //         if (layout[r][c] == collision) {
        //             collision = null; 
        //         }
        //     }
        // }
        return collision;
    }

    public void giveBlocks() {
        for (int r = 0; r < NUM_BLOCKS; r++) {
            for (int c = 0; c < NUM_BLOCKS; c++) {
                if (layout[r][c] != null) {
                    TetrisPile.addBlock(layout[r][c]);
                    layout[r][c] = null;
                }
            }
        }
    }

    
    public void rotate() {
        // the algorithm goes: this new object's last matrix
        // is filled with the first row, etc, etc
        // so, the values in this first row, get transposed

        for (int i = 0; i < NUM_BLOCKS; i++) {
            rotateLayout[i][3] = layout[0][i];
            rotateLayout[i][2] = layout[1][i];
            rotateLayout[i][1] = layout[2][i];
            rotateLayout[i][0] = layout[3][i];
        } 

        setGridPosition(this.x, this.y, this.rotateLayout);

        if (checkCollision(rotateLayout) == null) {
            // no collisions, accept this new layout
            // and preserve our matricies so we don't
            // leak memory
            TetrisBlock[][] temp = layout;
            layout = rotateLayout;
            rotateLayout = temp;
        } else {
            // we collided, so let's revert our layout
            setGridPosition(this.x, this.y, this.layout);
        }
    }

    @Override
    public void update() {
        GameObject collision = null;
        // try our left-right move first
        double x = this.x;
        double y = this.y;
        this.x += Main.getScreenDelta().getX();

        Main.getScreenDelta().setX(0.0);

        setGridPosition(this.x, this.y, this.layout);
        collision = checkCollision(layout);

        if (collision != null) {
            // left-right failed
            this.x = x;
            setGridPosition(this.x, this.y, layout);
        }
        
        // try our standard drop next
        double deltaY = Main.getScreenDelta().getY() * 0.5;
        if (deltaY == 0) {
            deltaY = 0.05;
        }

        this.y += deltaY;

        setGridPosition(this.x, this.y, this.layout);
        double collisionDist = checkCollisionDistance(layout);

        if (collisionDist != 0.0) {
            // we impacted something in our drop, so we're done moving
            // and since it's a vertical drop, reset our piece
            this.y -= (Tetris.SCALE - collisionDist) / Tetris.SCALE;
            setGridPosition(this.x, this.y, this.layout);
            giveBlocks();
            TetrisPile.clearLines();
            newRandomPiece();
        }
    }

    @Override
    public String toString() {
        String returnString = "";
        for (int r = 0; r < NUM_BLOCKS; r++) {
            for (int c = 0; c < NUM_BLOCKS; c++) {
                if (layout[r][c] != null) {
                    returnString += layout[r][c].bounding.toString();
                }
            }
        }
        return returnString;
    }
}
