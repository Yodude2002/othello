package players.NJD;

import game.Board;
import game.BoardState;
import game.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NJDAI implements Player {

    public boolean isWhite;

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
        int[] bestMoves = findBestMoves(boardState, isWhite);
        return bestMoves[(int) (bestMoves.length * Math.random())];
    }

    /**
     * @param boardState
     * @return
     */
    private int[] findBestMoves(BoardState boardState, boolean color) {
        List<Integer> possibleMoves = Player.findPossibleMoves(boardState, color);
        if (possibleMoves.size() == 0) return new int[]{-1};
        if (possibleMoves.size() == 1) return new int[]{possibleMoves.get(0)};
        State moveTree = new State(boardState,color,2); //TODO MAGIC # LIVES HERE //TODO this breaks my AI and throws some sort of exception
        return moveTree.bestMoves();
    }

    private int[] findOKMoves(BoardState boardState, boolean color){
        List<Integer> possibleMoves = Player.findPossibleMoves(boardState, color);
        if (possibleMoves.size() == 0) return new int[]{-1};
        if (possibleMoves.size() == 1) return new int[]{possibleMoves.get(0)};
        State moveTree = new State(boardState,color,1); //TODO MAGIC # LIVES HERE //TODO this breaks my AI and throws some sort of exception
        int[] bestMoves = moveTree.bestMoves();
        return bestMoves;
    }



}