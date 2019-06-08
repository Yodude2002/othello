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
     * flips disks according to Othello Rules:
     * If a group of disks in opposite color are sandwich from (r,c) to another disk of (r,c)'s color, than flip.
     * @param row Row of tile
     * @param col Column of tile
     * @param color The color tile (r,c) now is.
     */
    private void updateBoard(int row, int col, boolean color) {
        boolean[][] board = currentBoard.getColor();
        boolean[][] hasTile = currentBoard.getHasTile();
        for (int direction = 0; direction < board.length; direction++) {
            int rowInc = 0;
            int colInc = 0;
            int count = 0;
            if (direction == 7 || direction == 0 || direction == 1) {
                rowInc = 1;
            } else if (direction == 3 || direction == 4 || direction == 5) {
                rowInc = -1;
            }
            if (direction == 1 || direction == 2 || direction == 3) {
                colInc = 1;
            } else if (direction == 5 || direction == 6 || direction == 7) {
                colInc = -1;
            }
            int r = row += rowInc;
            int c = col += colInc;
            while (0 <= r && r < board.length && 0 <= c && c < board[r].length &&
               board[r][c] != color) {
                if (!hasTile[row][c])
                    return;
                r += rowInc;
                c += colInc;
                count++;
            }
            r -= rowInc;
            c -= colInc;
            while (count > 0 && 0 <= r && r < board.length && 0 <= c && c < board[r].length){
                currentBoard.getColor()[r][c] = color;
                r -= rowInc;
                c -= colInc;
                count--;
            }
        }
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
