package pt.amchat.levels;

import java.awt.*;

public class Level1 extends GamePanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 2 * CELL_SIZE, CELL_SIZE, CELL_SIZE * BOARD_HEIGHT);
        g.fillRect(0, 2 * CELL_SIZE, CELL_SIZE * BOARD_WIDTH, CELL_SIZE);
        g.fillRect(CELL_SIZE * (BOARD_WIDTH - 2), 2 * CELL_SIZE, CELL_SIZE * 2, CELL_SIZE * BOARD_HEIGHT);
        g.fillRect(0, CELL_SIZE * (BOARD_HEIGHT - 3), CELL_SIZE * BOARD_WIDTH, CELL_SIZE * 2);
    }

    @Override
    protected void checkCollisionsAndFood() {
        super.checkCollisionsAndFood();
        if (snake.xPos < 1 || snake.xPos >= BOARD_WIDTH - 2 || snake.yPos < 3 || snake.yPos >= BOARD_HEIGHT - 3) {
            endGame(); // End the game
        }
    }

    @Override
    protected void generateFood() {
        food.xPos = (int) Math.floor(Math.random() * (1 + (BOARD_WIDTH - 4) - 4)) + 4;
        food.yPos = (int) Math.floor(Math.random() * (1 + (BOARD_HEIGHT - 4) - 4)) + 4;
    }
}
