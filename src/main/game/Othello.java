package game;

public class Othello {

    private Player player1, player2;

    private Board board;

    /**
     * Creates a new Othello game, given 2 {@link Player} objects.
     *
     * {@code p1} will be the dark player, who will go first.
     * {@code p2} will be the light player, who will go second.
     * @param p1 a Player object representing the first player/AI
     * @param p2 a Player representing the second player/AI
     */
    public Othello(Player p1, Player p2) {
        player1 = p1;
        player2 = p2;


    }

    /**
     * Runs the game. Returns an integer representing the winner.
     * @return 0 if p1 won, 1 if p2 won, or -1 if the game was a draw.
     */
    public int run() {

        boolean running = true;

        while (running) {
            int p1Turn, p2Turn;
            try{
                p1Turn = player1.onTurn(board.getCurrentBoard());
            }catch (Exception e) {
                return 1;
            }
            try{
                p2Turn = player2.onTurn(board.getCurrentBoard());
            }catch (Exception e) {
                return 0;
            }

            // TODO: 2019-05-31 process the moves, and see if anyone has won.

        }

        return -2;
    }
}
