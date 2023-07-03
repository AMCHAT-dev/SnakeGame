package pt.amchat.levels;

import java.awt.*;

public class Level2 extends GamePanel {

    public Level2(Difficulty difficulty) {
        super(2, difficulty);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Add two obstacles
        g.setColor(Color.DARK_GRAY);
        g.fillRect((BOARD_WIDTH / 4) * CELL_SIZE, CELL_SIZE * BOARD_HEIGHT / 3, CELL_SIZE, CELL_SIZE * BOARD_HEIGHT / 3);
        g.fillRect((3 * BOARD_WIDTH / 4) * CELL_SIZE, CELL_SIZE * BOARD_HEIGHT / 3, CELL_SIZE, CELL_SIZE * BOARD_HEIGHT / 3);


    }

    @Override
    protected void checkCollisionsAndFood() {
        super.checkCollisionsAndFood();
        if (snake.xPos == BOARD_WIDTH / 4 && snake.yPos > BOARD_HEIGHT / 3) {
            endGame();
        }

    }

    @Override
    protected void generateFood() {

    }
}
