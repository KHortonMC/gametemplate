package gametemplate.gameobject;

import gametemplate.graphics.Vector2;
import javafx.scene.paint.Color;

public class TetrisPiece extends GameObject  {
    private static final int NUM_BLOCKS = 4;
    TetrisBlock[][] layout = new TetrisBlock[NUM_BLOCKS][NUM_BLOCKS];

    public enum Style {
        LINE,
        SQUARE,
        ELBOW,
        TEE,
        ZEE;
    }

    Style style = Style.LINE;
    boolean flipped = false;

    public TetrisPiece(Style style, boolean flipped) throws MaxObjectsException {
        super();
        this.style = style;
        this.flipped = flipped;
        this.assembleBlocks();
    }

    private void assembleBlocks() throws MaxObjectsException {
        TetrisBlock block = new TetrisBlock();

        switch (style) {
            case Style.LINE:
                block.setColor(Color.BISQUE);
                layout[0][1] = new TetrisBlock(block);
                layout[1][1] = new TetrisBlock(block);
                layout[2][1] = new TetrisBlock(block);
                layout[3][1] = block;
                break;
        
            default:
                break;
        }

        if (flipped) {
            for (int r = 0; r < NUM_BLOCKS; r++) {
                for (int c = 0; c < NUM_BLOCKS; c++) {
                    block = layout[r][c];
                    layout[r][c] = layout[r][NUM_BLOCKS - (1+c)];
                    layout[r][NUM_BLOCKS - (1+c)] = block;
                }
            }
        }
    }

    public void setGridPosition(int x, int y) {
        for (int r = 0; r < NUM_BLOCKS; r++) {
            for (int c = 0; c < NUM_BLOCKS; c++) {
                if (layout[r][c] != null) {
                    layout[r][c].setGridPosition(x+c, y+r);
                }
            }
        }
    }

    
    public void rotate() {

    }

    @Override
    public void update() {

    }
}
