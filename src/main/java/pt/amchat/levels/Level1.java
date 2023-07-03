package pt.amchat.levels;

public class Level1 extends GamePanel {

    public Level1(Difficulty difficulty) {
        super(1, difficulty);
    }


    @Override
    protected void generateFood() {
        food.xPos = (int) Math.floor(Math.random() * (1 + (BOARD_WIDTH - 4) - 4)) + 4;
        food.yPos = (int) Math.floor(Math.random() * (1 + (BOARD_HEIGHT - 4) - 4)) + 4;
    }
}
