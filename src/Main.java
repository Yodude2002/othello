
import game.Othello;
import game.Player;
import players.*;

public class Main {

    private static final int gamesToPlay = 10_000;

    private static final Player p1 = new RandomAI();
    private static final Player p2 = new RandomAI();

    public static void main(String[] args) {
        Othello game = new Othello(p1, p2);
        double p1Wins = 0;
        double p2Wins = 0;
        for (int i = 0; i < gamesToPlay; i++) {
            int ret = game.run(true);
            //printResult(ret);
            if (ret == 0)
                p1Wins++;
            else if (ret == 1)
                p2Wins++;
            else {
                p1Wins += .5;
                p2Wins += .5;
            }
        }
        printWinResult(p1Wins, p2Wins);
    }

    public static void printResult(int ret) {
        if (ret == 0)
            System.out.println("Player 1 Wins!");
        else if (ret == 1) System.out.println("Player 2 Wins!");
        else System.out.println("Tie Game!");
        System.out.println();
    }

    /**
     * Prints the win percentage of both players
     */
    public static void printWinResult(double p1Wins, double p2Wins) {
        System.out.printf("After %,d games,%n",gamesToPlay);
        System.out.printf("Player One (%s): %.1f wins, %4.2f%%%n",p1, p1Wins, p1Wins /gamesToPlay * 100);
        System.out.printf("Player Two (%s): %.1f wins, %4.2f%%%n",p2, p2Wins, p2Wins / gamesToPlay * 100);
    }

}
