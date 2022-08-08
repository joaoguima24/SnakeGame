package academy.mindswap.field;

import academy.mindswap.gameobjects.fruit.Fruit;
import academy.mindswap.gameobjects.mouse.Mouse;
import academy.mindswap.gameobjects.snake.Snake;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;

public final class Field {

    private static final String BORDER_STRING = "▒";
    private static final String SNAKE_BODY_STRING = "X";
    private static final String SNAKE_HEAD_STRING = "O";
    private static final String FRUIT_STRING = "@";

    private static int width;
    private static int height;
    private static Screen screen;
    private static ScreenWriter screenWriter;


    private Field() {
    }


    public static void init(int width, int height) {

        screen = TerminalFacade.createScreen();

        Field.width = width;
        Field.height = height;
        screen.getTerminal().getTerminalSize().setColumns(width);
        screen.getTerminal().getTerminalSize().setRows(height);

        screenWriter = new ScreenWriter(screen);
        screen.setCursorPosition(null);
        screen.startScreen();

        drawWalls();
        screen.refresh();
    }

    public static void drawSnake(Snake snake) {

        Terminal.Color snakeColor = Terminal.Color.GREEN;

        if (!snake.isAlive()){
            snakeColor = Terminal.Color.RED;
        }

        Position head = snake.getHead();

        for (Position p : snake.getFullSnake()) {
            if (!p.equals(head)) {
                screen.putString(p.getCol(), p.getRow(), SNAKE_BODY_STRING, snakeColor, null);
            } else {
                screen.putString(p.getCol(), p.getRow(), SNAKE_HEAD_STRING, snakeColor, null);
            }
        }
        screen.refresh();
    }

    public static void clearTail(Snake snake) {
        Position tail = snake.getTail();
        screen.putString(tail.getCol(), tail.getRow(), " ", null, null);
    }

    private static void drawWalls() {
        for (int i = 0; i < width; i++) {
            screenWriter.drawString(i, 0, BORDER_STRING);
            screenWriter.drawString(i, height - 1, BORDER_STRING);
        }


        for (int j = 0; j < height; j++) {
            screenWriter.drawString(0, j, BORDER_STRING);
            screenWriter.drawString(width - 1, j, BORDER_STRING);
        }
    }
    public static void drawScoreCard(int score){
        screenWriter.drawString(30, 0, "Score: " + score);
    }
    public static void drawHighscore(int score){
        screenWriter.drawString(50, 0, "Best score: " + score);
    }

    public static void drawFinalScore(String reason, int score){
        screenWriter.drawString(30, 15, reason + score);
    }

    public static Key readInput() {
        return screen.readInput();
    }

    public static void drawFruit(Fruit fruit) {
        screen.putString(fruit.getPosition().getCol(), fruit.getPosition().getRow(), FRUIT_STRING, Terminal.Color.RED ,null);
    }

    public static void drawMouse(Mouse mouse) {
        screen.putString(mouse.getPosition().getCol(), mouse.getPosition().getRow(), "ᐷ", Terminal.Color.YELLOW ,null);
    }
    public static void clearMouseTail(Mouse mouse) {
        Position tail = mouse.getTailPosition();
        screen.putString(tail.getCol(), tail.getRow(), " ", null, null);
    }
    public static void clearMouse(Mouse mouse) {
        Position tail = mouse.getPosition();
        screen.putString(tail.getCol(), tail.getRow(), " ", null, null);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
