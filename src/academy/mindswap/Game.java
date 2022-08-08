package academy.mindswap;

import academy.mindswap.field.Field;
import academy.mindswap.gameobjects.fruit.Fruit;
import academy.mindswap.gameobjects.mouse.Mouse;
import academy.mindswap.gameobjects.snake.Direction;
import academy.mindswap.gameobjects.snake.Snake;
import com.googlecode.lanterna.input.Key;

import java.io.*;


public class Game {

    private Snake snake;
    private Fruit fruit;
    private int delay;
    private int score;
    private Mouse mouse;
    private int highScore;
    private boolean restart;

    public Game(int cols, int rows, int delay) {
        Field.init(cols, rows);
        snake = new Snake();
        this.delay = delay;
        this.fruit = new Fruit();
    }

    public void start() throws InterruptedException, IOException {
        generateFruit();
        generateMouse();
        Field.drawScoreCard(score);
        Field.drawHighscore(highScore);

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
        checkHighScore();
    }

    public void checkHighScore() throws IOException {
        BufferedReader checkHighScore = new BufferedReader(
                new FileReader("/Users/guimaj/Documents/Mindswap/Snake/src/academy/mindswap/highscore.txt"));
        String readHighScore = checkHighScore.readLine();
        this.highScore = Integer.parseInt(readHighScore);
        setHighScore();
    }

    private void setHighScore() throws IOException {
        if (highScore < score){
            BufferedWriter setHighScore = new BufferedWriter(
                    new FileWriter("/Users/guimaj/Documents/Mindswap/Snake/src/academy/mindswap/highscore.txt"));
            setHighScore.write(""+score);
            setHighScore.close();
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

    private void moveSnake() throws IOException, InterruptedException {

        Key k = Field.readInput();

        if (k != null) {
            switch (k.getKind()) {
                case ArrowUp:
                    if (snake.getDirection() != Direction.DOWN){
                        snake.move(Direction.UP);
                        return;
                    }
                case ArrowDown:
                    if (snake.getDirection() != Direction.UP){
                        snake.move(Direction.DOWN);
                        return;
                    }
                case ArrowLeft:
                    if (snake.getDirection() != Direction.RIGHT){
                        snake.move(Direction.LEFT);
                        return;
                    }
                case ArrowRight:
                    if (snake.getDirection() != Direction.LEFT){
                        snake.move(Direction.RIGHT);
                        return;
                    }
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
            increaseScore(1);
            return;
        }
        if (mouse.getPosition().equals(fruit.getPosition())){
            generateFruit();
            increaseDifficultyLevel();
           decreaseScore(10);
            return;
        }
        if (snake.getHead().equals(mouse.getPosition())){
            mouse.die();
            generateMouse();
            snake.increaseSize();
            snake.increaseSize();
            snake.increaseSize();
            increaseScore(5);
            return;
        }
        for (int i = 1; i < snake.getSnakeSize(); i++) {
            if (snake.getFullSnake().get(i).equals(mouse.getPosition())){
                decreaseScore(5);
                mouse.die();
                Field.clearMouse(mouse);
                Field.clearMouseTail(mouse);
               generateMouse();
               return;
            }
        }
        Field.drawScoreCard(score);
    }

    private void decreaseScore(int amount) {
        score-=amount;
        if (score < 0){
            score = 0;
        }
    }
    private void increaseScore(int amount) {
        this.score += amount;
    }

}

