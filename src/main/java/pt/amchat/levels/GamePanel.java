package pt.amchat.levels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class GamePanel extends JPanel implements ActionListener {

    protected static final int BOARD_WIDTH = 40;
    protected static final int BOARD_HEIGHT = 30;
    protected static final int CELL_SIZE = 20;

    protected final Snake snake = new Snake();
    protected final Food food = new Food();
    protected final JLabel scoreLabel;
    protected final JLabel highScoreLabel;

    private final JLabel pauseMessage;
    protected boolean gameIsRunning;
    protected int highScore;
    private Timer timer;

    public GamePanel() {
        setLayout(null);
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });

        pauseMessage = new JLabel("Pause: \'P\' Key");
        pauseMessage.setFont(new Font("Arial", Font.BOLD, 16));
        pauseMessage.setBounds(50, 2, 200, 30);
        add(pauseMessage);

        highScoreLabel = new JLabel("HighScore: " + highScore);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        highScoreLabel.setBounds(500, 2, 200, 30);
        add(highScoreLabel);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setBounds(380, 2, 100, 30);
        add(scoreLabel);
    }

    public void startGame() {
        snake.xPos = BOARD_WIDTH / 2;
        snake.yPos = BOARD_HEIGHT / 2;
        snake.direction = KeyEvent.VK_RIGHT;

        generateFood();

        timer = new Timer(50, this);
        timer.start();

        gameIsRunning = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the snake head
        g.setColor(Color.GREEN);
        g.fillRect(snake.xPos * CELL_SIZE, snake.yPos * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Draw the snake body
        g.setColor(Color.GREEN.darker());
        for (BodyPart bodyPart : snake.body) {
            g.fillRect(bodyPart.x * CELL_SIZE, bodyPart.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Draw the food
        g.setColor(Color.RED);
        g.fillOval(food.xPos * CELL_SIZE, food.yPos * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameIsRunning) {
            moveSnake();
            updateHighScoreLabel();
            checkCollisionsAndFood();
            updateScoreLabel();
            repaint();
        }
    }

    public void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_UP && snake.direction != KeyEvent.VK_DOWN) {
            snake.direction = KeyEvent.VK_UP;
        } else if (keyCode == KeyEvent.VK_DOWN && snake.direction != KeyEvent.VK_UP) {
            snake.direction = KeyEvent.VK_DOWN;
        } else if (keyCode == KeyEvent.VK_LEFT && snake.direction != KeyEvent.VK_RIGHT) {
            snake.direction = KeyEvent.VK_LEFT;
        } else if (keyCode == KeyEvent.VK_RIGHT && snake.direction != KeyEvent.VK_LEFT) {
            snake.direction = KeyEvent.VK_RIGHT;
        } else if (keyCode == KeyEvent.VK_P) {
            gameIsRunning = !gameIsRunning;
        }
    }

    private void moveSnake() {
        // Create a new body part at the current position of the snake's head
        BodyPart newBodyPart = new BodyPart(snake.xPos, snake.yPos);

        // Move the snake's body
        snake.body.add(0, newBodyPart);

        if (!(snake.xPos == food.xPos && snake.yPos == food.yPos)) {
            snake.body.remove(snake.body.size() - 1);
        }

        if (snake.direction == KeyEvent.VK_UP) {
            snake.yPos--;
        } else if (snake.direction == KeyEvent.VK_DOWN) {
            snake.yPos++;
        } else if (snake.direction == KeyEvent.VK_LEFT) {
            snake.xPos--;
        } else if (snake.direction == KeyEvent.VK_RIGHT) {
            snake.xPos++;
        }
    }

    /***
     * Need to override for collisions with the boarders and other obstacles according to level
     */
    protected void checkCollisionsAndFood() {
        if (hasSnakeBodyCollisions()) {
            endGame();
        }
        if (snake.xPos == food.xPos && snake.yPos == food.yPos) {
            snake.score++;
            generateFood();
            snake.body.add(new BodyPart(snake.xPos, snake.yPos));
        }
    }

    private boolean hasSnakeBodyCollisions() {
        for (BodyPart bodyPart : snake.body) {
            if (snake.xPos == bodyPart.x && snake.yPos == bodyPart.y) return true;
        }
        return false;
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + snake.score); // Update the score label text
    }

    private void updateHighScoreLabel() {
        if (snake.score >= highScore && snake.score != 0) {
            highScoreLabel.setText("High Score: " + snake.score); // Update the score label text
        }
    }

    protected void endGame() {
        gameIsRunning = false;
        timer.stop();
        String normalScore = "Game Over! Score: " + snake.score + "\nDo you want to play again?";
        String beatHighScore = "Game Over! New High Score: " + snake.score + "\nDo you want to play again?";
        String message = snake.score > highScore ? beatHighScore : normalScore;


        int choice = JOptionPane.showConfirmDialog(this, message
                , "Game Over",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            startGame();
        } else {
            System.exit(0); // Shuts down
        }
        snake.score = 0;
        snake.body.clear();
    }

    /***
     * Need to override to avoid food to appear inside obstacles
     */
    protected abstract void generateFood();

    class Snake {
        public int direction;
        public int xPos;
        public int yPos;
        public int score;
        public java.util.List<BodyPart> body = new ArrayList<>();
    }

    class BodyPart {
        public int x;
        public int y;

        public BodyPart(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class Food {
        public int xPos;
        public int yPos;
    }
}