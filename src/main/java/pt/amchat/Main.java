package pt.amchat;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

        InitialMenu initialMenu = new InitialMenu(frame);
        frame.add(initialMenu);
        frame.setVisible(true);
    }

}