package gametemplate.gameobject;

import gametemplate.Tetris;

public class TetrisPile {
    static TetrisBlock[][] blockPile = new TetrisBlock[Tetris.GRID_HEIGHT][Tetris.GRID_WIDTH];

    public static void addBlock(TetrisBlock block) {
        int c = (int) ((block.getPosition().getX() - Tetris.X_ORIGIN) / Tetris.SCALE);
        int r = (int) ((block.getPosition().getY() - Tetris.Y_ORIGIN) / Tetris.SCALE);
        System.out.println("Adding a block at " + r + " " + c);
        if (r >= Tetris.GRID_HEIGHT) {
            r = Tetris.GRID_HEIGHT - 1;
        }
        if (c >= Tetris.GRID_WIDTH) {
            c = Tetris.GRID_WIDTH - 1 ;
        }
        blockPile[r][c] = block;
    }

    public static void clearLines() {
        for (int r = Tetris.GRID_HEIGHT - 1; r >= 0; r--) {
            // clear from the bottom up
            boolean isClear = true;
            for (int c = 0; c < Tetris.GRID_WIDTH; c++) {
                if (blockPile[r][c] == null) {
                    isClear = false;
                    break; // out of the row
                }
            }

            if (isClear) {
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
                for (int i = 0; i < Tetris.GRID_WIDTH; i++) {
                    // delete and free our blocks
                    // TODO: convert the blocks to a reusable pile
                    // so that we don't invoke the garbage collector
                    if (blockPile[0][i] != null) {
                        blockPile[0][i].destruc();
                        blockPile[0][i] = null;
                    }
                }
                // go down a row so we can check the newly
                // dropped line on the next loop through
                r++;
            }
        }
    }
}
