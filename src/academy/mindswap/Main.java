package academy.mindswap;

import java.io.FilterWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
       Game game = new Game(100, 30, 100);
        try {
            game.checkHighScore();
            game.start();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
