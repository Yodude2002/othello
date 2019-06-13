package players.drew;

import game.Board;
import game.BoardState;
import game.Player;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class DrewAI implements Player {

    /**
     * Color of player. True is white, false is black.
     */
    private boolean myColor;

    /**
     * How many turns to look ahead when making decisions. 0 would mean just best move of current board.
     */
    private final int DEPTH = 2;

    /**
     * When delving into possible moves, only looks at top K moves, determined by Drew's AI algorithm.
     */
    private final int TOP_K = 100;

    /**
     * Returns color of this player
     * @return Color of player. True is white, false is black.
     */
    @Override
    public boolean getColor(){
        return myColor;
    }

    /**
     * Sets player's color to myColor. This should only be called at the start of a game.
     * @param isWhite True if the player should be white, false if black.
     */
    @Override
    public void setColor(boolean isWhite){
        this.myColor = isWhite;
    }

    /**
     * The toString method for RandomAI.
     * Gets the name of this AI
     * @return "RandomAI"
     */
    @Override
    public String toString() {
        return "Drew's AI";
    }

    /**
     * Returns AI's favorite next valid move based on a secret sauce
     * @param boardState the current state of the board
     * @return A valid move
     */
    @Override
    public int getMove(BoardState boardState) {
        return findDepthBestMove(boardState);
       //return findBestMove(boardState);
    }

    private int findDepthBestMove(BoardState state) {
        List<int[]> movesAtDepth = getMovesAtDepth(state, DEPTH, myColor, -1, new ArrayList<>());
        if (movesAtDepth.size() == 0)
            return -1;
        int highestScoreSoFar = Integer.MIN_VALUE;
        for (int [] move : movesAtDepth)
            if (move[1] > highestScoreSoFar)
                highestScoreSoFar = move[1];
        for (int i = movesAtDepth.size()-1; i >= 0; i--) {
            if (movesAtDepth.get(i)[1] < highestScoreSoFar)
                movesAtDepth.remove(i);
        }
        return movesAtDepth.get((int) (Math.random() * movesAtDepth.size()))[0];
    }

    private List<int[]> getMovesAtDepth(BoardState state, int depth, boolean turnColor, int moveMadeToGetHere, List<int[]> moves) {
        List<Integer> posMoves = Player.findPossibleMoves(state, turnColor);
        if (posMoves.size() == 0)
            return moves;
        if (depth > 1) {
            if (turnColor == myColor) {
                ArrayList<Board> posNextBoards = new ArrayList<>();
                ArrayList<Integer> respectiveMove = new ArrayList<>();
                for (int move : posMoves) {
                    Board temp = new Board(state);
                    temp.makeMove(move, myColor);
                    posNextBoards.add(temp);
                    respectiveMove.add(move);
                }
                ArrayList<int[]> bestMoves = new ArrayList<>();
                ArrayList<Board> respectiveBoards = new ArrayList<>();
                for (int i = posNextBoards.size()-1; i >= 0; i--) {
                    if (bestMoves.size() < TOP_K) {
                        bestMoves.add(new int[]{respectiveMove.get(i),
                                posNextBoards.get(i).colorDifference(myColor)});
                        respectiveBoards.add(posNextBoards.get(i));
                    }
                    else{
                        int colorDiff = posNextBoards.get(i).colorDifference(myColor);
                        for (int j = bestMoves.size() - 1; j >= 0; j--)
                            if (bestMoves.get(j)[1] < colorDiff) {
                                bestMoves.remove(j);
                                respectiveBoards.remove(j);
                                bestMoves.add(new int[] {respectiveMove.get(i),colorDiff});
                                respectiveBoards.add(posNextBoards.get(i));
                                break;
                            }
                    }
                }
                for (int i = 0; i < bestMoves.size(); i++) {
                    int[] move = bestMoves.get(i);
                    if (depth == DEPTH)
                        moveMadeToGetHere = move[0];
                    getMovesAtDepth(respectiveBoards.get(i).getCurrentBoard(), depth - 1, !turnColor, moveMadeToGetHere, moves);
                }
            }
            else {
                Board nextState = new Board(state);
                int move = doOpponentsTurn(state, posMoves);
                nextState.makeMove(move,turnColor);
                if (depth == DEPTH)
                    moveMadeToGetHere = move;
                getMovesAtDepth(nextState.getCurrentBoard(), depth - 1, !turnColor, moveMadeToGetHere, moves);
            }
        }
         else {
             if (turnColor == myColor)
                for (int move : posMoves) {
                    if (depth == DEPTH)
                        moveMadeToGetHere = move;
                    moves.add(new int[]{moveMadeToGetHere, getMoveValue(state, move, myColor, myColor)});
                }
             else {
                 int move = doOpponentsTurn(state, posMoves);
                 moves.add(new int[] {moveMadeToGetHere, getMoveValue(state,move, turnColor, myColor)});
             }
         }
        return moves;
    }

    private int doOpponentsTurn(BoardState state, List<Integer> posMoves) {
        int bestScore = Integer.MIN_VALUE;
        if (posMoves.size() == 0)
            return -1;
        List<Integer> moveOptions = new ArrayList<>();
        for (int move : posMoves) {
            int moveScore = getMoveValue(state, move, !myColor, !myColor);
            if (moveScore > bestScore) {
                bestScore = moveScore;
                moveOptions.clear();
                moveOptions.add(move);
            }
            else if (moveScore == bestScore)
                moveOptions.add(move);
        }
        return moveOptions.get((int)(Math.random() * moveOptions.size()));
    }

    private int getMoveValue(BoardState boardState, int move, boolean turn, boolean colorPointOfView) {
        //int r = move/8;
        //int c = move%8;
        int ret = 0;
        //ret += farFromMiddleValue(r,boardState.getColor().length) + farFromMiddleValue(c,boardState.getColor().length);
        Board temp = new Board(boardState);
        temp.makeMove(move, turn);
        ret += temp.colorDifference(colorPointOfView);
        return ret;
    }

    private int findBestMove(BoardState boardState) {
        List<Integer> posMoves = Player.findPossibleMoves(boardState, myColor);
        if (posMoves.size() == 0)
            return -1;
        int[] moveValue = new int[posMoves.size()];
        for (int i = 0; i < posMoves.size(); i++) {
            int move = posMoves.get(i);
            moveValue[i] += getMoveValue(boardState, move, myColor, myColor);
        }
        int bestMove = 0;
        for (int i = 1; i < posMoves.size(); i++)
            if (moveValue[i] > moveValue[bestMove])
                bestMove = i;
        return posMoves.get(bestMove);
    }

    private int farFromMiddleValue(int a, double length) {
        return (int) Math.abs(length/2 - a);
    }
}
