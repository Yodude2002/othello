package players;

import game.BoardState;
import game.Player;

import java.util.ArrayList;
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
    public int onTurn(BoardState boardState) {

        List<Integer> possibleMoves = new ArrayList<>();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if(!boardState.getHasTile()[r][c]) {
                    // TODO: 2019-05-31 check for if a valid move and add to the list
                }
            }
        }

        if(possibleMoves.size() == 0) return -1;

        return 0;
    }
}
