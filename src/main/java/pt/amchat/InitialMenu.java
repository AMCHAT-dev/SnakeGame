package pt.amchat;

import pt.amchat.levelelements.Difficulty;
import pt.amchat.levels.DefaultLevel;
import pt.amchat.levels.Level1;
import pt.amchat.levels.Level2;
import pt.amchat.sound.SoundSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialMenu extends JPanel implements ActionListener {
    private JButton playLevel1Button;
    private JButton playLevel2Button;
    private JButton exitButton;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton legendButton;
    private JFrame frame;
    private Difficulty difficultySelected = Difficulty.EASY;

    private Player currentPlayer;

    public InitialMenu(JFrame frame, Player player) {
        this.currentPlayer = player;
        this.frame = frame;
        if (!currentPlayer.isRegisteredAlready()) currentPlayer.registerPlayer();
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        // Initialize buttons
        playLevel1Button = new JButton("Play level 1");
        playLevel2Button = new JButton("Play level 2");
        exitButton = new JButton("Exit");

        // Add action listeners to buttons
        playLevel1Button.addActionListener(this);
        playLevel2Button.addActionListener(this);
        exitButton.addActionListener(this);

        // Add buttons to the button panel
        buttonPanel.add(playLevel1Button);
        buttonPanel.add(playLevel2Button);
        buttonPanel.add(exitButton);

        // Create difficulty buttons
        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Hard");
        legendButton = new JButton("Legend");

        // Set preferred size for all buttons
        Dimension buttonSize = new Dimension(200, 50);
        playLevel1Button.setPreferredSize(buttonSize);
        playLevel2Button.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        easyButton.setPreferredSize(buttonSize);
        mediumButton.setPreferredSize(buttonSize);
        hardButton.setPreferredSize(buttonSize);
        legendButton.setPreferredSize(buttonSize);

        // Add action listeners to difficulty buttons
        easyButton.addActionListener(this);
        mediumButton.addActionListener(this);
        hardButton.addActionListener(this);
        legendButton.addActionListener(this);

        // Create a panel for difficulty buttons
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new GridLayout(4, 1));
        difficultyPanel.add(easyButton);
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(hardButton);
        difficultyPanel.add(legendButton);

        // Add the button panel and difficulty panel to the main panel
        add(buttonPanel, BorderLayout.CENTER);
        add(difficultyPanel, BorderLayout.EAST);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playLevel1Button) {
            DefaultLevel defaultLevel = new Level1(difficultySelected, frame);
            startGameFromMenu(defaultLevel);
        } else if (e.getSource() == playLevel2Button) {
            DefaultLevel defaultLevel = new Level2(difficultySelected, frame);
            startGameFromMenu(defaultLevel);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == easyButton) {
            difficultySelected = Difficulty.EASY;
        } else if (e.getSource() == mediumButton) {
            difficultySelected = Difficulty.MEDIUM;
        } else if (e.getSource() == hardButton) {
            difficultySelected = Difficulty.HARD;
        } else if (e.getSource() == legendButton) {
            difficultySelected = Difficulty.LEGEND;
        }
    }

    private void startGameFromMenu(DefaultLevel game) {
        // TODO able to change sound settings on Menu
        frame.getContentPane().removeAll();
        frame.getContentPane().add(game);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        game.startGame(SoundSettings.ON, currentPlayer);
        game.requestFocus();
    }
}
