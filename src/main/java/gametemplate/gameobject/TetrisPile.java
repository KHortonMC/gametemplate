package gametemplate.gameobject;

import gametemplate.Tetris;

public class TetrisPile {
    static TetrisBlock[][] blockPile = new TetrisBlock[Tetris.GRID_HEIGHT][Tetris.GRID_WIDTH];

    private TetrisPile() {}

    public static void addBlock(TetrisBlock block) {
        double x = (Math.ceil(block.getPosition().getX()) - Tetris.X_ORIGIN) / Tetris.SCALE;
        double y = (Math.ceil(block.getPosition().getY()) - Tetris.Y_ORIGIN) / Tetris.SCALE;

        int c = (int)x;
        int r = (int)y;

        System.out.println("Adding a block at " + r + " " + c + " with coords " + block.getPosition().toString());

        if (r >= Tetris.GRID_HEIGHT) {
            r = Tetris.GRID_HEIGHT - 1;
        }
        if (c >= Tetris.GRID_WIDTH) {
            c = Tetris.GRID_WIDTH - 1 ;
        }
        blockPile[r][c] = block;

        // fix the block's location
        block.setGridPosition(c, r);
    }

    private static boolean isLineCompleted(int r) {
        for (int c = 0; c < Tetris.GRID_WIDTH; c++) {
            if (blockPile[r][c] == null) {
                return false;
            }
        }
        return true;
    }

    private static void dropRowsAbove(int r) {
        TetrisBlock[] temp = blockPile[r];
        for (int i = r; i >= 1; i--) {
            blockPile[i] = blockPile[i - 1];
            for (int c = 0; c < Tetris.GRID_WIDTH; c++) {
                if (blockPile[i][c] != null) {
                    blockPile[i][c].dropOne();
                }
            }
        }
        blockPile[0] = temp;
    }

    private static void clearRow(int r) {
        for (int i = 0; i < Tetris.GRID_WIDTH; i++) {
            // free up our blocks to become new blocks
            if (blockPile[r][i] != null) {
                blockPile[r][i].setActive(false);
                blockPile[r][i].setVisible(false);
                blockPile[r][i] = null;
            }
        }
    }

    public static void clearLines() {
        for (int r = Tetris.GRID_HEIGHT - 1; r >= 0; r--) {
            // clear from the bottom up
            if (isLineCompleted(r)) {
                dropRowsAbove(r);
                clearRow(0);

                // go down a row so we can check the newly
                // dropped line on the next loop through
                r++;
            }
        }
    }
}
