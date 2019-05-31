package game;

public class Board {

    private BoardState currentBoard;

    /**
     * Constructs a new default board.
     */
    public Board() {
        currentBoard = new BoardState(new boolean[8][8],new boolean[8][8]);
        currentBoard.getHasTile()[3][3] = true;
        currentBoard.getHasTile()[3][4] = true;
        currentBoard.getHasTile()[4][3] = true;
        currentBoard.getHasTile()[4][4] = true;

        currentBoard.getColor()[3][3] = true;
        currentBoard.getColor()[4][4] = true;
    }

    /**
     * Performs a Move on the given tile
     *
     *
     * @param position the position of the move that was played
     * @param color the color of the played tile
     */
    public void makeMove(int position, boolean color) {
        if(position < -1 || position > 63) throw new IllegalArgumentException("position must be in the range [-1,63]");
        if(position == -1) return;

        int row = position/8, col = position%8;

        currentBoard.getHasTile()[row][col] = true;
        currentBoard.getColor()[row][col] = true;
    }

    /**
     * Returns the current BoardState
     * @return the current {@link BoardState}
     */
    public BoardState getCurrentBoard() {
        return currentBoard;
    }
}
