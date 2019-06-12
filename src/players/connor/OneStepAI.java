package players.connor;

import game.Board;
import game.BoardState;
import game.Player;

import java.util.List;

/**
 * This {@link Player} is a simple one-step lookahead with a simple heuristic.
 *
 * The heuristic used is a simple algorithm that determines which moves result in the largest number of tiles.
 */
public class OneStepAI implements Player {

    /**
     * This is the color that this {@code Player}
     */
    private boolean color;

    @Override
    public boolean getColor() {
        return color;
    }

    @Override
    public void setColor(boolean white) {
        color = white;
    }

    @Override
    public String toString() {
        return "kSimple";
    }

    @Override
    public int getMove(BoardState boardState) {
        List<Integer> moves = Player.findPossibleMoves(boardState,color);
        if(moves.size() == 0) return -1;

        Board[] after = new Board[moves.size()];
        for (int i = 0; i < moves.size(); i++) {
            after[i] = new Board(boardState);
            after[i].makeMove(moves.get(i),color);
        }

        int largest = Integer.MIN_VALUE, largestPos = 0;
        for (int i = 0; i < after.length; i++) {
            int total = after[i].colorDifference(color);
            if(total > largest) {
                largest = total;
                largestPos = i;
            }
        }

        return moves.get(largestPos);
    }
}
