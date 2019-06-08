package game;

public class Board {

    private BoardState currentBoard;

    /**
     * Constructs a new default board (8 by 8) based on Othello Rules
     * 3,3: White 3,4: Black
     * 4,3: Black 4,4: White
     */
    public Board() {
        currentBoard = new BoardState(new boolean[8][8],new boolean[8][8]);
        currentBoard.defaultSetup();
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
     * PRECONDITION Move (int position) must be valid (-1 to 63)
     * Does nothing if move is invalid.
     * Performs a Move on the given tile.
     * Changes tile[r][c] to color in boolean.
     * Then flips surrounding tiles based on Othello rules.
     * @param position The position of the move that was played
     * @param color The color of the player
     */
    public void makeMove(int position, boolean color) {
        if(position < -1 || position > 63) throw new IllegalArgumentException("position must be in the range [-1,63]");
        if(position == -1) return;

        int row = position/8,col = position%8;

        currentBoard.getHasTile()[row][col] = true;
        currentBoard.getColor()[row][col] = color;
        updateBoard(row,col,color);
    }


    /**
     * PRECONDITION - Move (r,c) must be valid
     * Goes out in each direction from (r,c).
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
     * Travels vertically from (r,c) and flips tiles according to Othello Rules:
     * If a group of disks in opposite color are sandwich from (r,c) to another disk of (r,c)'s color, than flip.
     * @param r Row of tile that just had a disk placed
     * @param c Column of tile that just had a disk placed
     * @param color Color of disk just placed
     */
    private void flipUp(int r, int c, boolean color) {
        int row = r-1;
        while (row >= 0 && currentBoard.getColor()[row][c] != color) {
            if (!currentBoard.getHasTile()[row][c])
                return;
            row--;
        }
        while (++row < r && row < currentBoard.getColor().length)
            currentBoard.getColor()[row][c] = color;
    }

    /**
     * Travels up and to the right from (r,c) and flips tiles according to Othello Rules:
     * If a group of disks in opposite color are sandwich from (r,c) to another disk of (r,c)'s color, than flip.
     * @param r Row of tile that just had a disk placed
     * @param c Column of tile that just had a disk placed
     * @param color Color of disk just placed
     */
    private void flipUpRight(int r, int c, boolean color) {
        int row = r-1;
        int col = c+1;
        while (row >= 0 && col < currentBoard.getColor()[row].length && currentBoard.getColor()[row][col] != color) {
            if (!currentBoard.getHasTile()[row][col])
                return;
            row--;
            col++;
        }
        while (++row < r && --col > c && row < currentBoard.getColor().length && col >= 0)
            currentBoard.getColor()[row][col] = color;
    }

    /**
     * Travels right from (r,c) and flips tiles according to Othello Rules:
     * If a group of disks in opposite color are sandwich from (r,c) to another disk of (r,c)'s color, than flip.
     * @param r Row of tile that just had a disk placed
     * @param c Column of tile that just had a disk placed
     * @param color Color of disk just placed
     */
    private void flipRight(int r, int c, boolean color) {
        int col = c+1;
        while (col < currentBoard.getColor()[r].length && currentBoard.getColor()[r][col] != color) {
            if (!currentBoard.getHasTile()[r][col])
                return;
            col++;
        }
        while (--col > c && col >= 0)
            currentBoard.getColor()[r][col] = color;
    }

    /**
     * Travels down and to the right from (r,c) and flips tiles according to Othello Rules:
     * If a group of disks in opposite color are sandwich from (r,c) to another disk of (r,c)'s color, than flip.
     * @param r Row of tile that just had a disk placed
     * @param c Column of tile that just had a disk placed
     * @param color Color of disk just placed
     */
    private void flipDownRight(int r, int c, boolean color) {
        int row = r+1;
        int col = c+1;
        while (row < currentBoard.getColor().length && col < currentBoard.getColor()[row].length && currentBoard.getColor()[row][col] != color) {
            if (!currentBoard.getHasTile()[row][col])
                return;
            row++;
            col++;
        }
        while (--row > r && --col > c && row < currentBoard.getColor().length && col < currentBoard.getColor()[row].length)
            currentBoard.getColor()[row][col] = color;
    }

    /**
     * Travels down from (r,c) and flips tiles according to Othello Rules:
     * If a group of disks in opposite color are sandwich from (r,c) to another disk of (r,c)'s color, than flip.
     * @param r Row of tile that just had a disk placed
     * @param c Column of tile that just had a disk placed
     * @param color Color of disk just placed
     */
    private void flipDown(int r, int c, boolean color) {
        int row =  r+1;
        while (row < currentBoard.getColor().length && currentBoard.getColor()[row][c] != color) {
            if (!currentBoard.getHasTile()[row][c])
                return;
            row++;
        }
        while (--row > r && row < currentBoard.getColor().length)
            currentBoard.getColor()[row][c] = color;
    }

    /**
     * Travels down and to the left from (r,c) and flips tiles according to Othello Rules:
     * If a group of disks in opposite color are sandwich from (r,c) to another disk of (r,c)'s color, than flip.
     * @param r Row of tile that just had a disk placed
     * @param c Column of tile that just had a disk placed
     * @param color Color of disk just placed
     */
    private void flipDownLeft(int r, int c, boolean color) {
        int row = r+1;
        int col = c-1;
        while (row < currentBoard.getColor().length && col >= 0 && currentBoard.getColor()[row][col] != color) {
            if (!currentBoard.getHasTile()[row][col])
                return;
            row++;
            col--;
        }
        while (--row > r && ++col < c && row >= 0 && col < currentBoard.getColor()[row].length)
            currentBoard.getColor()[row][col] = color;
    }

    /**
     * Travels left from (r,c) and flips tiles according to Othello Rules:
     * If a group of disks in opposite color are sandwich from (r,c) to another disk of (r,c)'s color, than flip.
     * @param r Row of tile that just had a disk placed
     * @param c Column of tile that just had a disk placed
     * @param color Color of disk just placed
     */
    private void flipLeft(int r, int c, boolean color) {
        int col = c-1;
        while (col >= 0 && currentBoard.getColor()[r][col] != color) {
            if (!currentBoard.getHasTile()[r][col])
                return;
            col--;
        }
        while (++col < c && col < currentBoard.getColor()[r].length)
            currentBoard.getColor()[r][col] = color;
    }

    /**
     * Travels up and to the left from (r,c) and flips tiles according to Othello Rules:
     * If a group of disks in opposite color are sandwich from (r,c) to another disk of (r,c)'s color, than flip.
     * @param r Row of tile that just had a disk placed
     * @param c Column of tile that just had a disk placed
     * @param color Color of disk just placed
     */
    private void flipUpLeft(int r, int c, boolean color) {
        int row = r-1;
        int col = c-1;
        while (row >= 0 && col >= 0 && currentBoard.getColor()[row][col] != color) {
            if (!currentBoard.getHasTile()[row][col])
                return;
            row--;
            col--;
        }
        while (++row < r && ++col < c && row < currentBoard.getColor().length && col < currentBoard.getColor()[row].length)
            currentBoard.getColor()[row][col] = color;
    }

    /**
     * Returns the current BoardState
     * @return the current {@link BoardState}
     */
    public BoardState getCurrentBoard() {
        return currentBoard;
    }

    /**
     * Returns ratio of black disks to white disks
     * @return byte[] where [0] is # of black disks, [1] is # of white disks
     */
    public byte[] blackToWhiteRatio() {
        byte[] ratio = new byte[2];
        for (int r = 0; r < currentBoard.getColor().length; r++)
            for (int c = 0; c < currentBoard.getColor()[r].length; c++)
                if (currentBoard.getHasTile()[r][c]) {
                    if (!currentBoard.getColor()[r][c])
                        ratio[0]++;
                    else
                        ratio[1]++;
                }
        return ratio;
    }

    /**
     * Puts board back to the default position in Othello rules.
     */
    public void reset() {
        currentBoard.defaultSetup();
    }
}
