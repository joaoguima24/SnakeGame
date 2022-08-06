package academy.mindswap.gameobjects.fruit;

import academy.mindswap.field.Position;

public class Fruit {
    private Position p;

    public Fruit() {
        this.p = new Position((int)(Math.random()*(90-2+1)+2),(int) (Math.random()*(23-2+1)+2));
    }

    public Position getPosition() {
        return p;
    }
}
