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
     * Does nothing if move is invalid.
     * Performs a Move on the given tile.
     * Changes tile[r][c] to color in boolean.
     * Then flips surrounding tiles based on Othello rules.
     * @param position The position of the move that was played
     * @param color The color of the disk now at the position
     */
    public void makeMove(int position, boolean color) {
        if(position < -1 || position > 63) throw new IllegalArgumentException("position must be in the range [-1,63]");
        if(position == -1 || !Player.validMove(currentBoard,color,position)) return;

        int row = position/8,col = position%8;

        currentBoard.getHasTile()[row][col] = true;
        currentBoard.getColor()[row][col] = color;
        //updateBoard(row,col);
    }


    /**
     * PRECONDITION - Move (r,c) must be valid
     * Goes out in each direction from (r,c). TODO updateBoard is unfished as it updates oppsoite color tiles even when they do not form a sandwhich.
     * I will fix mistake soon.
     * Flips a disk if it is not equal to color.
     * Stops when it gets to a tile that is color or when it reaches a tile that has no disk.
     * @param r Row of tile
     * @param c Col of tile
     * @param color The color tile (r,c) now is.
     */
    private void updateBoard(byte r, byte c, boolean color) {
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
     * Travels verically from (r,c) and flips tiles accoridng to Othello Rules:
     * If a tile is the opposite color of color, than flip.
     * Stop flipping tiles if a tile does not have a disk
     * @param r
     * @param c
     * @param color
     */
    private void flipUp(byte r, byte c, boolean color) {
        byte row = (byte) (r-1);
        while (currentBoard.getColor()[row][c] == color) {
            if (row < 0 || !currentBoard.getHasTile()[row][c])
                return;
            row--;
        }
        while (++row < r)
            currentBoard.getColor()[row][c] = color;
    }

    private void flipUpRight(byte r, byte c, boolean color) {
        byte row = (byte) (r-1);
        byte col = (byte) (c+1);
        while (currentBoard.getColor()[row][col] == color) {
            if (row < 0 || col >= currentBoard.getColor()[row].length || !currentBoard.getHasTile()[row][col])
                return;
            row--;
            col++;
        }
        while (++row < r && --col > c)
            currentBoard.getColor()[row][col] = color;
    }

    private void flipRight(byte r, byte c, boolean color) {
        byte col = (byte) (c+1);
        while (currentBoard.getColor()[r][col] == color) {
            if (col >= currentBoard.getColor()[r].length || !currentBoard.getHasTile()[r][col])
                return;
            col++;
        }
        while (--col > c)
            currentBoard.getColor()[r][col] = color;
    }

    private void flipDownRight(byte r, byte c, boolean color) {
        byte row = (byte) (r+1);
        byte col = (byte) (c+1);
        while (currentBoard.getColor()[row][col] == color) {
            if (row >= currentBoard.getColor().length || col >= currentBoard.getColor()[row].length || !currentBoard.getHasTile()[row][col])
                return;
            row++;
            col++;
        }
        while (--row > r && --col > c)
            currentBoard.getColor()[row][col] = color;
    }

    private void flipDown(byte r, byte c, boolean color) {
        byte row = (byte) (r+1);
        while (currentBoard.getColor()[row][c] == color) {
            if (row >= currentBoard.getColor().length || !currentBoard.getHasTile()[row][c])
                return;
            row++;
        }
        while (--row > r)
            currentBoard.getColor()[row][c] = color;
    }

    private void flipDownLeft(byte r, byte c, boolean color) {
        byte row = (byte) (r+1);
        byte col = (byte) (c-1);
        while (currentBoard.getColor()[row][col] == color) {
            if (row >= currentBoard.getColor().length || col < 0 || !currentBoard.getHasTile()[row][col])
                return;
            row++;
            col--;
        }
        while (--row > r && ++col < c)
            currentBoard.getColor()[row][col] = color;
    }

    private void flipLeft(byte r, byte c, boolean color) {
        byte col = (byte) (c-1);
        while (currentBoard.getColor()[r][col] == color) {
            if (col < 0 || !currentBoard.getHasTile()[r][col])
                return;
            col--;
        }
        while (++col < c)
            currentBoard.getColor()[r][col] = color;
    }

    private void flipUpLeft(byte r, byte c, boolean color) {
        byte row = (byte) (r-1);
        byte col = (byte) (c-1);
        while (currentBoard.getColor()[row][col] == color) {
            if (row < 0 || col < 0 || !currentBoard.getHasTile()[row][col])
                return;
            row--;
            col--;
        }
        while (++row < r && ++col < c)
            currentBoard.getColor()[row][col] = color;
    }

    /**
     * Returns the current BoardState
     * @return the current {@link BoardState}
     */
    public BoardState getCurrentBoard() {
        return currentBoard;
    }
}
