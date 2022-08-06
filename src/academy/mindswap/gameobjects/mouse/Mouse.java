package academy.mindswap.gameobjects.mouse;

import academy.mindswap.field.Field;
import academy.mindswap.field.Position;
import academy.mindswap.gameobjects.snake.Direction;

import java.util.LinkedList;

public class Mouse {
    private final LinkedList<Position> mouse;
    private Direction mouseDirection;
    private boolean amIAlive;
    int counter;


    public Mouse() {
        this.mouse = new LinkedList<>();
        mouse.addFirst(new Position((int)(Math.random()*(90-2+1)+2),(int) (Math.random()*(23-2+1)+2)));
        mouse.addLast(new Position(mouse.get(0).getCol()+1,mouse.get(0).getRow()));
        mouseDirection = Direction.UP;
        this.amIAlive = true;
    }

    public void mouseMove(){
        if (!amIAlive){
            return;
        }
        avoidCollisions();
        counter++;
        if (counter %40 == 0){
            randomDirection();
        }
        if (mouseDirection == Direction.RIGHT){
            mouse.addFirst(new Position(getPosition().getCol()+1,getPosition().getRow()));
            mouse.removeLast();
        }
        if (mouseDirection == Direction.LEFT){
            mouse.addFirst(new Position(getPosition().getCol()-1,getPosition().getRow()));
            mouse.removeLast();
        }
        if (mouseDirection == Direction.UP){
            mouse.addFirst(new Position(getPosition().getCol(),getPosition().getRow()-1));
            mouse.removeLast();
        }
        if (mouseDirection == Direction.DOWN){
            mouse.addFirst(new Position(getPosition().getCol(),getPosition().getRow()+1));
            mouse.removeLast();
        }
    }
    public void die(){
        amIAlive = false;
    }

    private void avoidCollisions() {
        if (mouse.getFirst().getCol() == 1 ){
            this.mouseDirection = Direction.RIGHT;
        }
        if (mouse.getFirst().getCol() == Field.getWidth()-2 ){
            this.mouseDirection = Direction.LEFT;
        }
        if (mouse.getFirst().getRow() == 1){
            this.mouseDirection = Direction.DOWN;
        }
        if (mouse.getFirst().getRow() == Field.getHeight()-2){
            this.mouseDirection = Direction.UP;
        }
    }

    private void randomDirection() {
        double randomDirection = Math.random();
        if (mouseDirection == Direction.DOWN || mouseDirection == Direction.UP){
            if (randomDirection < 0.5){
                mouseDirection = Direction.RIGHT;
                return;
            }
            mouseDirection = Direction.LEFT;
            return;
        }
        if (randomDirection < 0.5){
            mouseDirection = Direction.UP;
            return;
        }
        mouseDirection = Direction.DOWN;
    }

    public Position getTailPosition(){
        return mouse.getLast();
    }

    public Position getPosition() {
        return mouse.getFirst();
    }
}
