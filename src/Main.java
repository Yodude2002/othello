
import game.Othello;
import players.RandomAI;

public class Main {

    public static void main(String[] args) {
        Othello game = new Othello(new RandomAI(),new RandomAI());
        int ret = game.run();
        if(ret == 0) System.out.println("Player 1 Wins!");
        else if(ret == 1) System.out.println("Player 2 Wins!");
        else System.out.println("Tie Game!");
    }

}
