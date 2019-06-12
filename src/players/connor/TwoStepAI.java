package players.connor;

import game.Board;
import game.BoardState;
import game.Player;

import java.util.List;

/**
 * This {@link Player} is similar to the {@link OneStepAI}, but uses a two-step lookahead.
 *
 * This AI is somehow worse than the {@code OneStepAI}
 */
public class TwoStepAI implements Player {

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
        return "kDoubleSimple";
    }

    @Override
    public int getMove(BoardState boardState) {
        List<Integer> moves = Player.findPossibleMoves(boardState,color);
        List<?>[] moves2 = new List<?>[moves.size()]; //Only Integers Allowed
        if(moves.size() == 0) return -1;

        Board[] after = new Board[moves.size()];
        Board[][] after2 = new Board[moves.size()][];
        for (int i = 0; i < moves.size(); i++) {
            after[i] = new Board(boardState);
            after[i].makeMove(moves.get(i),color);
            List<Integer> movesAfter = Player.findPossibleMoves(after[i].getCurrentBoard(),!color);
            moves2[i] = movesAfter;
            after2[i] = new Board[movesAfter.size()];
            for (int j = 0; j < movesAfter.size(); j++) {
                after2[i][j] = new Board(after[i].getCurrentBoard());
                after2[i][j].makeMove(((Integer) moves2[i].get(j)),!color);
            }
        }

        int largest = Integer.MIN_VALUE, largestPos = 0;
        for (int i = 0; i < after.length; i++) {
            for (int j = 0; j < after2[i].length; j++) {
                int total = after2[i][j].colorDifference(color);
                if(total > largest) {
                    largest = total;
                    largestPos = i;
                }
            }

        }

        return moves.get(largestPos);
    }
}
