package players.miles;

import game.Board;
import game.BoardState;
import game.Player;

import java.util.ArrayList;
import java.util.List;

public class MilesAI implements Player{

    /**
     * keeps track of which player it is
     * true if playing as white, false if playing as black
     */
    private boolean isWhite;

    /**
     * default constructor - always black
     */
    public MilesAI(){
        isWhite = false;
    }

    /**
     * constructor
     * @param white the value to set isWhite too, true if white, false if black
     */
    public MilesAI(boolean white){
        isWhite = white;
    }

    /**
     * accessor for isWhite
     * @return isWhite
     */
    public boolean getColor(){return isWhite;}

    /**
     * changes isWhite to either false or true to change between black and white
     * @param white the value to set isWhite too, true if white, false if black
     */
    public void setColor(boolean white){isWhite = white;}

    /**
     * accessor for the name of this AI
     * @return the name of this AI, MilesAI
     */
    public String getName(){
        return "MilesAI";
    }

    /**
     * performs a turn
     * @param boardState the current state of the board
     * @return an integer in the range [0,63] to show where to move or -1 to show no moves can be made
     */
    public int getMove(BoardState boardState){
        ArrayList<Integer> topMoves = new ArrayList<>();
        int topScore = Integer.MIN_VALUE;
        List<Integer> moves = Player.findPossibleMoves(boardState, isWhite);

        if (moves.size() == 0){
            return -1;
        }
        if (moves.size() == 1){
            return moves.get(0);
        }

        for (int i = 0; i < moves.size(); i++){
            int movScr = moveRating(boardState, moves.get(i));
            if (movScr > topScore){
                topScore = movScr;
                topMoves = new ArrayList<>();
                topMoves.add(moves.get(i));
            } else if (movScr == topScore){
                topMoves.add(moves.get(i));
            }
        }

        return topMoves.get((int) (topMoves.size()*Math.random()));
    }

    /**
     * gives a score to each move so the AI can decide which move is the best move
     * @param boardState the current state of the board
     * @param move a proposed move to be made
     * @return a number showing how good the move is, bigger is better
     */
    private int moveRating(BoardState boardState, int move){
        int score = 0;

        Board newBoard = new Board(boardState);
        newBoard.makeMove(move, isWhite);

        int taken = 0;

        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 8; c++){
                if (newBoard.getCurrentBoard().getHasTile()[r][c]){
                    taken++;
                }
            }
        }

        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 8; c++){
                int points = 1;
                if (newBoard.getCurrentBoard().getHasTile()[r][c] && taken < 58){
                    if (isCorner(r*8 + c)){
                        points = 7;
                    } else if (isNextToOpenCorner(r*8 + c, newBoard.getCurrentBoard().getHasTile())){
                        points = -5;
                    } else if (isEdge(r*8 + c)){
                        points = 3;
                    }
                    if (newBoard.getCurrentBoard().getColor()[r][c] != isWhite){
                        points = -points;
                    }
                    score += points;
                }
            }
        }

        return score;
    }

    /**
     * tells whether a tile is a corner tile
     * @param tile the tile number that correlates to a position on the board
     * @return true if it is at corner
     */
    private boolean isCorner(int tile){
        return tile == 0 || tile == 7 || tile == 56 || tile == 63;
    }

    /**
     * tells whether a tile is on the edge of the board
     * @param tile the tile number that correlates to a position on the board
     * @return true if the tile is on the edge
     */
    private boolean isEdge(int tile){
        return (tile > -1 && tile <= 8) || (tile >= 55 && tile < 64) || tile == 15 || tile == 16 || tile == 23 || tile == 24 || tile == 31 || tile == 32 || tile == 39 || tile == 40 || tile == 47 || tile == 48;
    }

    /**
     * tells whether a tile is next to an unused corner
     * @param tile the tile number that correlates to a position on the board
     * @param tiles boolean[][] that tells whether each space has a tile or not
     * @return true if the tile is next to an unused corner
     */
    private boolean isNextToOpenCorner(int tile, boolean[][] tiles){
        return ((tile == 1 || tile == 8 || tile == 9) && !tiles[0][0]) || ((tile == 6 || tile == 14 || tile == 15) && !tiles[0][7]) || ((tile == 48 || tile == 49 || tile == 57) && !tiles[7][0]) || ((tile == 55 || tile == 54 || tile == 62) && !tiles[7][7]);
    }
}
