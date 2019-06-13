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
        //board.makeMove(19,player1.getColor());
        //board.makeMove(20,player2.getColor());

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
        }
        //logBoard();
        int ratio = board.colorDifference(player1.getColor());
        if (ratio == 0)
            return -2;
        if (ratio > 0)
            return 0;
        return 1;
    }

    private void logBoard() {
        boolean[][] hasTile = board.getCurrentBoard().getHasTile(), color = board.getCurrentBoard().getColor();
        String space = "  ";
        for (int r = 0; r < hasTile.length; r++) {
            for (int c = 0; c < hasTile[0].length; c++) {
                if(hasTile[r][c]) {
                    System.out.print(color[r][c]?'\u0001' : 'B');
                }else {
                    System.out.print(space);
                }
                System.out.print(space);
            }
            System.out.println();
        }
        System.out.println();
    }
}
