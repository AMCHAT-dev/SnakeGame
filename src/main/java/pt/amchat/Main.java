package pt.amchat;

import pt.amchat.levels.GamePanel;
import pt.amchat.levels.Level1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

        GamePanel gamePanel = new Level1();
        frame.add(gamePanel);

        frame.setVisible(true);
        gamePanel.startGame();

    }

}