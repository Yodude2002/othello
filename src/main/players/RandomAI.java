package players;

import game.BoardState;
import game.Player;
import java.util.List;

public class RandomAI implements Player {

    private boolean isWhite;

    @Override
    public boolean getColor(){
        return isWhite;
    }

    @Override
    public void setColor(boolean white){
        isWhite = white;
    }

    @Override
    public int getMove(BoardState boardState) {
        List<Integer> possibleMoves = Player.findPossibleMoves(boardState,isWhite);
        if(possibleMoves.size() == 0) return -1;
        return possibleMoves.get((int) (Math.random()*possibleMoves.size()));
    }
}
