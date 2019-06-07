import game.Othello;
import players.RandomAI;

public class Main {

    public static void main(String[] args) {
        Othello game = new Othello(new RandomAI(),new RandomAI());
        game.run();
    }

}
