package pt.amchat;

import pt.amchat.levels.Difficulty;
import pt.amchat.levels.GamePanel;
import pt.amchat.levels.Level1;
import pt.amchat.levels.Level2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialMenu extends JPanel implements ActionListener {
    private GameState gameState;
    private JButton playLevel1Button;
    private JButton playLevel2Button;
    private JButton exitButton;
    private JFrame frame;

    public InitialMenu(JFrame frame) {
        this.frame = frame;
        setLayout(null); // Disable the layout manager

        setFocusable(true);
        requestFocusInWindow();

        // Initialize buttons
        playLevel1Button = new JButton("Play level 1");
        playLevel2Button = new JButton("Play level 2");
        exitButton = new JButton("Exit");

        // Set button positions and sizes
        playLevel1Button.setBounds(100, 100, 200, 50);
        playLevel2Button.setBounds(100, 200, 200, 50);
        exitButton.setBounds(100, 300, 200, 50);

        // Add action listeners to buttons
        playLevel1Button.addActionListener(this);
        playLevel2Button.addActionListener(this);
        exitButton.addActionListener(this);

        // Add buttons to the panel
        add(playLevel1Button);
        add(playLevel2Button);
        add(exitButton);

        // Set initial game state
        gameState = GameState.MENU;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playLevel1Button) {
            GamePanel gamePanel = new Level1(Difficulty.HARD, frame);
            startGameFromMenu(gamePanel);
        } else if (e.getSource() == playLevel2Button) {
            GamePanel gamePanel = new Level2(Difficulty.HARD, frame);
            startGameFromMenu(gamePanel);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private void startGameFromMenu(GamePanel game) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(game);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        game.startGame();
        game.requestFocus();
    }

}
