package game;

public class Board {

    private BoardState currentBoard;

    /**
     * Constructs a new default board based on Othello Rules
     * 3,3: White 3,4: Black
     * 4,3: Black 4,4: White
     */
    public Board() {
        currentBoard = new BoardState(new boolean[8][8],new boolean[8][8]);
    }

    /**
     * Constructs a new Board with a clone of the given BoardState.
     *
     * This method creates deep clones.
     * @param state
     */
    public Board(BoardState state) {
        currentBoard = new BoardState(state.getHasTile().clone(), state.getColor().clone());
    }

    /**
     * Performs a Move on the given tile.
     * Changes tile[r][c] to color in boolean.
     * Then flips surrounding tiles based on Othello rules.
     * @param position the position of the move that was played
     * @param color the color of the disk now at the position
     */
    public void makeMove(int position, boolean color) {
        if(position < -1 || position > 63) throw new IllegalArgumentException("position must be in the range [-1,63]");
        if(position == -1) return;

        int row = position/8,col = position%8;

        currentBoard.getHasTile()[row][col] = true;
        currentBoard.getColor()[row][col] = color;
        //updateBoard(row,col);
    }

    /**
     * Goes out in each direction from (r,c). TODO updateBoard is unfished as it updates oppsoite color tiles even when they do not form a sandwhich.
     * I will fix mistake soon.
     * Flips a disk if it is not equal to color.
     * Stops when it gets to a tile that is color or when it reaches a tile that has no disk.
     * @param r Row of tile
     * @param c Col of tile
     * @param color The color tile (r,c) now is.
     */
    private void updateBoard(int r, int c, boolean color) {
        flipUp(r,c,color);
        flipUpRight(r,c,color);
        flipRight(r,c,color);
        flipDownRight(r,c,color);
        flipDown(r,c,color);
        flipDownLeft(r,c,color);
        flipLeft(r,c,color);
        flipUpLeft(r,c,color);
    }

    /**
     * When flipping disks on a direction, a direction should no longer be updated if a tile reached has no disk
     * or a disk of the color from the originating tile in the direction.
     * @param r Row of current tile in the direction
     * @param c Column of current tile in the direction
     * @param color Color of the tile at the begining of a direction. In all 8 directions from a tile,
     *              the tile in the middle is defined as the tile at the begining of a direction.
     *              True is white, false is black.
     * @return True if a direction should no longer be updated, false if continue updating tiles.
     */
    private boolean shouldStopUpdatingDirection(byte r, byte c, boolean color) {
        return !currentBoard.gethasTile()[r][c] || currentboard.getColor()[r][c] == color;
    }

    /**
     * Travels verically from (r,c) and flips tiles accoridng to Othello Rules:
     * If a tile is the opposite color of color, than flip.
     * Stop flipping tiles if a tile does not have a disk
     * @param r
     * @param c
     * @param color
     */
    private void flipUp(byte r, byte c, boolean color) {
        for (byte row = (byte) (r-1); row >= 0; row--) {
            if (shouldStopUpdatingDirection(row,c, color)) {

            }
                return;
            currentBoard.getColor()[row][c] = color;
        }
    }

    private void flipUpRight(byte r, byte c, boolean color) {
        for (byte row = (byte) (r-1); row >= 0; row--) {
            c++;
            if (c >= currentBoard.getColor()[r].length || shouldStopUpdatingDirection(row,c,color))
                return;
            currentBoard.getColor()[row][c] = color;
        }
    }

    private void flipRight(byte r, byte c, boolean color) {
        for (byte col = (byte) (c+1); col < currentBoard.getColor()[r].length; col++) {
            if (shouldStopUpdatingDirection(r,col, color))
                return;
            currentBoard.getColor()[r][col] = color;
        }
    }

    private void flipDownRight(byte r, byte c, boolean color) {
        for (byte col = (byte)(c+1); col < currentBoard.getColor()[r].length; col++) {
            r++;
            if (r >= currentBoard.getColor().length || shouldStopUpdatingDirection(r,col,color))
                return;
            currentBoard.getColor()[r][col] = color;
        }
    }

    private void flipDown(byte r, byte c, boolean color) {
        for (byte row = (byte) (r+1); row < currentBoard.getColor().length; row++) {
            if (shouldStopUpdatingDirection(row,c,color))
                return;
            currentBoard.getColor()[row][c] = color;
        }
    }

    private void flipDownLeft(byte r, byte c, boolean color) {
        for (byte row = (byte) (r+1); row < currentBoard.getColor().length; row++) {
            c--;
            if (c < 0 || shouldStopUpdatingDirection(row,c, color))
                return;
            currentBoard.getColor()[row][c] = color;
        }
    }

    private void flipLeft(byte r, byte c, boolean color) {
        for (byte col = (byte) (c - 1); col >= 0; col--) {
            if (shouldStopUpdatingDirection(r, col, color))
                return;
            currentBoard.getColor()[r][col] = color;
        }
    }

    private void flipUpLeft(byte r, byte c, boolean color) {
        for (byte row = (byte) (r-1); row >= 0; row--) {
            c--;
            if (c < 0 || shouldStopUpdatingDirection(row,c, color))
                return;
            currentBoard.getColor()[row][c] = color;
        }
    }

    /**
     * Returns the current BoardState
     * @return the current {@link BoardState}
     */
    public BoardState getCurrentBoard() {
        return currentBoard;
    }
}
