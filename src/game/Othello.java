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
        p1.setColor(false);
        p2.setColor(true);
        board = new Board();
    }

    /**
     * Runs the game. Returns an integer representing the winner.
     * @param clearBoard Clears board before game if true.
     * @return 0 if p1 won, 1 if p2 won, or -2 if the game was a draw.
     */
    public int run(boolean clearBoard) {

        if (clearBoard)
            board.reset();

        boolean running = true;

        while (running) {
            int p1Turn, p2Turn;
            try{
                p1Turn = player1.getMove(board.getCurrentBoard());
            }catch (Exception e) {
                return 1;
            }

            if(p1Turn != -1) {
                board.makeMove(p1Turn,player1.getColor());
            }

            try{
                p2Turn = player2.getMove(board.getCurrentBoard());
            }catch (Exception e) {
                return 0;
            }

            if(p2Turn != -1) {
                board.makeMove(p2Turn, player2.getColor());
            }else {
                if(p1Turn == -1) running = false;
            }
            //logBoard();
        }
        byte[] ratio = board.blackToWhiteRatio();
        if (ratio[0] == ratio[1])
            return -2;
        boolean winner = ratio[1] > ratio[0];
        if (winner == player1.getColor())
            return 0;
        return 1;
    }

    private void logBoard() {
        boolean[][] hasTile = board.getCurrentBoard().getHasTile(), color = board.getCurrentBoard().getColor();

        for (int r = 0; r < hasTile.length; r++) {
            for (int c = 0; c < hasTile[0].length; c++) {
                if(hasTile[r][c]) {
                    (color[r][c]?System.out:System.err).print('\u0001');
                }else {
                    System.out.print(' ');
                }
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
