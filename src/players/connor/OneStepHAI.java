package players.connor;

import game.Board;
import game.BoardState;
import game.Player;

import java.util.List;

/**
 * This {@link Player} is similar to the {@link OneStepAI}, but it uses a modified heuristic for its decisions.
 *
 * This Player uses a heuristic based on data gathered from running the game with a Random AI.
 * The data collected was information about which spaces which the player place a tile on resulted in a win.
 */
public class OneStepHAI implements Player {

    private boolean color;

    private static final int[][] HEURISTIC_DATA = {
            {11476-7587,11057-8376,10862-8162,10708-8168},
            {11012-8313,10964-8794,10843-8523,11078-8656},
            {11142-8129,10540-8471,10707-8457,10267-8192},
            {10919-8243,10671-8394,10265-7813,0}
    };

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
        return "kLessSimple";
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

        int largest = -1, largestPos = 0;
        for (int i = 0; i < after.length; i++) {
            int total = 0;
            BoardState state = after[i].getCurrentBoard();
            for (int r = 0; r < state.getHasTile().length; r++) {
                for (int c = 0; c < state.getHasTile()[0].length; c++) {
                    if(state.getHasTile()[r][c] && state.getColor()[r][c]==color)
                        total += HEURISTIC_DATA[Math.min(r,7-r)][Math.min(c,7-c)];
                }
            }
            if(total > largest) {
                largest = total;
                largestPos = i;
            }
        }

        return moves.get(largestPos);
    }
}
