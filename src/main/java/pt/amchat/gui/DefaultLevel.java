package pt.amchat.gui;

import pt.amchat.Player;
import pt.amchat.levelelements.Difficulty;
import pt.amchat.levelelements.Direction;
import pt.amchat.levelelements.Food;
import pt.amchat.levelelements.Snake;
import pt.amchat.sound.Sound;
import pt.amchat.sound.SoundSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class DefaultLevel extends JPanel implements ActionListener {
    protected static final int BOARD_WIDTH = 40;
    protected static final int BOARD_HEIGHT = 30;
    protected static final int CELL_SIZE = 20;
    //External Elements
    protected final Snake snake = new Snake();
    protected final Food food = new Food();
    protected final JLabel scoreLabel, highScoreLabel, displayLevel, pauseMessage, soundMessage;
    private final JFrame frame;
    private final Sound sound = new Sound();
    private final Difficulty difficulty;
    private final int levelId;
    protected int highScore;
    protected boolean gameIsRunning;
    private Timer timer;
    private long lastTimeDirectionChanged;
    private Player currentPlayer;

    public DefaultLevel(int levelId, Difficulty difficulty, JFrame frame) {
        this.levelId = levelId;
        this.difficulty = difficulty;
        this.frame = frame;
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
        pauseMessage.setBounds(25, 2, 125, 30);
        add(pauseMessage);

        soundMessage = new JLabel("Sound ON/OFF: \'O\' Key ");
        soundMessage.setFont(new Font("Arial", Font.BOLD, 16));
        soundMessage.setBounds(150, 2, 200, 30);
        add(soundMessage);

        displayLevel = new JLabel("Level: " + levelId + " (" + difficulty.name() + ")");
        displayLevel.setFont(new Font("Arial", Font.BOLD, 16));
        displayLevel.setBounds(350, 2, 150, 30);
        add(displayLevel);


        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setBounds(550, 2, 100, 30);
        add(scoreLabel);

        highScoreLabel = new JLabel("HighScore: " + highScore);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        highScoreLabel.setBounds(650, 2, 200, 30);
        add(highScoreLabel);

    }

    public void startGame(SoundSettings soundSettings, Player player) {
        this.currentPlayer = player;
        highScore = player.getHighscoreOfLevel(levelId);
        highScoreLabel.setText("High Score: " + highScore);
        add(highScoreLabel);
        snake.xPos = BOARD_WIDTH / 2;
        snake.yPos = BOARD_HEIGHT / 2;
        snake.direction = Direction.STOPPED;

        if (soundSettings == SoundSettings.ON) sound.loop();

        generateFood();

        timer = new Timer(difficulty.getValue(), this);
        timer.start();

        gameIsRunning = true;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw borders
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 2 * CELL_SIZE, CELL_SIZE, CELL_SIZE * BOARD_HEIGHT);
        g.fillRect(0, 2 * CELL_SIZE, CELL_SIZE * BOARD_WIDTH, CELL_SIZE);
        g.fillRect(CELL_SIZE * (BOARD_WIDTH - 2), 2 * CELL_SIZE, CELL_SIZE * 2, CELL_SIZE * BOARD_HEIGHT);
        g.fillRect(0, CELL_SIZE * (BOARD_HEIGHT - 3), CELL_SIZE * BOARD_WIDTH, CELL_SIZE * 2);

        // Draw the snake head
        g.setColor(Color.GREEN);
        g.fillRect(snake.xPos * CELL_SIZE, snake.yPos * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Draw the snake body
        g.setColor(Color.GREEN.darker());
        for (Snake.BodyPart bodyPart : snake.body) {
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
            snake.moveSnake(food);
            updateHighScoreLabel();
            checkCollisionsAndFood();
            updateScoreLabel();
            repaint();
        }
    }

    public void handleKeyPress(int keyCode) {
        long currentTime = System.currentTimeMillis();
        long coolDownPeriod = (long) (0.9 * difficulty.getValue());
        if (currentTime - lastTimeDirectionChanged < coolDownPeriod) {
            return;
        }
        if (keyCode == KeyEvent.VK_UP && snake.direction != Direction.DOWN) {
            snake.direction = Direction.UP;
            lastTimeDirectionChanged = currentTime;
        } else if (keyCode == KeyEvent.VK_DOWN && snake.direction != Direction.UP) {
            snake.direction = Direction.DOWN;
            lastTimeDirectionChanged = currentTime;
        } else if (keyCode == KeyEvent.VK_LEFT && snake.direction != Direction.RIGHT) {
            snake.direction = Direction.LEFT;
            lastTimeDirectionChanged = currentTime;
        } else if (keyCode == KeyEvent.VK_RIGHT && snake.direction != Direction.LEFT) {
            snake.direction = Direction.RIGHT;
            lastTimeDirectionChanged = currentTime;
        } else if (keyCode == KeyEvent.VK_P) {
            gameIsRunning = !gameIsRunning;
        } else if (keyCode == KeyEvent.VK_O) {
            sound.switchStates();
        }
    }


    /***
     * Need to override for collisions with the boarders and other obstacles according to level
     */
    protected void checkCollisionsAndFood() {
        if (snake.hasBodyCollisions() || snake.xPos < 1 || snake.xPos >= BOARD_WIDTH - 2 || snake.yPos < 3 || snake.yPos >= BOARD_HEIGHT - 3) {
            endGame();
        }
        if (snake.isEating(food)) {
            generateFood();
            snake.eatsFood();
        }
    }

    /***
     * Need to override to avoid food to appear inside obstacles
     */
    protected abstract void generateFood();


    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + snake.score); // Update the score label text
    }

    private void updateHighScoreLabel() {
        if (snake.score >= highScore && snake.score != 0) {
            highScoreLabel.setText("High Score: " + snake.score); // Update the score label text
        }
    }

    protected void endGame() {
        SoundSettings previousSettings = sound.state();
        sound.stop();
        timer.stop();
        gameIsRunning = false;
        String message;
        if (snake.score > highScore) {
            message = "Game Over! New High Score: " + snake.score + "\nDo you want to play again?";
            highScore = snake.score;
            currentPlayer.registerNewHighScore(highScore, levelId);
        } else {
            message = "Game Over! Score: " + snake.score + "\nDo you want to play again?";
        }
        int choice = JOptionPane.showConfirmDialog(this, message, "Game Over",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            startGame(previousSettings, currentPlayer);
        } else {
            InitialMenu initialMenu = new InitialMenu(frame, currentPlayer);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(initialMenu);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
            initialMenu.requestFocus();
        }
        snake.score = 0;
        snake.body.clear();

    }


}