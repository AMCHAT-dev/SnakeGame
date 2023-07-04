package pt.amchat.levels;

import pt.amchat.levelelements.Difficulty;

import javax.swing.*;

public class Level1 extends DefaultLevel {
    public Level1(Difficulty difficulty, JFrame frame) {
        super(1, difficulty, frame);
    }

    @Override
    protected void generateFood() {
        food.xPos = (int) Math.floor(Math.random() * (1 + (BOARD_WIDTH - 4) - 4)) + 4;
        food.yPos = (int) Math.floor(Math.random() * (1 + (BOARD_HEIGHT - 4) - 4)) + 4;
    }
}
