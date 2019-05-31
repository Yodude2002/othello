package game;

public interface Player {

    /**
     * Performs a Turn
     *
     * If the invocation of his method throws an Exception, then that Player loses the game.
     *
     * @param boardState the current state of the board
     * @return an integer in the range [0,64), or -1 to indicate that no legal moves can be made
     */
    int onTurn(BoardState boardState);
}
