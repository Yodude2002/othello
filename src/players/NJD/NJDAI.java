package players.NJD;

import game.BoardState;
import game.Player;

import java.util.List;

/**
 * Nathaniel J Dorson's AI for Othello
 */
public class NJDAI implements Player {

    /**
     * If this AI is playing for white
     * true: white
     * false: black
     */
    private boolean isWhite;

    @Override
    public String toString() {
        return "NJDAI";
    }

    @Override
    public boolean getColor() {
        return isWhite;
    }

    @Override
    public void setColor(boolean white) {
        isWhite = white;
    }

    @Override
    public int getMove(BoardState boardState) {
        int[] bestMoves = findBestMoves(boardState);
        return bestMoves[(int) (bestMoves.length * Math.random())];
    }

    /**
     * @param boardState the current state of the board
     * @return an integer array of what are (hopefully) the best moves for the given position
     */
    private int[] findBestMoves(BoardState boardState) {
        List<Integer> possibleMoves = Player.findPossibleMoves(boardState, isWhite);
        if (possibleMoves.size() == 0) return new int[]{-1};
        if (possibleMoves.size() == 1) return new int[]{possibleMoves.get(0)};
        Node moveTree = new Node(boardState,isWhite,4);
        int[] bestMoves = moveTree.bestMoves();
        return bestMoves;
    }

    /**
     * a one step look-ahead version of my AI. Seems to function the most reliably
     * @param boardState the current state of the board
     * @return an integer array of what are (hopefully) the best moves for the given position
     */
    private int[] findOKMoves(BoardState boardState){
        List<Integer> possibleMoves = Player.findPossibleMoves(boardState, isWhite);
        if (possibleMoves.size() == 0) return new int[]{-1};
        if (possibleMoves.size() == 1) return new int[]{possibleMoves.get(0)};
        Node moveTree = new Node(boardState,isWhite,1);
        int[] bestMoves = moveTree.bestMoves();
        return bestMoves;
    }



}