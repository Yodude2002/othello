package players.NJD;

import game.Board;
import game.BoardState;
import game.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class State {

    private int move; //0 to 63
    private boolean color; //true is white
    private int value;
    private int depth;
    private Board board;
    private BoardState boardState;
    private State parent;
    private ArrayList<State> children;

    public State(BoardState state, boolean color, int depthTarget){
        boardState = state;
        board = new Board(state);
        this.color = color;
        value = evaluateBoard(boardState, color);
        this.move = -1;
        this.depth = 0;
        this.parent = null;
        children = new ArrayList<>();
        branch(depthTarget);
    }

    public State(BoardState state, boolean color, int move, int depth, int depthTarget, State parent){
        boardState = state;
        board = new Board(state);
        board.makeMove(move, (depth % 2 == 1) == color);
        value = evaluateBoard(boardState, color);
        this.color = color;
        this.move = move;
        this.depth = depth;
        this.parent = parent;
        children = new ArrayList<>();
        branch(depthTarget);
    }

    /**
     * HERE IS WHERE YOU TRIM
     * @param depthTarget the amount of branches basically
     */
    private void branch(int depthTarget){
        if(depth == depthTarget) return;
        List<Integer> possibleMoves = Player.findPossibleMoves(boardState, (depth % 2 == 1) == color);
        for(Integer move: possibleMoves){
            children.add(new State(boardState, color, move, depth+1,depthTarget,this));
        }
    }

    /**
     * Best moves at depth 1
     * @return
     */
    public int[] bestMoves(){ //TODO make this a larger array
        if(children.size()==0) return new int[]{-1};
        State bestChild = children.get(0);
        int bestVal = -99999;
        for(State child : children){
            int temp = child.getExpectedValue();
            if(temp>bestVal){
                bestChild = child;
                bestVal = temp;
            }
        }
        return new int[]{bestChild.getMove()};
    }

    public int getMove(){
        return move;
    }

    public int getExpectedValue(){
        if(children.size() == 0){
            return value;
        }else{
            ArrayList<Integer> values = new ArrayList<>();
            for(State child: children) values.add(child.getExpectedValue());
            Collections.sort(values); //TODO maybe doesnt work
            if(depth % 2 == 1){ //opponents choice, do an avg
                return (values.get(values.size()-1) + values.get(0))/2;
            }else{ //my choice, take highest value
                return values.get(values.size()-1);
            }
        }
    }

    /**
     * @param b
     * @param color
     * @return
     */
    private int evaluateBoard(BoardState b, boolean color) {
        boolean[][] tiles = b.getHasTile();
        boolean[][] tileStates = b.getColor();
        int val = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (tiles[r][c]) {
                    int tileVal = getTileVal(r, c);
                    if (tileStates[r][c] == color) {
                        val += tileVal;
                    } else {
                        val -= tileVal;
                    }
                }
            }
        }
        return val;
    }

    /**
     * @param r
     * @param c
     * @return
     */
    private int getTileVal(int r, int c) {
        int val = 1;
        if (r == 0 || r == 7) {
            val++;
        }
        if (c == 0 || c == 7) {
            val++;
        }
        if (r == 1 || r == 6) {
            val--;
        }
        if (c == 1 || c == 6) {
            val--;
        }
        return val;
    }

}