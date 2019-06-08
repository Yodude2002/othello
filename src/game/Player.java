package game;

import java.util.ArrayList;
import java.util.List;

public interface Player {

    boolean getColor();

    void setColor(boolean white);

    /**
     * Gets the name of this AI
     * @return the name of the AI
     */
    String toString();

    /**
     * Performs a Turn
     *
     * If the invocation of his method throws an Exception, then that Player loses the game.
     *
     * @param boardState the current state of the board
     * @return an integer in the range [0,64), or -1 to indicate that no legal moves can be made.
     *         This move must be a valid move.
     */
    int getMove(BoardState boardState);

    /**
     * Finds all of the possible moves for a player with a given board, and a specified color to be playing as
     * @param boardState the current board
     * @param color the color player to find moves for
     * @return all of the possible moves for the player of the specified color
     * designated as integers from 0 to 63, where the row is the integer divided by 8, and the column is the integer modulo 8
     */
    static List<Integer> findPossibleMoves(BoardState boardState, boolean color){

        List<Integer> possibleMoves = new ArrayList<>();

        boolean[][] board = boardState.getColor(); //An array of booleans where white is true and black is false

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if(!boardState.getHasTile()[r][c]) {
                    for (int direction = 0; direction < 8; direction++) {
                        int rowInc = 0;
                        int colInc = 0;
                        if(direction == 7 || direction == 0 || direction == 1){
                            rowInc = 1;
                        }else if(direction == 3 || direction == 4 || direction == 5){
                            rowInc = -1;
                        }
                        if(direction == 1 || direction == 2 || direction == 3){
                            colInc = 1;
                        }else if(direction == 5 || direction == 6 || direction == 7){
                            colInc = -1;
                        }
                        int row = r + rowInc;
                        int col = c + colInc;
                        while(row<board.length && row>-1 && col<board[0].length && col>-1 && boardState.getHasTile()[row][col] && color != board[row][col]){
                            row += rowInc;
                            col += colInc;
                        }
                        if((row<board.length && row>-1 && col<board[0].length && col>-1) && boardState.getHasTile()[row][col] &&(row != r + rowInc || col != c + colInc) && color == board[row][col]){
                            possibleMoves.add(r*8 + c);
                            break;
                        }
                    }
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Given a potentialMove [-1, 63], return if move is valid
     * @param boardState Board with a grid
     * @param color Color of player checking if move is valid
     * @param potentialMove Move for player from [-1,63].
     * @return True if valid move for color of player, false if invalid
     */
    static boolean validMove(BoardState boardState, boolean color, int potentialMove) {
        if (potentialMove < 0 || potentialMove > 63)
            return false;
        List<Integer> posMoves = findPossibleMoves(boardState, color);
        for (int move : posMoves)
            if (potentialMove == move)
                return true;
        return false;
    }
}
