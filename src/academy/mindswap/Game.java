package academy.mindswap;

import academy.mindswap.field.Field;
import academy.mindswap.gameobjects.fruit.Fruit;
import academy.mindswap.gameobjects.mouse.Mouse;
import academy.mindswap.gameobjects.snake.Direction;
import academy.mindswap.gameobjects.snake.Snake;
import com.googlecode.lanterna.input.Key;


public class Game {

    private Snake snake;
    private Fruit fruit;
    private int delay;
    public int score;
    private Mouse mouse;

    public Game(int cols, int rows, int delay) {
        Field.init(cols, rows);
        snake = new Snake();
        this.delay = delay;
        this.fruit = new Fruit();
    }

    public void start() throws InterruptedException {
        generateFruit();
        generateMouse();
        Field.drawScoreCard(score);

        while (true) {
            if(!checkDeadlyCollisions()){
                break;
            }
            Thread.sleep(delay);
            Field.clearTail(snake);
            moveSnake();
            moveMouse();
            checkDeadlyCollisions();
            checkCollisionsThatChangeScore();
            Field.drawSnake(snake);
            Field.drawMouse(mouse);
            Field.clearMouseTail(mouse);
        }

    }

    private void moveMouse() {
        this.mouse.mouseMove();
    }

    private void generateMouse() {
        this.mouse = new Mouse();
        Field.drawMouse(this.mouse);
    }

    private void generateFruit() {
            this.fruit = new Fruit();
            Field.drawFruit(this.fruit);
    }
    private void increaseDifficultyLevel(){
        this.delay--;

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

    private boolean checkDeadlyCollisions() {
        if(snake.getHead().getCol() == 0 || snake.getHead().getCol() == Field.getWidth()-1){
            Field.drawFinalScore("This game has lateral borders, please pay attention... your score:   ", score);
            return false;
        }

        if(snake.getHead().getRow() == 0 || snake.getHead().getRow() == Field.getHeight()-1) {
            Field.drawFinalScore("You crashed your skull, your score is:  ", score);
            return false;
        }
        for (int i = 1; i < snake.getSnakeSize(); i++) {
            if (snake.getFullSnake().get(i).equals(snake.getHead())){
                Field.drawFinalScore("You bite your body, your score is:  ", score);
                return false;
            }
        }
        return true;
    }



    private void checkCollisionsThatChangeScore() {
        if(snake.getHead().equals(fruit.getPosition())){
            generateFruit();
            snake.increaseSize();
            increaseDifficultyLevel();
            this.score++;
        }
        if (mouse.getPosition().equals(fruit.getPosition())){
            generateFruit();
            increaseDifficultyLevel();
            score -= 10;
            if (score < 0){
                score = 0;
            }

        }
        if (snake.getHead().equals(mouse.getPosition())){
            mouse.die();
            generateMouse();
            snake.increaseSize();
            snake.increaseSize();
            snake.increaseSize();
            this.score += 5;

        }
        Field.drawScoreCard(score);
    }
}

