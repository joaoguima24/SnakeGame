package academy.mindswap.gameobjects.snake;

import academy.mindswap.field.Position;

import java.util.LinkedList;

public class Snake {

    private final static int SNAKE_INITIAL_SIZE = 3;
    private Direction direction;
    private boolean alive;
    private int snakeSize;

    private LinkedList<Position> snake;



    public Snake() {
        this.direction = Direction.RIGHT;
        this.alive = true;
        this.snakeSize = SNAKE_INITIAL_SIZE;
        this.snake = new LinkedList<>();
        snake.add(new Position(10,10));
        snake.add(new Position(10,11));
        snake.add(new Position(10,12));
    }

    public void increaseSize() {
        snake.addLast(getTail());
        snakeSize++;
    }

    public void move(Direction direction) {
        int headRow = getHead().getRow();
        int headCol = getHead().getCol();

        this.direction=direction;

        if (direction == Direction.UP){
            snake.addFirst(new Position(headCol,headRow-1));
            snake.removeLast();
        }
        if (direction == Direction.DOWN){
            snake.addFirst(new Position(headCol,headRow+1));
            snake.removeLast();
        }
        if (direction == Direction.RIGHT){
            snake.addFirst(new Position(headCol+1,headRow));
            snake.removeLast();
        }
        if (direction == Direction.LEFT){
            snake.addFirst(new Position(headCol-1,headRow));
            snake.removeLast();
        }
    }

    public void move(){
        move(direction);
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public Position getHead() {
        return snake.get(0);
    }

    public Position getTail() {
        return snake.get(snakeSize-1);
    }

    public LinkedList<Position> getFullSnake(){

        return snake;
    }

    public int getSnakeSize() {
        return snakeSize;
    }
}

