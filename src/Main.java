
import game.Othello;
import players.DrewAI;
import players.RandomAI;
import java.text.DecimalFormat;

public class Main {

    private static final int gamesToPlay = 1000;

    public static void main(String[] args) {
        Othello game = new Othello(new RandomAI(), new RandomAI());
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
        DecimalFormat df = new DecimalFormat("##.##");
        System.out.println("After " + String.format("%,d", gamesToPlay) + " games,");
        System.out.println("Player One: " + p1Wins + " wins, " + df.format(p1Wins /gamesToPlay * 100) +'%');
        System.out.println("Player Two: " + p2Wins + " wins, " + df.format(p2Wins / gamesToPlay * 100) +'%');
    }

}
