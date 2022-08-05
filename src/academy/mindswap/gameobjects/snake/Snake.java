package academy.mindswap.gameobjects.snake;

import academy.mindswap.field.Position;

import java.util.LinkedList;

public class Snake {

    private final static int SNAKE_INITIAL_SIZE = 3;
    private Direction direction;
    private boolean alive;

    public void increaseSize() {

    }

    public void move(Direction direction) {

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
        return null;
    }

    public Position getTail() {
        return null;
    }

    public LinkedList<Position> getFullSnake(){
        return null;
    }

    public int getSnakeSize() {
        return 0;
    }
}

