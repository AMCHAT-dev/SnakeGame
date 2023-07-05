package pt.amchat.levelelements;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public Direction direction;
    public int xPos;
    public int yPos;
    public int score;
    public List<BodyPart> body = new ArrayList<>();

    public void moveSnake(Food food) {
        // Create a new body part at the current position of the snake's head
        BodyPart newBodyPart = new BodyPart(xPos, yPos);

        // Move the snake's body
        body.add(0, newBodyPart);

        if (!(xPos == food.xPos && yPos == food.yPos)) {
            body.remove(body.size() - 1);
        }

        if (direction == Direction.UP) {
            yPos--;
        } else if (direction == Direction.DOWN) {
            yPos++;
        } else if (direction == Direction.LEFT) {
            xPos--;
        } else if (direction == Direction.RIGHT) {
            xPos++;
        }
    }

    public boolean isEating(Food food) {
        return xPos == food.xPos && yPos == food.yPos;
    }

    public void eatsFood() {
        score++;
        body.add(new BodyPart(xPos, yPos));
    }

    public boolean hasBodyCollisions() {
        for (BodyPart bodyPart : body) {
            if (xPos == bodyPart.x && yPos == bodyPart.y) return true;
        }
        return false;
    }

    public class BodyPart {
        public int x;
        public int y;

        public BodyPart(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}