package players;

import game.BoardState;
import game.Player;
import java.util.List;

public class RandomAI implements Player { //TODO Was winning 50% of the time until it suddenly wasn't. What has changed?

    /**
     * Color of player. True is white, false is black.
     */
    private boolean isWhite;

    /**
     * Returns color of this player
     * @return Color of player. True is white, false is black.
     */
    @Override
    public boolean getColor(){
        return isWhite;
    }

    /**
     * Sets player's color to boolean white. This should only be called at the start of a game.
     * @param white True if the player should be white, false if black.
     */
    @Override
    public void setColor(boolean white){
        isWhite = white;
    }

    /**
     * The toString method for RandomAI.
     * Gets the name of this AI
     * @return "RandomAI"
     */
    @Override
    public String toString() {
        return "Random AI";
    }

    /**
     * Picks a random move from all the valid moves available for the player's color.
     * @param boardState the current state of the board
     * @return An int representing a valid move for the player's color. -1 = pass, 0 to 63 for spot on 8 by 8 grid.
     */
    @Override
    public int getMove(BoardState boardState) {
        List<Integer> possibleMoves = Player.findPossibleMoves(boardState,isWhite);
        if(possibleMoves.size() == 0) return -1;
        return possibleMoves.get((int) (Math.random()*possibleMoves.size()));
    }

}
