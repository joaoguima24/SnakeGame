package academy.mindswap;

import academy.mindswap.field.Field;
import academy.mindswap.gameobjects.fruit.Fruit;
import academy.mindswap.gameobjects.snake.Direction;
import academy.mindswap.gameobjects.snake.Snake;
import com.googlecode.lanterna.input.Key;

import java.util.Objects;


public class Game {

    private Snake snake;
    private Fruit fruit;
    private int delay;
    public int score;

    public Game(int cols, int rows, int delay) {
        Field.init(cols, rows);
        snake = new Snake();
        this.delay = delay;
        this.fruit = new Fruit();
    }

    public void start() throws InterruptedException {
        generateFruit();
        Field.drawScoreCard(score);

        while (true) {
            if(!checkCollisions()){
                break;
            }
            Thread.sleep(delay);
            Field.clearTail(snake);
            moveSnake();
            checkCollisions();
            checkCollisionWithApple();
            Field.drawSnake(snake);
        }
    }

    private void generateFruit() {
            this.fruit = new Fruit();
            Field.drawFruit(this.fruit);
    }

    private void moveSnake() {

        Key k = Field.readInput();

        if (k != null) {
            switch (k.getKind()) {
                case ArrowUp:
                    snake.move(Direction.UP);
                    return;

                case ArrowDown:
                    snake.move(Direction.DOWN);
                    return;

                case ArrowLeft:
                    snake.move(Direction.LEFT);
                    return;

                case ArrowRight:
                    snake.move(Direction.RIGHT);
                    return;
            }
        }
        snake.move();
    }

    private boolean checkCollisions() {
        if(snake.getHead().getCol() == 0 || snake.getHead().getCol() == Field.getWidth()-1){
            return false;
        }

        if(snake.getHead().getRow() == 0 || snake.getHead().getRow() == Field.getHeight()-1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(snake, game.snake) && Objects.equals(fruit, game.fruit);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    private void checkCollisionWithApple() {
        if(snake.getHead().equals(fruit.getPosition())){
            generateFruit();
            snake.increaseSize();
            this.score++;
            Field.drawScoreCard(score);
        }
    }
}

