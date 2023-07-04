package pt.amchat.levels;

import javax.swing.*;
import java.awt.*;

public class Level2 extends DefaultLevel {
    public Level2(Difficulty difficulty, JFrame frame) {
        super(2, difficulty, frame);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Add two Vertical obstacles
        g.setColor(Color.DARK_GRAY);
        g.fillRect((BOARD_WIDTH / 4) * CELL_SIZE, CELL_SIZE * BOARD_HEIGHT / 3, CELL_SIZE, CELL_SIZE * BOARD_HEIGHT / 3);
        g.fillRect((3 * BOARD_WIDTH / 4) * CELL_SIZE, CELL_SIZE * BOARD_HEIGHT / 3, CELL_SIZE, CELL_SIZE * BOARD_HEIGHT / 3);
    }

    @Override
    protected void checkCollisionsAndFood() {
        super.checkCollisionsAndFood();
        if (snake.xPos == BOARD_WIDTH / 4 && snake.yPos >= BOARD_HEIGHT / 3 && snake.yPos < BOARD_HEIGHT * 2 / 3) {
            endGame();
        }
        if (snake.xPos == 3 * BOARD_WIDTH / 4 && snake.yPos >= BOARD_HEIGHT / 3 && snake.yPos < BOARD_HEIGHT * 2 / 3) {
            endGame();
        }
    }

    @Override
    protected void generateFood() {
        do {
            food.xPos = (int) Math.floor(Math.random() * (1 + (BOARD_WIDTH - 4) - 4)) + 4;
            food.yPos = (int) Math.floor(Math.random() * (1 + (BOARD_HEIGHT - 4) - 4)) + 4;
        } while (isFoodOverllapingWithWalls());
    }

    private boolean isFoodOverllapingWithWalls() {
        if (food.xPos == BOARD_WIDTH / 4 && food.yPos >= BOARD_HEIGHT / 3 && food.yPos < BOARD_HEIGHT * 2 / 3) {
            return true;
        }
        if (food.xPos == 3 * BOARD_WIDTH / 4 && food.yPos >= BOARD_HEIGHT / 3 && food.yPos < BOARD_HEIGHT * 2 / 3) {
            return true;
        }
        return false;
    }
}

