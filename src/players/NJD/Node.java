package players.NJD;

import game.Board;
import game.BoardState;
import game.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A node of a tree of all moves that is built. Used in the NJDAI class to figure out the best moves and such
 */
public class Node {

    private int move; //0 to 63
    private boolean color; //true is white
    private int value;
    private int depth;
    private Board board;
    private BoardState boardState;
    private Node parent;
    private ArrayList<Node> children;

    /**
     * The constructor for the node at the top of the tree
     * @param state the current board
     * @param color the color that this tree is "playing for"
     * @param depthTarget the depth that the tree should be built to
     */
    public Node(BoardState state, boolean color, int depthTarget){
        boardState = state;
        board = new Board(state);
        this.color = color;
        value = evaluateBoard(board.getCurrentBoard(), color);
        this.move = -1;
        this.depth = 0;
        this.parent = null;
        children = new ArrayList<>();
        branch(depthTarget);
    }

    /**
     * The constructor for any and all children nodes in the tree
     * @param state the current board
     * @param color the color that this tree is "playing for"
     * @param move the move that this node is simulating as a possibility
     * @param depth the depth of this node (0 = the top)
     * @param depthTarget the depth that the tree should be built to
     * @param parent the parent node
     */
    public Node(BoardState state, boolean color, int move, int depth, int depthTarget, Node parent){
        boardState = state;
        board = new Board(state);
        board.makeMove(move, (depth % 2 == 1) == color);
        value = evaluateBoard(board.getCurrentBoard(), color);
        this.color = color;
        this.move = move;
        this.depth = depth;
        this.parent = parent;
        children = new ArrayList<>();
        branch(depthTarget);
    }

    /**
     * A method that is part of a recursive algorithm to build the tree
     * Also where the tree could be trimmed in order to remove sub-par move choices and reduce computation time
     * @param depthTarget the depth that the tree is being built to
     */
    private void branch(int depthTarget){
        if(depth < depthTarget) {
            List<Integer> possibleMoves = Player.findPossibleMoves(boardState, (depth % 2 == 0) == color);
            for (Integer move : possibleMoves) {
                children.add(new Node(board.getCurrentBoard(), color, move, depth + 1, depthTarget, this));
            }
        }
    }

    /**
     * Should be called on top node of tree
     * Best moves at depth 1
     * @return the best moves in the tree
     */
    public int[] bestMoves(){
        if(children.size()==0) return new int[]{-1};
        Node bestChild = children.get(0);
        int bestVal = bestChild.getExpectedValue();
        ArrayList<Integer> vals = new ArrayList<>();
        for(Node child : children){
            int temp = child.getExpectedValue();
            vals.add(temp);
            if(temp>bestVal){
                bestChild = child;
                bestVal = temp;
            }
        }
        Collections.sort(vals);
        int target = vals.get(vals.size()-1) - 2;
        ArrayList<Integer> moves = new ArrayList<>();
        for(Node child : children){
            int temp = child.getExpectedValue();
            if(temp>target){
                moves.add(child.getMove());
            }
        }
        int[] bestMoves = new int[moves.size()];
        for (int i = 0; i < bestMoves.length; i++) {
            bestMoves[i] = moves.get(i);
        }
        return bestMoves;
        //return new int[]{bestChild.getMove()}; //Just the one best move
    }

    /**
     * @return the move this node is simulating
     */
    public int getMove(){
        return move;
    }

    /**
     * @return an integer that is the expected value of this move, if chosen. Higher numbers are better
     */
    public int getExpectedValue(){
        if(children.size() == 0){
            return value;
        }else{
            ArrayList<Integer> values = new ArrayList<>();
            for(Node child: children) values.add(child.getExpectedValue());
            Collections.sort(values);
            if(depth % 2 == 1){
                return (values.get(values.size()-1) + values.get(0))/2;
            }else{
                return values.get(values.size()-1);
            }
        }
    }

    /**
     * A method to get the value of a board based on the positions of all the tiles
     * @param b the boardstate
     * @param color the color that is being calculated for
     * @return the value of the board
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
     * @param r the row of the square
     * @param c the column of the square
     * @return how valuable the square is to have your piece on
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